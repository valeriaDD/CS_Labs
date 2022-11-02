package CLIs;

import Ciphers.AsymmetricCiphers.RSA;
import Interfaces.AsymmetricCiphers.IAsymmetricCipher;

import java.math.BigInteger;
import java.util.Scanner;

public class AsymmetricCipherCLI {
    public static void main(String[] args) {
        IAsymmetricCipher rsa = new RSA();
        Scanner scan = new Scanner(System.in);

        System.out.println("RSA encryption: the q and p numbers remain private");
        System.out.println("Enter the plain text:");

        String plaintext = scan.nextLine();


        BigInteger encrypt = rsa.encrypt(plaintext);
        String decrypt = rsa.decrypt(encrypt);

        System.out.println("Encrypted: " + encrypt);
        System.out.println("Decrypted: " + decrypt);
    }
}
