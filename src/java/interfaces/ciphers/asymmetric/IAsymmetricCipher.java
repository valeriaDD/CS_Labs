package interfaces.ciphers.asymmetric;

import java.math.BigInteger;

public interface IAsymmetricCipher {
    BigInteger encrypt(String message);

    String decrypt(BigInteger encryptedMessage);
}
