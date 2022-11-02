# Topic: Intro to Cryptography. Classical ciphers. Caesar cipher.

## Course: Cryptography & Security

### Author: Dubina Valeria

----

## Theory

The classical algorithms are those invented pre-computer up until around the 1950's.
There are two major principles in classical cryptography: transposition and substitution.

Transposition is the changing in the position of the letters in the message such as a simple writing backwards.

Substitution is substituting a Symbol for a letter of a plaintext, or word or even sentence.

### Caesar Cipher

In this cipher, each letter of the plaintext is replaced by a new letter obtained by an alphabetical shift. The secret key k, which is the same for encryption as and
upon decryption, it consists of the number indicating the alphabetic displacement, i.e. $k \in \{1, 2, 3,…, n–1\}$,  where n is the length of the alphabet. The encryption and decryption of the message with the Caesar cipher can be defined by the formulas
where x and y are the numeric representation of the respective plaintext character.

### Affine Cipher

This is a generalization of the Caesar cipher, having the key $k = \{(a, b) \mid a, b \in Z_{26} = \{0, 1, 2, …, 25\}, cmmdc(a, 26) = 1\}$,
and the encryption and decryption functions for a key k = (a, b) are $e_{k}(x) = ax + b (mod 26)$ and $d_k(y) = a^{ −1}(y + 26 − b) (mod 26)$
where $a*a^{−1} =1 mod(n)$.


### Vigenere Cipher

The Vigenère cipher has several Caesar ciphers in sequence with different shift values.

To encrypt, a table of alphabets can be used, termed a tabula recta, Vigenère square 
or Vigenère table. It has the alphabet written out 26 times in different rows, each 
alphabet shifted cyclically to the left compared to the previous alphabet, corresponding 
to the 26 possible Caesar ciphers. Encryption is done as follows: $c_{i} = m_{i} + k_{i} (mod 26)$.


The key can be, for example, k = (5, 20, 17, 10, 20, 13) and would cause the displacement
of the first letter by 5, $c_1=m_1 + 5 (mod 26)$, of the second by 20, $c_2 = m_2 + 20 (mod 26)$,
etc. to the end of key 11 and then from the beginning again. The key is usually a word, to make 
it easier to remember - the key above corresponds to the word "furtun".

Decryption for the Vigenere cipher is similar to encryption. The difference is that the 
key is subtracted from the ciphertext: $m_i = c_i – k_i (mod 26)$.

### Playfair Cipher

The Playfair cipher uses a 5 by 5 table containing a key word or phrase.

To generate the key table, one would first fill in the spaces in the table
with the letters of the keyword dropping any duplicate letters, then fill
the remaining spaces with the rest of the letters of the alphabet 
in order (usually omitting "J" or "Q" to reduce the alphabet to fit; 
other versions put both "I" and "J" in the same space).

The keyword together with the conventions for filling in the 5 by 5 table constitute the cipher key.

To encrypt a message, one would break the message into digrams. These 
digrams will be substituted using the key table. Since encryption requires 
pairs of letters, messages with an odd number of characters usually append 
an uncommon letter, such as "X", to complete the final digram. The two 
letters of the digram are considered opposite corners of a rectangle in the 
key table. To perform the substitution, apply the following 4 rules, in order,
to each pair of letters in the plaintext:

1. If both letters are the same (or only one letter is left), add an "X" after 
the first letter. Encrypt the new pair and continue. Some variants of Playfair 
use "Q" instead of "X", but any letter, itself uncommon as a repeated pair, will do.


2. If the letters appear on the same row of your table, replace them with 
the letters to their immediate right respectively (wrapping around to the 
left side of the row if a letter in the original pair was on the right side of the row).


3. If the letters appear on the same column of your table, replace them with
the letters immediately below respectively (wrapping around to the top side
of the column if a letter in the original pair was on the bottom side of the column). 


4. If the letters are not on the same row or column, replace them with the
letters on the same row respectively but at the other pair of corners of 
the rectangle defined by the original pair. The order is important – the 
first letter of the encrypted pair is the one that lies on the same row as 
the first letter of the plaintext pair.

