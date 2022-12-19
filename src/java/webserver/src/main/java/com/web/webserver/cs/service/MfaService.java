package com.web.webserver.cs.service;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;

@Service
public class MfaService {
    public String generateSecret(){
        return new DefaultSecretGenerator(64).generate();
    }

    private QrData generateQrData(String secret, String email){
        return new QrData.Builder()
                .label(email)
                .secret(secret)
                .issuer("CsLabsMFA")
                .algorithm(HashingAlgorithm.SHA1)
                .build();
    }

    public byte[] generateQrPng(String secret, String email) throws AuthException {
        QrData data = generateQrData(secret, email);
        try {
            return new ZxingPngQrGenerator().generate(data);
        } catch (QrGenerationException e) {
            throw new AuthException();
        }
    }

    public boolean verify(String secret, String code){
        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);

        return verifier.isValidCode(secret, code);
    }
}
