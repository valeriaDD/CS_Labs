package tests.ciphers.classical;

import ciphers.classical.AffineCipher;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AffineCipherTest {

    @Test
    public void affineEncryptMessageWithNoSpaces() {
        AffineCipher affineCipher = new AffineCipher();
        String toEncrypt = "HELLOWORLD";
        String encrypted = "XOJJSQSBJL";
        int keyA = 3;
        int keyB = 2;

        assertEquals(encrypted,affineCipher.encrypt(toEncrypt, keyA, keyB));
    }

    @Test
    public void affineEncryptMessageWithSpaces() {
        AffineCipher affineCipher = new AffineCipher();
        String toEncrypt = "hello world";
        String encrypted = "ESUUG MGSUO";
        int keyA = 4;
        int keyB = 2;

        assertEquals(encrypted,affineCipher.encrypt(toEncrypt, keyA, keyB));
    }

    @Test
    public void affineEncryptMessageWithUpperAndLowerCase() {
        AffineCipher affineCipher = new AffineCipher();
        String toEncrypt = "HElloWorlD";
        String encrypted = "XOJJSQSBJL";
        int keyA = 3;
        int keyB = 2;

        assertEquals(encrypted,affineCipher.encrypt(toEncrypt, keyA, keyB));
    }

    @Test
    public void affineDecryptMessageWithNoSpaces() {
        AffineCipher affineCipher = new AffineCipher();
        String encrypted = "XOJJSQSBJL";
        String decrypted = "HELLOWORLD";
        int keyA = 3;
        int keyB = 2;

        assertEquals(decrypted,affineCipher.decrypt(encrypted, keyA, keyB));
    }

    @Test
    public void affineDecryptMessageWithSpaces() {
        AffineCipher affineCipher = new AffineCipher();
        String decrypted =  "HELLO WORLD";
        String encrypted = "YPKKT RTCKM";
        int keyA = 3;
        int keyB = 3;

        assertEquals(decrypted,affineCipher.decrypt(encrypted, keyA, keyB));
    }

    @Test
    public void affineDecryptMessageWithUpperAndLowerCase() {
        AffineCipher affineCipher = new AffineCipher();
        String decrypted =  "HELLOWORLD";
        String encrypted = "XoJJsQSBjL";
        int keyA = 3;
        int keyB = 2;

        assertEquals(decrypted,affineCipher.decrypt(encrypted, keyA, keyB));
    }
}
