# Topic: Symmetric Ciphers. Stream Ciphers. Block Ciphers.

## Course: Cryptography & Security

### Author: Dubina Valeria

----

## Theory

A _**symmetric cipher**_ is one that uses the same key for encryption and decryption.
Symmetric encryption algorithms are categorized into two: **block** and **stream** ciphers.

### Block ciphers

Block ciphers convert data in plaintext into ciphertext in fixed-size blocks. The block size generally 
depends on the encryption scheme and is usually in octaves (64-bit or 128-bit blocks). If the plaintext 
length is not a multiple of 8, the encryption scheme uses padding to ensure complete blocks.

### Stream ciphers

A stream cipher encrypts a continuous string of binary digits by applying time-varying transformations on 
plaintext data. Therefore, this type of encryption works bit-by-bit, using keystreams to generate 
ciphertext for arbitrary lengths of plain text messages. The cipher combines a key (128/256 bits) and
a nonce digit (64-128 bits) to produce the keystream — a pseudorandom number XORed with the plaintext to
produce ciphertext. While the key and the nonce can be reused, the keystream has to be unique for each 
encryption iteration to ensure security.

## Objectives

1. Get familiar with the symmetric cryptography, stream and block ciphers.

2. Implement an example of a stream cipher.

3. Implement an example of a block cipher.

4. The implementation should, ideally follow the abstraction/contract/interface used in the previous laboratory work.

5. Please use packages/directories to logically split the files that you will have.

6. As in the previous task, please use a client class or test classes to showcase the execution of your programs.

## Implementation description:

This laboratory work contains the implementation of two symmetric ciphers, meaning a representative of each category:
block and stream.

### DES

As block cipher algorithm, **DES** cipher was chosen to be implemented, it stands for _Data Encryption Standard_, and 
is a implementation of _Feistel Cipher_. In the following in more details will be analysed the method `permute` from `DES.java`:

There are 16 identical stages of processing, called rounds and also an initial and final permutation, 
named IP and FP, which are inverses:
```java
    // Initial Permutation table
    private static final byte[] IP = {
        58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9,  1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7
    };
    
        // Inverse permutation
    private static final byte[] FP = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };
```
Before the main rounds, the block is divided into two 32-bit halves and processed alternately
```java
        int[] L = new int[32];
        int[] R = new int[32];
```
The Feistel structure ensures that decryption and encryption are very similar processes—the only 
difference is that the subkeys are applied in the reverse order when decrypting. The rest of 
the algorithm is identical. The output from the Feistel is then combined with the other half 
of the block, and the halves are swapped before the next round.
```java
        for(int n=0 ; n < 16 ; n++) {
            int[] newR;
            
            if(isDecrypt) {
                newR = fiestel(R, subkey[15-n]);
            } else {
                newR = fiestel(R, KS(n, keyBits));
            }
            int[] newL = xor(L, newR);
            L = R;
            R = newL;
        }
```

The Feistel, operates on half a block (32 bits) at a time and consists of four stages:

1. The 32-bit half-block is expanded to 48 bits using the expansion permutation

2. The result is combined with a subkey using an XOR operation.

3. After mixing in the subkey, the block is divided into eight 6-bit pieces before proceeding 
by the substitution boxes, each of the eight S-boxes replaces its six input bits with four output bits
provided in the form of a table

4. The 32 outputs from the S-boxes are rearranged according to a fixed permutation, the P-box.
```java
    private static int[] fiestel(int[] R, int[] roundKey) {
        int[] expandedR = new int[48];
        for(int i=0 ; i < 48 ; i++) {
            expandedR[i] = R[E[i]-1];
        }
        int[] temp = xor(expandedR, roundKey);
        int[] output = sBlock(temp);
        return output;
    }
```

