package Ciphers.ClassicalCiphers;


import Interfaces.ClassicalCiphers.IVigenereCipher;

public class VigenereCipher implements IVigenereCipher {

    public String encrypt(String text, String key) {
        StringBuilder res = new StringBuilder();
        text = text.toUpperCase();
        key = key.toUpperCase();

        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (!String.valueOf(c).equals(" ")) {
                if (c < 'A' || c > 'Z')
                    continue;
                res.append((char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A'));
                j = ++j % key.length();

            } else {
                res.append(" ");
            }

        }
        return res.toString();
    }

    public String decrypt(String text, String key) {
        StringBuilder res = new StringBuilder();
        text = text.toUpperCase();
        key = key.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++)  {
            char c = text.charAt(i);

            if (!String.valueOf(c).equals(" ")) {
                if (c < 'A' || c > 'Z')
                    continue;
                res.append((char) ((c - key.charAt(j) + 26) % 26 + 'A'));
                j = ++j % key.length();
            } else {
                res.append(" ");
            }
        }
        return res.toString();
    }
}
