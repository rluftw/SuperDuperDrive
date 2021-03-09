package com.udacity.jwdnd.course1.cloudstorage.services.storage;

import com.udacity.jwdnd.course1.cloudstorage.Model.internal.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Model.POJO.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.authentication.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.authentication.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private UserService userService;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    public Credential[] allCredentials(Authentication authentication) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        Credential[] credentials = credentialMapper.getAllCredentials(userId);
        for(Credential credential: credentials) {
            String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            credential.setDecryptedPassword(decryptedPassword);
        }
        return credentials;
    }

    public Boolean addCredential(CredentialForm credentialForm, Authentication authentication) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        Credential credential = new Credential(
                credentialForm.getId(),
                credentialForm.getUrl(),
                credentialForm.getUsername(),
                null, null, null
        );
        Integer rowsAffected;
        String key = createKey();

        credential.setPassword(encryptionService.encryptValue(credentialForm.getPassword(), key));
        credential.setKey(key);
        credential.setUserId(userId);

        if (credential.getId() == null) {
            rowsAffected = credentialMapper.addCredential(credential);
        } else {
            rowsAffected = credentialMapper.updateCredential(credential);
        }
        return rowsAffected > 0;
    }

    public Boolean deleteCredential(Integer credentialId) {
        Integer rowsChanged = credentialMapper.deleteCredential(credentialId);
        return rowsChanged > 0;
    }

    private String createKey() {
        byte[] key = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
