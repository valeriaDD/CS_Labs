package cli.ciphers;

import ciphers.block.DES;
import interfaces.ciphers.block.IDESCipher;

public class BlockCipherCLI {
    public static void main(String[] args) {
        IDESCipher des = new DES("76616c6572790d0a");
        String originalText = "6d69696161616177";

        int[] encrypted = des.encrypt(originalText);
        int[] decrypted = des.decrypt("F0C347D84D21ECCD");

        System.out.println("Original text:");
        System.out.println(originalText);

        System.out.println("Encrypted text:");
        displayBits(encrypted);

        System.out.println("Decrypted text:");
        displayBits(decrypted);
    }

    private static void displayBits(int[] bits) {
        for(int i=0 ; i < bits.length ; i+=4) {
            StringBuilder output = new StringBuilder();
            for(int j=0 ; j < 4 ; j++) {
                output.append(bits[i + j]);
            }
            System.out.print(Integer.toHexString(Integer.parseInt(output.toString(), 2)));
        }
        System.out.println();
    }
}
