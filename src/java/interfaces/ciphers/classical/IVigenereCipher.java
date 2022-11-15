package interfaces.ciphers.classical;

public interface IVigenereCipher {
    public String encrypt(String text, String key);

    public String decrypt(String text, String key);
}
