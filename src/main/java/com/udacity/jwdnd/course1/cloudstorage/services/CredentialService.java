package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.utils.KeyGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CredentialService {

    private final UserService userService;
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;
    private final KeyGenerator keyGenerator;

    public CredentialService(
            UserService userService,
            CredentialMapper credentialMapper,
            EncryptionService encryptionService,
            KeyGenerator keyGenerator
    ) {
        this.userService = userService;
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.keyGenerator = keyGenerator;
    }

    public int createCredential(CredentialForm form, String user) {
        final Integer userId = userService.getUserByName(user).getUserId();
        final Credential credential = formToCredential(form);
        credential.setUserid(userId);
        return credentialMapper.insert(credential);
    }

    public void updateCredential(CredentialForm form) {
        final Credential credential = formToCredential(form);
        credentialMapper.update(credential);
    }

    public void deleteCredential(Integer id) {
        credentialMapper.delete(id);
    }

    public List<Credential> getAllCredentials(String userName) {
        final User user = userService.getUserByName(userName);
        if (user != null) {
            final Integer userId = user.getUserId();
            List<Credential> credentials = credentialMapper.getUserCredentials(userId);
            for (Credential credential : credentials) {
                final String key = credential.getKey();
                final String password = credential.getPassword();
                final String decodedPassword = encryptionService.decryptValue(password, key);
                credential.setDecodedPassword(decodedPassword);
            }
            return credentials;
        }
        return new ArrayList<>();
    }

    public boolean userNameExist(String userName) {
        return credentialMapper.getCredentialByUsername(userName) != null;
    }

    private Credential formToCredential(CredentialForm form) {
        final String key = keyGenerator.generate();
        final String encodedPassword = encryptionService.encryptValue(
                form.getPassword(),
                key
        );
        return new Credential(
                form.getId(),
                form.getUrl(),
                form.getUsername(),
                key,
                encodedPassword,
                null
        );
    }
}
