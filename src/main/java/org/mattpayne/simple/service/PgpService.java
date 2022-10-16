package org.mattpayne.simple.service;

import org.pgpainless.sop.SOPImpl;
import org.bouncycastle.util.io.Streams;
import org.springframework.stereotype.Service;
import sop.ByteArrayAndResult;
import sop.DecryptionResult;

import static org.mattpayne.simple.service.UtilsFromStackOverflow.getResourceFileAsString;

import java.io.*;

@Service
public class PgpService {
    SOPImpl sop;

    public PgpService() {
        sop = new SOPImpl();
    }

    public String decrypt(String encryptedString) {
        String privateKey = getResourceFileAsString("bob.key");
        try {
            return decrypt(encryptedString, privateKey);
        } catch (IOException e) {
            e.printStackTrace(); // TODO(MGP): Use lombok to add log42j
            return "Could not decrypt message.";
        }
    }

    String decrypt(String encryptedString, String privateKey) throws IOException {
        String clearText=null;
        byte[] encrypted = encryptedString.getBytes();

        ByteArrayAndResult<DecryptionResult> bytesAndResult = sop.decrypt()
                .withKey(privateKey.getBytes())
                // .verifyWithCert(aliceCert)
                .ciphertext(encrypted)
                .toByteArrayAndResult();
        ByteArrayOutputStream decrypted = new ByteArrayOutputStream();
        Streams.pipeAll(bytesAndResult.getInputStream(), decrypted);
        clearText = decrypted.toString();
        return clearText;
    }

}
