package com.web.webserver.cs.controller;

import com.web.webserver.cs.ciphers.CaesarCipher;
import com.web.webserver.cs.ciphers.RSA;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/cipher")
@RequiredArgsConstructor
public class CipherController {

    @GetMapping("/caesar")
    @PreAuthorize("hasAuthority('CLASSIC')")
    @ResponseStatus(HttpStatus.OK)
    public String caesarCipher(@RequestParam String message, @RequestParam int shift) {
        CaesarCipher caesar = new CaesarCipher();
        String encryptedCaesar = caesar.encrypt(message, shift);

        return "Encrypted: " + encryptedCaesar + "  .  " +  "Decrypted: " + caesar.decrypt(encryptedCaesar, shift);
    }

    @GetMapping("/rsa")
    @PreAuthorize("hasAuthority('ASYMMETRIC')")
    @ResponseStatus(HttpStatus.OK)
    public String asymmetricCipher(@RequestParam String message) {
        RSA rsa = new RSA();

        BigInteger encrypted = rsa.encrypt(message);
        String decrypted = rsa.decrypt(encrypted);

        return "Encrypted: " + encrypted + "  .  " +  "Decrypted: " + decrypted;
    }
}
