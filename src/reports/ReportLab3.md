# Topic: Topic: Asymmetric ciphers.

## Course: Cryptography & Security

### Author: Dubina Valeria

----

## Theory

Asymmetric Encryption uses two distinct keys. One key, the Public Key, is used 
for encryption and the other, the Private Key, is for decryption. As implied in the name, the 
Private Key is intended to be private so that only the authenticated recipient can decrypt the
message.

This algorithm uses a key generation protocol (a kind of mathematical function) to generate a
key pair. Both the keys are mathematically connected with each other. This relationship between
the keys differs from one algorithm to another. In this particular Laboratory work RSA algorithm 
is implemented

## Objectives

1. Get familiar with the asymmetric cryptography mechanisms.

2. Implement an example of an asymmetric cipher.

3. As in the previous task, please use a client class or test classes to showcase the execution of your programs.

## Implementation description:

### RSA

RSA algorithm is an asymmetric cryptography algorithm. The idea of RSA is based on the fact 
that it is difficult to factorize a large integer. The public key consists of two numbers 
where one number is a multiplication of two large prime numbers. And private key is also 
derived from the same two prime numbers. So if somebody can factorize the large number, 
the private key is compromised. Therefore encryption strength totally lies on the key size 
and if we double or triple the key size, the strength of encryption increases exponentially.

**Mechanism:**

Select two prime numbers:
```java
BigInteger p = largePrime();
BigInteger q = largePrime();
```
Compute the first part of the Public key - N , that is the product of p and q:
```java
this.N = p.multiply(q);
``` 
We need to calculate a number $\phi$(n) such that $\phi$(n)  = (p-1)(q-1):
```java
this.Fn = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
```

Compute a small exponent say `e` that must be an integer and not a factor of N, 
bigger than 1 and smaller than $\phi$(n). So we can say that we have to find a number that satisfies the condition:
gcd(e , $\phi$(n) ) = 1

`N` and `e` for the part of the public key
```java
private BigInteger computeE() {
        Random rand = new Random();
        BigInteger e;
        
        do {
            e = new BigInteger(1024, rand);

            while (e.min(this.Fn).equals(this.Fn)) {
                e = new BigInteger(1024, rand);
            }
        } while (!Objects.equals(gcd(e, this.Fn), BigInteger.ONE));

        return e;
    }
```

For the Private key part, we should calculate a number `d` that should satisfy the following
condition: e*d mod $\phi$ (n) = 1. To compute such number is used the extended Euclid algorithm.

```java
this.d = this.extendedEuclid(e, Fn)[1];

private BigInteger[] extendedEuclid(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO))
            return new BigInteger[]{a, BigInteger.ONE, BigInteger.ZERO};

        BigInteger[] values = extendedEuclid(b, a.mod(b));

        BigInteger d = values[0];
        BigInteger p = values[2];
        BigInteger q = values[1].subtract(a.divide(b).multiply(values[2]));

        return new BigInteger[]{d, p, q};
    }
```

The encryption and decryption uses mathematical formulas:
Encryption: TEXT_TO_ASCII_INTEGER$^{e \mod n}$
```java
 public BigInteger encrypt(String message) {
        BigInteger cipherMessage = stringToAsciiBigInteger(message);

        return cipherMessage.modPow(this.e, this.N);
    }
```
Decryption: TEXT_TO_ASCII_INTEGER$^{d \mod n}$
```java
public String decrypt(BigInteger encryptedMessage) {
        BigInteger decryptedMessage = encryptedMessage.modPow(this.d, this.N);

        return this.bigIntegerAsciiToString(decryptedMessage);
    }
```

To see how algorithm works, see the `RSATest` or `RsaCLI` classes.