package ciphers.classical;

import interfaces.ciphers.classical.IAffineCipher;

import java.math.BigInteger;

public class AffineCipher implements IAffineCipher {

    public String encrypt(String input, int keyA, int keyB) {
        input = input.toUpperCase();
        StringBuilder builder = new StringBuilder();

        for (int in = 0; in < input.length(); in++) {
            char character = input.charAt(in);

            if (Character.isLetter(character)) {
                character = (char) ((keyA * (character - 'A') + keyB) % 26 + 'A');
            }
            builder.append(character);
        }
        return builder.toString();
    }

    public String decrypt(String input, int keyA, int keyB) {
        input = input.toUpperCase();
        StringBuilder builder = new StringBuilder();
        BigInteger inverse = BigInteger.valueOf(keyA).modInverse(BigInteger.valueOf(26));

        for (int in = 0; in < input.length(); in++) {
            char character = input.charAt(in);

            if (Character.isLetter(character)) {
                int decoded = inverse.intValue() * (character - 'A' - keyB + 26);
                character = (char) (decoded % 26 + 'A');
            }
            builder.append(character);
        }
        return builder.toString();
    }
}