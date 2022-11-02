package CLIs;

import Ciphers.AsymmetricCiphers.RSA;
import Interfaces.AsymmetricCiphers.IAsymmetricCipher;

import java.math.BigInteger;

public class AsymmetricCipherCLI {
    public static void main(String[] args) {
        IAsymmetricCipher rsa = new RSA();

        BigInteger encrypt = rsa.encrypt("some dummy text here");
        String decrypt = rsa.decrypt(encrypt);

        System.out.println("Encrypted: " + encrypt);
        System.out.println("Decrypted: " + decrypt);
    }
}
