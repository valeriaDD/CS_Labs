# Topic: Topic: Hash functions and Digital Signatures.

## Course: Cryptography & Security

### Author: Dubina Valeria

----

## Theory

Hashing is a technique used to compute a new representation of an existing value, message or any piece of text.
The new representation is also commonly called a digest of the initial text, and it is a one way function meaning that
it should be impossible to retrieve the initial content from the digest.

Such a technique has the following usages:

1. Offering confidentiality when storing passwords

2. Checking for integrity for some downloaded files or content

3. Creation of digital signatures, which provides integrity and non-repudiation. In order to create digital signatures,
   the initial message or text needs to be hashed to get the digest.
   After that, the digest is to be encrypted using a public key encryption cipher.
   Having this, the obtained digital signature can be decrypted with the public key and the hash can be compared with
   an additional hash computed from the received message to check the integrity of it.

## Objectives

1. Get familiar with the hashing techniques/algorithms.

2. Use an appropriate hashing algorithms to store passwords in a local DB.

3. Use an asymmetric cipher to implement a digital signature process for a user message.
   - Take the user input message.
   - Preprocess the message, if needed.
   - Get a digest of it via hashing.
   - Encrypt it with the chosen cipher.
   - Perform a digital signature check by comparing the hash of the message with the decrypted one.

## Implementation description:

Database class is HashMap that simulates the database by linking an unique user email to an User (Record) class.
A user has `email`, `password`, and `public key` entries and a method `signMessage`.

The `Database` class has methods `addUser` and `getUser` that are used by the `UserManagementService` that stores
methods that register, authenticate users and verifies digital signatures.

# SHA-256

The **SHA** (Secure Hash Algorithm) is one of a number of cryptographic hash functions. A cryptographic hash is like a
signature for a data set. SHA256 algorithm generates an almost-unique,
fixed size 256-bit (32-byte) hash. Hash is so called a one way function. This makes it suitable for checking integrity
of your data, challenge hash authentication as in this project case.

For user registration the password is hashed before storing in the database.

The java.security package provides a class, i.e., MessageDigest, that supports algorithms such as SHA-1,
SHA 256, and MD5 etc., for converting a message of arbitrary length to a message digest.

 In the first step, we will create an instance of the MessageDigest by using the getInstance() method of
the MessageDigest The getInstance() method accepts a parameter, i.e., algo, which defines the algorithm to be used.
The getInstance() method returns a MessageDigest object implementing the specified algorithm.

```
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
```
In the next step, we will use the digest() method of the MessageDigest class to generate the message digest.
The digest() method is responsible for computing the hash function on the current object. The digest() method returns
the message digest in the form of the byte array.
```
        String hashedPassword = new String(messageDigest.digest(password.getBytes()));
```

To verify the password for authentication, in UserManagementService class, the same algorithm is used, we instantiate the MessageDigest class with the
specified algorithm, then we retrieve user's password from the database - if the hashes are equal - everything is ok and
the user can authenticate.
```
    public boolean isAuthenticationAllowed(String email, String password) throws Exception {
        User user = database.getUser(email);
                    ...
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        String hashedPassword = new String(messageDigest.digest(password.getBytes()));

        return user.getPasswordHash().equals(hashedPassword);
    }
```

# Digital signature

Core concept of digital signature revolves around, computing signature on the sender side using PrivateKey applied on
hash of the message(M), sending original message and computed signature to receiver. Receiver verifies the signature
using PublicKey. If signatures match, non- repudiation, authenticity and integrity of message from intended sender has
been verified.

Encryption/Decryption post, would need to be implemented upon KeyPairGenerator class to
 generate asymmetric keys using RSA algorithm. The `initialize` function sets the key size.
 ```
        this.keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        this.keyPairGenerator.initialize(2048);
 ```
 Using the KeyPair class, we generate the public and private keys. The public key is store in the database while
 the public key is returned to the user.
 ```
     public PrivateKey addUser(String email, String password) throws Exception {
                                ...
         KeyPair keyPair = keyPairGenerator.generateKeyPair();
                                ...
         User user = new User(email, hashedPassword, keyPair.getPublic());

         this.database.addUser(email, user);

         return keyPair.getPrivate();
     }
 ```

 Having the private key user can send signed messages. Java provides the Signature class that can be used to create a digital signature.
 For instantiations of the signature class we have mentioned the signature algorithm name with the message-digest
 algorithm that is used by the signature algorithm `SHA256withRSA` in our case.

 ```
        Signature signature = Signature.getInstance("SHA256withRSA");
 ```
 The signature object must be initialized before using it. The initialization method for signing requires a private key.

 ```
         signature.initSign(privateKey);
 ```
 In order to supply the data, we use the update() method provided by the Signature class that takes the input an array of
 bytes.
```
        signature.update(plaintext.getBytes());
```
When we supplied all the data to the Signature object, it allows us to generate a digital signature for that data.
```
    public byte[] signMessage(String plaintext, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(plaintext.getBytes());

        return signature.sign();
    }
```

Using this signature the plaintext can be send to others. The receiver of the message using the signature verifies the integrity of it
using the public key.

First we have to initialize the Signature class object, mentioning the algorithms used for creation of the signature in
the first place `SHA256withRSA`. The initialization method for verification requires a public key object as a parameter.
Now we can supply the message to the signature class using update method. The function that performs the verification is
`verify` that returns a boolean value.

```
    public boolean isCorrectSignature(String email, String message, byte[] signature) throws Exception {
        User user = database.getUser(email);
                ...
        Signature ecdsaSignature = Signature.getInstance("SHA256withRSA");
        ecdsaSignature.initVerify(user.getPublicKey());
        ecdsaSignature.update(message.getBytes());

        return ecdsaSignature.verify(signature);
    }
```

To make sure that everything works well, some unit tests were created in `tests/digitalsignature` package.