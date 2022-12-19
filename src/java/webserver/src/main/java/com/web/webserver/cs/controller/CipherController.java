package com.web.webserver.cs.controller;

import com.web.webserver.cs.ciphers.CaesarCipher;
import com.web.webserver.cs.ciphers.RSA;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequiredArgsConstructor
public class CipherController {

    @GetMapping("/caesar")
    @PreAuthorize("hasRole('CLASSIC')")
    @ResponseStatus(HttpStatus.OK)
    public String caesarCipher(@RequestParam String message, @RequestParam int shift) {
        CaesarCipher caesar = new CaesarCipher();

        String encrypted = caesar.encrypt(message, shift);
        String decrypted = caesar.decrypt(encrypted, shift);

        return "Caesar: Encrypted: " + encrypted + "  .  " +  "Decrypted: " + decrypted;
    }

    @GetMapping("/rsa")
    @PreAuthorize("hasRole('ASYMMETRIC')")
    @ResponseStatus(HttpStatus.OK)
    public String asymmetricCipher(@RequestParam String message) {
        RSA rsa = new RSA();

        BigInteger encrypted = rsa.encrypt(message);
        String decrypted = rsa.decrypt(encrypted);

        return "RSA: Encrypted: " + encrypted + "  .  " +  "Decrypted: " + decrypted;
    }
}
