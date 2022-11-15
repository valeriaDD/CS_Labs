package interfaces.ciphers.classical;

public interface IAffineCipher {
    public String encrypt(String inputString, int keyA, int keyB);

    public String decrypt(String inputString, int keyA, int keyB);
}
