package ciphers.stream;

import interfaces.ciphers.stream.IGrainCipher;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GrainCipher implements IGrainCipher {

    Byte[] lfsr = new Byte[80];
    Byte[] nfsr = new Byte[80];
    String keyStream;
    private final Byte[] temp_lfsr;
    private final Byte[] temp_nfsr;
    private final Byte[] filter;

    public GrainCipher(String iv, String key) {
        this.temp_lfsr = new Byte[160];
        this.temp_nfsr = new Byte[160];
        this.filter = new Byte[160];

        String iv_result = this.stringToBinary(iv);

        for (int i = 0; i < iv_result.length(); i++) {
            lfsr[i] = Byte.parseByte(String.valueOf(iv_result.charAt(i)));
        }

        for (int i = 64; i < 80; i++) {
            lfsr[i] = 1;
        }

        String result2 = this.stringToBinary(key);

        for (int i = 0; i < result2.length(); i++) {
            this.nfsr[i] = Byte.parseByte(String.valueOf(result2.charAt(i)));
        }

        this.lfsr();
        this.nfsr();
        Byte x0 = this.temp_lfsr[3];
        Byte x1 = this.temp_lfsr[25];
        Byte x2 = this.temp_lfsr[46];
        Byte x3 = this.temp_lfsr[64];
        Byte x4 = this.temp_nfsr[63];
        Byte f_h = (byte) (x1 ^ x4 ^ (x0 & x3) ^ (x2 & x3) ^ (x3 & x3) ^ (x0 & x1 & x2)
                ^ (x0 & x2 & x3) ^ (x0 & x2 & x4) ^ (x1 & x2 & x4) ^ (x2 & x3 & x4));

        StringBuilder string_filter = new StringBuilder();
        for (int i = 0; i < 160; i++) {
            this.filter[i] = (byte) (this.temp_nfsr[i] ^ f_h);
            string_filter.append(this.filter[i]);
        }

        this.keyStream =  this.stringBinaryToHex(string_filter.toString());
    }

    public String stringToBinary(String string) {
        StringBuilder result = new StringBuilder();
        String tmpStr;
        int tmpInt;
        char[] messChar = string.toCharArray();

        for (char c : messChar) {
            tmpStr = Integer.toBinaryString(c);

            tmpInt = tmpStr.length();
            if (tmpInt != 8) {
                tmpInt = 8 - tmpInt;
                if (tmpInt == 8) {
                    result.append(tmpStr);
                } else if (tmpInt > 0) {
                    result.append("0".repeat(tmpInt));
                    result.append(tmpStr);
                } else {
                    System.err.println("argument 'bits' is too small");
                }
            } else {
                result.append(tmpStr);
            }
        }

        return result.toString();
    }

    public String stringBinaryToHex(String string) {
        return new BigInteger(string, 2).toString(16);
    }

    public String hexToBinary(String hex) {
        StringBuilder binStrBuilder = new StringBuilder();
        for (int i = 0; i < hex.length() - 1; i += 2) {
            String output = hex.substring(i, (i + 2));

            int decimal = Integer.parseInt(output, 16);

            String binStr = Integer.toBinaryString(decimal);
            int len = binStr.length();
            StringBuilder sbf = new StringBuilder();
            if (len < 8) {

                sbf.append("0".repeat((8 - len)));
                sbf.append(binStr);
            } else {
                sbf.append(binStr);
            }

            binStrBuilder.append(sbf.toString());
        }

        return binStrBuilder.toString();
    }

    public void lfsr() {
        Byte[] data = this.lfsr;
        Byte xor;

        for (int i = 1; i <= 160; i++) {
            xor = (byte) (data[62] ^ data[51] ^ data[38] ^ data[23] ^ data[13] ^ data[0]);
            List<Byte> data_list = Arrays.asList(data);
            Collections.rotate(data_list, 1);
            this.temp_lfsr[i - 1] = data_list.get(0);
            data_list.set(0, xor);
        }

    }

    public void nfsr() {
        Byte[] data = this.nfsr;
        Byte xor;

        for (int i = 1; i < 161; i++) {
            xor = (byte) (data[0] ^ data[63] ^ data[60]
                    ^ data[52] ^ data[45] ^ data[37]
                    ^ data[33] ^ data[28] ^ data[21]
                    ^ data[15] ^ data[19] ^ data[0]
                    ^ (data[63] & data[60]) ^ (data[37] & data[33])
                    ^ (data[15] & data[9]) ^ (data[60] & data[52] & data[45])
                    ^ (data[33] & data[28] & data[21])
                    ^ (data[63] & data[45] & data[28] & data[9])
                    ^ (data[60] & data[52] & data[37] & data[33])
                    ^ (data[63] & data[60] & data[21] & data[15])
                    ^ (data[63] & data[60] & data[52] & data[45] & data[37])
                    ^ (data[33] & data[28] & data[21] & data[15] & data[9])
                    ^ (data[52] & data[45] & data[37] & data[33] & data[28] & data[21]));
            List<Byte> data_list = Arrays.asList(data);
            Collections.rotate(data_list, 1);
            this.temp_nfsr[i - 1] = data_list.get(0);
            data_list.set(0, xor);
        }

    }

    public String encrypt(String input) {
        String result = this.stringToBinary(input);
        StringBuilder result_string = new StringBuilder();
        Byte[] result_array = new Byte[result.length()];
        Byte[] result_xor_array = new Byte[result.length()];

        for (int i = 0; i < result_array.length; i++) {
            result_array[i] = Byte.parseByte(String.valueOf(result.charAt(i)));
            result_xor_array[i] = (byte) (this.filter[i] ^ result_array[i]);
            result_string.append(result_xor_array[i]);
        }
        return this.stringBinaryToHex(result_string.toString());
    }

    public String decrypt(String cipher) {
        String a = this.hexToBinary(cipher);
        Byte[] a_array = new Byte[a.length()];

        String b = this.hexToBinary(this.keyStream);
        Byte[] b_array = new Byte[b.length()];

        StringBuilder plain_binary = new StringBuilder();
        StringBuilder plain = new StringBuilder();
        Byte[] hasil = new Byte[a.length()];

        for (int i = 0; i < a_array.length; i++) {
            a_array[i] = Byte.parseByte(String.valueOf(a.charAt(i)));
        }

        for (int i = 0; i < b_array.length; i++) {
            b_array[i] = Byte.parseByte(String.valueOf(b.charAt(i)));
        }

        for (int i = 0; i < a.length(); i++) {
            hasil[i] = (byte) (b_array[i] ^ a_array[i]);
            plain_binary.append(hasil[i]);
        }

        for (int i = 0; i <= plain_binary.length() - 8; i += 8) {
            int k = Integer.parseInt(plain_binary.substring(i, i + 8), 2);
            plain.append((char) k);
        }

        return plain.toString();
    }

}
