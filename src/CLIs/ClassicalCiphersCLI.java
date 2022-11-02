package CLIs;

import Ciphers.ClassicalCiphers.AffineCipher;
import Ciphers.ClassicalCiphers.CaesarCipher;
import Ciphers.ClassicalCiphers.PlayfairCipher;
import Ciphers.ClassicalCiphers.VigenereCipher;
import Interfaces.ClassicalCiphers.IAffineCipher;
import Interfaces.ClassicalCiphers.ICaesarCipher;
import Interfaces.ClassicalCiphers.IPlayfairCipher;
import Interfaces.ClassicalCiphers.IVigenereCipher;

import java.util.Scanner;

public class ClassicalCiphersCLI {
    public static void main(String[] args) {
        int cipherType = chooseCipherType();
        while (cipherType > 4 || cipherType < 1) {
            System.out.println("Invalid option! ");
            cipherType = chooseCipherType();
        }

        int actionType = chooseActionType();
        while (actionType > 2 || actionType < 1) {
            System.out.println("Invalid option! ");
            actionType = chooseActionType();
        }

        String cipherOutput = runCipher(cipherType, actionType);

        System.out.println(cipherOutput);
    }

    public static int chooseCipherType() {
        Scanner scan = new Scanner(System.in);

        System.out.println("\nChoose cipher type\n");
        System.out.println("1. Caesar Cipher");
        System.out.println("2. Affine Cipher");
        System.out.println("3. Vigenere Cipher");
        System.out.println("4. Playfair Cipher\n");
        System.out.print("Your choice: ");

        return scan.nextInt();
    }

    public static int chooseActionType() {
        Scanner scan = new Scanner(System.in);

        System.out.println("What do you want to perform?\n");
        System.out.println("1. Encryption");
        System.out.println("2. Decryption");
        System.out.print("Your choice: ");

        return scan.nextInt();
    }

    public static String runCipher(int cipherType, int actionType) {
        if(cipherType == 1) {
            return runCaesarCipher(actionType);
        } else if ( cipherType == 2){
            return runAffineCipher(actionType);
        } else if( cipherType == 3) {
            return runVigenereCipher(actionType);
        } else {
            return runPlayfairCipher(actionType);
        }
    }

    public static String runCaesarCipher(int actionType) {
        Scanner scan = new Scanner(System.in);
        ICaesarCipher cipher = new CaesarCipher();

        System.out.println("Enter the text:");
        System.out.print("Text: ");
        String text = scan.nextLine();

        System.out.println("Enter a key (number format)");
        System.out.print("Key: ");
        int key = scan.nextInt();

        if (actionType == 1) {
            return cipher.encrypt(text, key);
        }

        return cipher.decrypt(text, key);
    }

    public static String runAffineCipher(int actionType) {
        Scanner scan = new Scanner(System.in);
        IAffineCipher cipher = new AffineCipher();

        System.out.println("Enter the text:");
        System.out.print("Text: ");
        String text = scan.nextLine();

        System.out.println("Enter 2 keys (number format)");
        System.out.print("Key 1: ");
        int keyA = scan.nextInt();
        System.out.print("Key 2: ");
        int keyB = scan.nextInt();

        if (actionType == 1) {
            return cipher.encrypt(text, keyA, keyB);
        }

        return cipher.decrypt(text, keyA, keyB);
    }

    public static String runVigenereCipher(int actionType) {
        Scanner scan = new Scanner(System.in);
        IVigenereCipher cipher = new VigenereCipher();

        System.out.println("Enter the text:");
        System.out.print("Text: ");
        String text = scan.nextLine();

        System.out.println("Enter a key (text format)");
        System.out.print("Key: ");
        String key = scan.nextLine();

        if (actionType == 1) {
            return cipher.encrypt(text, key);
        }

        return cipher.decrypt(text, key);
    }

    public static String runPlayfairCipher(int actionType) {
        Scanner scan = new Scanner(System.in);
        IPlayfairCipher cipher = new PlayfairCipher();

        System.out.println("Enter the text:");
        System.out.print("Text: ");
        String text = scan.nextLine();

        System.out.println("Enter a key (text format)");
        System.out.print("Key: ");
        String key = scan.nextLine();

        if (actionType == 1) {
            return cipher.encrypt(text, key);
        }

        return cipher.decrypt(text, key);
    }
}
