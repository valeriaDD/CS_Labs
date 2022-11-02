package Ciphers.AsymmetricCiphers;

import Interfaces.AsymmetricCiphers.IAsymmetricCipher;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Random;

public class RSA implements IAsymmetricCipher {
    private final BigInteger Fn;
    private final BigInteger N;
    private final BigInteger e;
    private final BigInteger d;

    public RSA() {
        BigInteger p = largePrime();
        BigInteger q = largePrime();
        this.N = p.multiply(q);
        this.Fn = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        this.e = this.computeE();
        this.d = this.extendedEuclid(e, Fn)[1];
    }

    @Override
    public BigInteger encrypt(String message) {
        BigInteger cipherMessage = stringToAsciiBigInteger(message);

        return cipherMessage.modPow(this.e, this.N);
    }

    @Override
    public String decrypt(BigInteger encryptedMessage) {
        BigInteger decryptedMessage = encryptedMessage.modPow(this.d, this.N);

        return this.bigIntegerAsciiToString(decryptedMessage);
    }

    private BigInteger largePrime() {
        Random randomInteger = new Random();

        return BigInteger.probablePrime(512, randomInteger);
    }

    private BigInteger gcd(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return a;
        } else {
            return gcd(b, a.mod(b));
        }
    }

    private BigInteger computeE() {
        Random rand = new Random();
        BigInteger e;
        do {
            e = new BigInteger(1024, rand);

            while (e.min(this.Fn).equals(this.Fn)) {
                e = new BigInteger(1024, rand);
            }
        } while (!Objects.equals(gcd(e, this.Fn), BigInteger.ONE));

        return e;
    }

    private BigInteger[] extendedEuclid(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO))
            return new BigInteger[]{a, BigInteger.ONE, BigInteger.ZERO};

        BigInteger[] values = extendedEuclid(b, a.mod(b));

        BigInteger d = values[0];
        BigInteger p = values[2];
        BigInteger q = values[1].subtract(a.divide(b).multiply(values[2]));

        return new BigInteger[]{d, p, q};
    }

    private BigInteger stringToAsciiBigInteger(String message) {
        String upperCaseMessage = message.toUpperCase();
        StringBuilder cipherString = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            cipherString.append((int) upperCaseMessage.charAt(i));
        }

        return new BigInteger(cipherString.toString());
    }

    private String bigIntegerAsciiToString(BigInteger message) {
        String cipherString = message.toString();
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < cipherString.length(); i += 2) {
            int temp = Integer.parseInt(cipherString.substring(i, i + 2));
            output.append((char) temp);
        }

        return output.toString();
    }

}
