package Tests.ClassicalCiphers;

import Ciphers.ClassicalCiphers.PlayfairCipher;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayfairCipherTest {
    @Test
    public void playfairEncryptMessageWithNoSpaces() {
        PlayfairCipher playfairCipher = new PlayfairCipher();
        String toEncrypt = "caniaskyousomething";
        String encrypted = "BITNLOQWSVOMEPARNTUG";
        String key = "someplaintext";

        assertEquals(encrypted,playfairCipher.encrypt(toEncrypt, key));
    }

    @Test
    public void playfairEncryptMessageWithSpaces() {
        PlayfairCipher playfairCipher = new PlayfairCipher();
        String toEncrypt = "can i ask you something";
        String encrypted = "FUOHBALXNPPQKGNMHOEZ";
        String key = "ups";

        assertEquals(encrypted,playfairCipher.encrypt(toEncrypt, key));
    }

    @Test
    public void playfairEncryptMessageWithUpperAndLowerCase() {
        PlayfairCipher playfairCipher = new PlayfairCipher();
        String toEncrypt = "Someplaintext Someplaintext Someplaintext Someotherplaintext Someotherplaintext Someotherplaintext";
        String encrypted = "QESFUBONTZVYPTEILSOLKHSAZRQESFUBONTZVYPTEIAVPNVSUBONTZVYPTEIAVPNVSUBONTZVYPTEIAVPNVSUBONTZVYRZ";
        String key = "love";

        assertEquals(encrypted,playfairCipher.encrypt(toEncrypt, key));
    }

    @Test
    public void playfairDecryptMessageWithNoSpaces() {
        PlayfairCipher playfairCipher = new PlayfairCipher();
        String decrypted = "SOMEPLAINTEXTSOMEPLAINTEXT";
        String encrypted = "TNLFQKCFOSFVPTKNDQMHGOQIZR";
        String key = "wha";

        assertEquals(decrypted,playfairCipher.decrypt(encrypted, key));
    }

    @Test
    public void vigenereDecryptMessageWithUpperAndLowerCase() {
        PlayfairCipher playfairCipher = new PlayfairCipher();
        String decrypted =  "CANIASKYOUSOMETHINGX";
        String encrypted = "AHMFBaIZEPGsENAfFMHW";
        String key = "nocommentsnocommentsnocommentsnocomments";

        assertEquals(decrypted,playfairCipher.decrypt(encrypted, key));
    }
}
