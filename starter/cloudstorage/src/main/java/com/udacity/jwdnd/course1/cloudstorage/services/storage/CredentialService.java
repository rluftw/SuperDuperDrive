package com.udacity.jwdnd.course1.cloudstorage.services.storage;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.authentication.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private UserService userService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
    }

    public Credential[] allCredentials(Authentication authentication) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        return credentialMapper.getAllCredentials(userId);
    }

    public Boolean storeCredential(Credential credential, Authentication authentication) {
        credential.setUserId(userService.getUser(authentication.getName()).getId());
        Integer rowsAffected;
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
}
