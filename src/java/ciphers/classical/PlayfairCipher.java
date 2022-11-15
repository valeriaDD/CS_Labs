package ciphers.classical;

import interfaces.ciphers.classical.IPlayfairCipher;

import java.awt.*;

public class PlayfairCipher implements IPlayfairCipher {
    private int length = 0;
    private String[][] table;

    public String encrypt(String inputString, String keyInput) {
        String key = parseString(keyInput);
        String input = parseString(inputString);
        StringBuilder outputString = new StringBuilder();

        table = this.cipherTable(key);
        this.keyTable(table);

        length = input.length() / 2 + input.length() % 2;

        for (int i = 0; i < (length - 1); i++) {
            if (input.charAt(2 * i) == input.charAt(2 * i + 1)) {
                input = new StringBuffer(input).insert(2 * i + 1, 'X').toString();
                length = input.length() / 2 + input.length() % 2;
            }
        }

        String[] digraph = new String[length];
        for (int j = 0; j < length; j++) {
            if (j == (length - 1) && input.length() / 2 == (length - 1)) {
                input = input + "X";
            }
            digraph[j] = input.charAt(2 * j) + "" + input.charAt(2 * j + 1);
        }

        String[] encDigraphs = encodeDigraph(digraph);
        for (int k = 0; k < length; k++) {
            outputString.append(encDigraphs[k]);
        }

        return outputString.toString();
    }

    public String decrypt(String inputString, String keyInput) {
        String key = parseString(keyInput);
        String out = parseString(inputString);

        table = this.cipherTable(key);
        this.keyTable(table);

        StringBuilder decoded = new StringBuilder();
        for (int i = 0; i < out.length() / 2; i++) {
            char a = out.charAt(2 * i);
            char b = out.charAt(2 * i + 1);
            int r1 = (int) getPoint(a).getX();
            int r2 = (int) getPoint(b).getX();
            int c1 = (int) getPoint(a).getY();
            int c2 = (int) getPoint(b).getY();
            if (r1 == r2) {
                c1 = (c1 + 4) % 5;
                c2 = (c2 + 4) % 5;
            } else if (c1 == c2) {
                r1 = (r1 + 4) % 5;
                r2 = (r2 + 4) % 5;
            } else {
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }
            decoded.append(table[r1][c1]).append(table[r2][c2]);
        }
        return decoded.toString();
    }

    private String parseString(String parse) {
        return parse.toUpperCase()
                .replaceAll("[^A-Z]", "")
                .replace("J", "I");
    }

    private String[][] cipherTable(String key) {
        String[][] playfairTable = new String[5][5];
        String keyString = key + "ABCDEFGHIKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                playfairTable[i][j] = "";

        for (int k = 0; k < keyString.length(); k++) {
            boolean repeat = false;
            boolean used = false;

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (playfairTable[i][j].equals("" + keyString.charAt(k))) {
                        repeat = true;
                    } else if (playfairTable[i][j].equals("") && !repeat && !used) {
                        playfairTable[i][j] = "" + keyString.charAt(k);
                        used = true;
                    }
                }
            }
        }
        return playfairTable;
    }

    private String[] encodeDigraph(String[] di) {
        String[] encipher = new String[length];

        for (int i = 0; i < length; i++) {
            char a = di[i].charAt(0);
            char b = di[i].charAt(1);
            int r1 = (int) getPoint(a).getX();
            int r2 = (int) getPoint(b).getX();
            int c1 = (int) getPoint(a).getY();
            int c2 = (int) getPoint(b).getY();

            if (r1 == r2) {
                c1 = (c1 + 1) % 5;
                c2 = (c2 + 1) % 5;
            } else if (c1 == c2) {
                r1 = (r1 + 1) % 5;
                r2 = (r2 + 1) % 5;
            } else {
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }

            encipher[i] = table[r1][c1] + "" + table[r2][c2];
        }
        return encipher;
    }

    private Point getPoint(char c) {
        Point pt = new Point(0, 0);
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (c == table[i][j].charAt(0))
                    pt = new Point(i, j);
        return pt;
    }

    private void keyTable(String[][] printTable) {
        System.out.println("Playfair Cipher Key Matrix: ");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(printTable[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}