To decrypt, use the inverse (opposite) of the two shift rules, selecting the 
letter to the left or upwards as appropriate. The last rule remains unchanged,
as the transformation switches the selected letters of the rectangle to the
opposite diagonal, and a repeat of the transformation returns the selection
to its original state. The first rule can only be reversed by dropping any
extra instances of the chosen insert letter — generally "X"s or "Q"s — that
do not make sense in the final message when finished.

## Objectives

1. Get familiar with the basics of cryptography and classical ciphers.
2. Implement 4 types of the classical ciphers:
    - Caesar Cipher with one key used for substitution
    - Caesar Cipher with one key used for substitution, and a permutation of the alphabet
    - Vigenere Cipher
    - Playfair Cipher
    - If you want you can implement other.
3. Structure the project in methods/classes/packages as neeeded.

## Implementation description:

This laboratory work contains the implementation of 4 classical ciphers: Affine, Caesar, Playfair and
Vigenere classical Ciphers, covered with some unit Tests.

### Caesar Cipher:

We have to give to the cipher a text and an integer value, known as a shift which indicates the number 
of positions each letter of the text has been moved down. 

The encryption can be represented using modular arithmetic by first transforming the letters into number
```aidl
                int pos = ALPHABET.indexOf(inputChar);
                int encryptPos = (key + pos) % 26;
                char encryptChar = ALPHABET.charAt(encryptPos);
                encryptStr.append(encryptChar);
```
For the decryption just shift back the key, meaning perform subtraction (from the given position) of a int key.
```aidl
                int pos = ALPHABET.indexOf(inputChar);
                int decryptPos = (pos - key) % 26;
                if (decryptPos < 0) {
                    decryptPos = ALPHABET.length() + decryptPos;
                }
                char decryptChar = ALPHABET.charAt(decryptPos);
                decryptStr.append(decryptChar);
```

### Afine Cipher

Each letter in an alphabet is mapped to its numeric equivalent, encrypted using a simple mathematical function,
and converted back to a letter.
```aidl
            char character = input.charAt(in);
            
            if (Character.isLetter(character)) {
                character = (char) ((keyA * (character - 'A') + keyB) % 26 + 'A');
            }
            builder.append(character);
```
For the decryption process the inverse of the operation is performed, onto the inverse of the number representation 
of the given char, meaning to perform $nr^{-1}$:
```aidl
            BigInteger inverse = BigInteger.valueOf(keyA).modInverse(BigInteger.valueOf(26));
            char character = input.charAt(in);

            if (Character.isLetter(character)) {
                int decoded = inverse.intValue() * (character - 'A' - keyB + 26);
                character = (char) (decoded % 26 + 'A');
            }
            builder.append(character);
```
### Vigenere Cipher

The encryption and decryption are done by Vigenar algebraically formula:

For the encryption:
```aidl
                char character = text.charAt(i);
                
                if (character < 'A' || character > 'Z')
                    continue;
                res.append((char) ((character + key.charAt(j) - 2 * 'A') % 26 + 'A'));
                j = ++j % key.length();
```
For the decryption:
```aidl
                char character = text.charAt(i);
                
                if (character < 'A' || character > 'Z')
                    continue;
                res.append((char) ((c - key.charAt(j) + 26) % 26 + 'A'));
                j = ++j % key.length();
```

### Playfair Cipher

The first step is to generate a key that is a 5x5 grid of alphabets.
The initial alphabets in the key square are the unique alphabets of the key in the order 
in which they appear followed by the remaining letters of the alphabet in order

```aidl
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (playfairTable[i][j].equals("" + keyString.charAt(k))) {
                        repeat = true;
                    } else if (playfairTable[i][j].equals("") && !repeat && !used) {
                        playfairTable[i][j] = "" + keyString.charAt(k);
                        used = true;
                    }
                }
            }
```
For the encryption the input String is split in digraphs as follows:
```aidl
    for (int j = 0; j < length; j++) {
            if (j == (length - 1) && input.length() / 2 == (length - 1)) {
                input = input + "X";
            }
            digraph[j] = input.charAt(2 * j) + "" + input.charAt(2 * j + 1);
        }
```
Then some rules are applied as in function `encodeDigraph`:
- If both the letters are in the same row: Take the letter to the right of each one
- If both the letters are in the same column: Take the letter below each one
- If neither of the above rules is true: Form a rectangle with the two letters and take the 
letters on the horizontal opposite corner of the rectangle.

For decryption, is just the encryption process in the reverse order, as in `decrypt` function

