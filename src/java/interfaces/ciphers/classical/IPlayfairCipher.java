package interfaces.ciphers.classical;

public interface IPlayfairCipher {
    public String encrypt(String inputString, String keyInput);

    public String decrypt(String inputString, String keyInput);
}
