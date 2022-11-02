package Tests.ClassicalCiphers;

import static org.junit.Assert.assertEquals;

import Ciphers.ClassicalCiphers.CaesarCipher;
import org.junit.Test;


public class CaesarCipherTest {

    @Test
    public void caesarEncryptMessageWithNoSpaces() {
        CaesarCipher caesarCipher = new CaesarCipher();
        String toEncrypt = "helloworld";
        String encrypted = "mjqqtbtwqi";
        int key = 5;

        assertEquals(encrypted,caesarCipher.encrypt(toEncrypt, key));
    }

    @Test
    public void caesarEncryptMessageWithSpaces() {
        CaesarCipher caesarCipher = new CaesarCipher();
        String toEncrypt = "hello world";
        String encrypted = "mjqqt btwqi";
        int key = 5;

        assertEquals(encrypted,caesarCipher.encrypt(toEncrypt, key));
    }

    @Test
    public void caesarEncryptMessageWithUpperAndLowerCase() {
        CaesarCipher caesarCipher = new CaesarCipher();
        String toEncrypt = "HElloWorlD";
        String encrypted = "mjqqtbtwqi";
        int key = 5;

        assertEquals(encrypted,caesarCipher.encrypt(toEncrypt, key));
    }

    @Test
    public void caesarDecryptMessageWithNoSpaces() {
        CaesarCipher caesarCipher = new CaesarCipher();
        String decrypted = "helloworld";
        String encrypted = "mjqqtbtwqi";
        int key = 5;

        assertEquals(decrypted,caesarCipher.decrypt(encrypted, key));
    }

    @Test
    public void caesarDecryptMessageWithSpaces() {
        CaesarCipher caesarCipher = new CaesarCipher();
        String decrypted =  "hello world";
        String encrypted = "mjqqt btwqi";
        int key = 5;

        assertEquals(decrypted,caesarCipher.decrypt(encrypted, key));
    }

    @Test
    public void caesarDecryptMessageWithUpperAndLowerCase() {
        CaesarCipher caesarCipher = new CaesarCipher();
        String decrypted =  "helloworld";
        String encrypted = "MjqqtBtwqi";
        int key = 5;

        assertEquals(decrypted,caesarCipher.decrypt(encrypted, key));
    }
}
