package cli.ciphers;

import ciphers.stream.GrainCipher;
import interfaces.ciphers.stream.IGrainCipher;


public class StreamCipherCLI {
    public static void main(String[] args) {
        IGrainCipher grain = new GrainCipher("conflict", "pacemaking");
        String plainText = "EverythingGood";

        String encrypt = grain.encrypt(plainText);
        String decrypted = grain.decrypt(encrypt);


        System.out.println("Original text:");
        System.out.println(plainText);

        System.out.println("Encrypted text:");
        System.out.println(encrypt);

        System.out.println("Decrypted text:");
        System.out.println(decrypted);
    }
}