The algorithm which generates the subkeys works as follows: 
initially, 56 bits of the key are selected from the initial 64 by Permuted Choice 1 (PC-1)—the 
remaining eight bits are either discarded or used as parity check bits. The 56 bits are then 
divided into two 28-bit halves; each half is thereafter treated separately. In successive rounds,
both halves are rotated left by one or two bits (specified for each round), and then 48 subkey bits are 
selected by Permuted Choice 2 (PC-2)—24 bits from the left half, and 24 from the right.
```java
    private static int[] keySchedule(int round, int[] key) {
        int[] C1;
        int[] D1;

        int rotationTimes = rotations[round];
        C1 = leftShift(C, rotationTimes);
        D1 = leftShift(D, rotationTimes);
        int[] CnDn = new int[56];
        System.arraycopy(C1, 0, CnDn, 0, 28);
        System.arraycopy(D1, 0, CnDn, 28, 28);
        int[] Kn = new int[48];
        for(int i=0 ; i < Kn.length ; i++) {
            Kn[i] = CnDn[PC2[i]-1];
        }

        subkey[round] = Kn;
        C = C1;
        D = D1;
        return Kn;
    }
```

### Grain

As stream cipher algorithm, **Grain** cipher was chosen to be implemented.

Before any keystream is generated the cipher must be initialized with the key and the IV.
Let the bits of the key, k, be denoted ki, 0 ≤ i ≤ 79 and the bits of the IV be denoted IVi,
0 ≤ i ≤ 63. The initialization of the key is done as follows. First load the NFSR with the key 
bits, bi = ki, 0 ≤ i ≤ 79, then load the f irst 64 bits of the LFSR with the IV, si = IVi, 0 ≤ i ≤ 63. 
The remaining bits of the LFSR are filled with ones, si = 1, 64 ≤ i ≤ 79. Because of this the LFSR cannot
be initialized to the all zero state. Then the cipher is clocked 160 times without producing any running key.
Instead the output function is fed back and xored with the input, both to the LFSR and to the NFSR.
```java
        for (int i = 0; i < iv_result.length(); i++) {
            lfsr[i] = Byte.parseByte(String.valueOf(iv_result.charAt(i)));
        }

        for (int i = 64; i < 80; i++) {
            lfsr[i] = 1;
        }

        String result2 = this.stringToBinary(key);

        for (int i = 0; i < result2.length(); i++) {
            this.nfsr[i] = Byte.parseByte(String.valueOf(result2.charAt(i)));
        }
```
The cipher consists of three main building blocks, namely an LFSR, an NFSR and an output function.

The feedback polynomial of the LFSR is defined:
```java
   xor = (byte) (data[62] ^ data[51] ^ data[38] ^ data[23] ^ data[13] ^ data[0]);
```

The feedback polynomial of the NFSR, is defined as
```java
 xor = (byte) (data[0] ^ data[63] ^ data[60]
                    ^ data[52] ^ data[45] ^ data[37]
                    ^ data[33] ^ data[28] ^ data[21]
                    ^ data[15] ^ data[19] ^ data[0]
                    ^ (data[63] & data[60]) ^ (data[37] & data[33])
                    ^ (data[15] & data[9]) ^ (data[60] & data[52] & data[45])
                    ^ (data[33] & data[28] & data[21])
                    ^ (data[63] & data[45] & data[28] & data[9])
                    ^ (data[60] & data[52] & data[37] & data[33])
                    ^ (data[63] & data[60] & data[21] & data[15])
                    ^ (data[63] & data[60] & data[52] & data[45] & data[37])
                    ^ (data[33] & data[28] & data[21] & data[15] & data[9])
                    ^ (data[52] & data[45] & data[37] & data[33] & data[28] & data[21]));
```
The contents of the two shift registers represent the state of the cipher. From this state, 5 variables are taken as input to a boolean function, h(x).
```java
        Byte x0 = this.temp_lfsr[3];
        Byte x1 = this.temp_lfsr[25];
        Byte x2 = this.temp_lfsr[46];
        Byte x3 = this.temp_lfsr[64];
        Byte x4 = this.temp_nfsr[63];
        Byte f_h = (byte) (x1 ^ x4 ^ (x0 & x3) ^ (x2 & x3) ^ (x3 & x3) ^ (x0 & x1 & x2)
                ^ (x0 & x2 & x3) ^ (x0 & x2 & x4) ^ (x1 & x2 & x4) ^ (x2 & x3 & x4));
```

The encryption and decryprion process are done by xor-ing the keystream with the provided string for encryption,
String keystream is has the same info as `this.final` but in byte array form:
```java
            result_xor_array[i] = (byte) (this.filter[i] ^ result_array[i]);

```




