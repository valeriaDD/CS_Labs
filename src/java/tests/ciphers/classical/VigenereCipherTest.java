package tests.ciphers.classical;

import ciphers.classical.VigenereCipher;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VigenereCipherTest {
    @Test
    public void vigenereEncryptMessageWithNoSpaces() {
        VigenereCipher vigenereCipher = new VigenereCipher();
        String toEncrypt = "helloworld";
        String encrypted = "TMLHAEONXL";
        String key = "miaw";

        assertEquals(encrypted,vigenereCipher.encrypt(toEncrypt, key));
    }

    @Test
    public void vigenereEncryptMessageWithSpaces() {
        VigenereCipher vigenereCipher = new VigenereCipher();
        String toEncrypt = "HElloWorlD";
        String encrypted = "TMLHA  EONXL";
        String key = "miawmiawmiawmiaw";

        assertEquals(encrypted,vigenereCipher.encrypt(toEncrypt, key));
    }

    @Test
    public void vigenereEncryptMessageWithUpperAndLowerCase() {
        VigenereCipher vigenereCipher = new VigenereCipher();
        String toEncrypt = "HElloWorlD";
        String encrypted = "TMLHEMEDTD";
        String key = "miawqqq";

        assertEquals(encrypted,vigenereCipher.encrypt(toEncrypt, key));
    }

    @Test
    public void vigenereDecryptMessageWithNoSpaces() {
        VigenereCipher vigenereCipher = new VigenereCipher();
        String decrypted = "HELLOFROMTHEOTHERSIDE";
        String encrypted = "dllegzgkttawiidlrlaxt";
        String key = "whatsup";

        assertEquals(decrypted,vigenereCipher.decrypt(encrypted, key));
    }

    @Test
    public void vigenereDecryptMessageWithSpaces() {
        VigenereCipher vigenereCipher = new VigenereCipher();
        String decrypted =  "HELLO FROM THE OTHER SIDE";
        String encrypted = "useec irmz hax cwhce gbws";
        String key = "nottoday";

        assertEquals(decrypted,vigenereCipher.decrypt(encrypted, key));
    }

    @Test
    public void vigenereDecryptMessageWithUpperAndLowerCase() {
        VigenereCipher vigenereCipher = new VigenereCipher();
        String decrypted =  "HELLO FROM THE OTHER SIDE";
        String encrypted = "usnza Rvbf lus qHtqv fbvr";
        String key = "nocommentsnocommentsnocommentsnocomments";

        assertEquals(decrypted,vigenereCipher.decrypt(encrypted, key));
    }
}
