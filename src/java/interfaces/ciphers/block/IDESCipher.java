package interfaces.ciphers.block;

public interface IDESCipher {
    public int[] encrypt(String text);

    public int[] decrypt(String text);
}
