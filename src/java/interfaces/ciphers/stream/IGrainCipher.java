package interfaces.ciphers.stream;

public interface IGrainCipher {
    public String encrypt(String input);

    public String decrypt(String cipher);
}
