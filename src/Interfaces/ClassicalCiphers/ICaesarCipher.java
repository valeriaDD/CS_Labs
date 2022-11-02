package Interfaces.ClassicalCiphers;

public interface ICaesarCipher {
    public String encrypt(String inputString, int key);

    public String decrypt(String inputString, int key);
}
