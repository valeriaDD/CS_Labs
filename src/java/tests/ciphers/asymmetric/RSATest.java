package tests.ciphers.asymmetric;

import ciphers.asymmetric.RSA;
import interfaces.ciphers.asymmetric.IAsymmetricCipher;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RSATest {

    @Test
    public void encryptionAndDecryptionShouldPass() {
        IAsymmetricCipher rsa = new RSA();
        String plaintext = "Some Text";

        BigInteger encrypted = rsa.encrypt(plaintext);
        String decrypted = rsa.decrypt(encrypted);

        assertEquals(plaintext.toUpperCase(), decrypted);
    }

    @Test
    public void decryptionWithAnotherRsaShouldFail() {
        IAsymmetricCipher rsa = new RSA();
        IAsymmetricCipher rsa2 = new RSA();
        String plaintext = "Some Text";

        BigInteger encrypted = rsa.encrypt(plaintext);
        String decrypted = rsa2.decrypt(encrypted);

        assertNotEquals(plaintext.toUpperCase(), decrypted);
    }

    @Test
    public void encryptionAndDecryptionOfNumbersShouldPass() {
        IAsymmetricCipher rsa = new RSA();
        String plaintext = "22";

        BigInteger encrypted = rsa.encrypt(plaintext);
        String decrypted = rsa.decrypt(encrypted);

        assertEquals(plaintext.toUpperCase(), decrypted);
    }

    @Test
    public void encryptionAndDecryptionOfSpecialCharactersShouldPass() {
        IAsymmetricCipher rsa = new RSA();
        String plaintext = "!@#$$$$$$%^&*()_:;+=";

        BigInteger encrypted = rsa.encrypt(plaintext);
        String decrypted = rsa.decrypt(encrypted);

        assertEquals(plaintext.toUpperCase(), decrypted);
    }
}
