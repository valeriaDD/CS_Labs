package Interfaces.ClassicalCiphers;

public interface IPlayfairCipher {
    public String encrypt(String inputString, String keyInput);

    public String decrypt(String inputString, String keyInput);
}
