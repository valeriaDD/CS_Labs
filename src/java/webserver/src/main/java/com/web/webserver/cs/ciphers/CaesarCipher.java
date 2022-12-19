package com.web.webserver.cs.ciphers;


public class CaesarCipher  {
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public String encrypt(String inputString, int key) {
        inputString = inputString.toLowerCase();
        StringBuilder encryptStr = new StringBuilder();

        for (int i = 0; i < inputString.length(); i++) {
            char inputChar = inputString.charAt(i);
            if (!String.valueOf(inputChar).equals(" ")) {
                int pos = ALPHABET.indexOf(inputChar);
                int encryptPos = (key + pos) % 26;
                char encryptChar = ALPHABET.charAt(encryptPos);
                encryptStr.append(encryptChar);
            } else {
                encryptStr.append(" ");
            }

        }

        return encryptStr.toString();
    }

    public String decrypt(String inputString, int key) {
        inputString = inputString.toLowerCase();
        StringBuilder decryptStr = new StringBuilder();

        for (int i = 0; i < inputString.length(); i++) {
            char inputChar = inputString.charAt(i);

            if (!String.valueOf(inputChar).equals(" ")) {

                int pos = ALPHABET.indexOf(inputChar);
                int decryptPos = (pos - key) % 26;
                if (decryptPos < 0) {
                    decryptPos = ALPHABET.length() + decryptPos;
                }

                char decryptChar = ALPHABET.charAt(decryptPos);
                decryptStr.append(decryptChar);
            } else {
                decryptStr.append(" ");
            }

        }

        return decryptStr.toString();
    }
}
