package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.utils.MessageUrlComposer;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {

    private final CredentialService credentialService;
    private final MessageUrlComposer messageUrlComposer;

    public CredentialController(CredentialService credentialService, MessageUrlComposer messageUrlComposer) {
        this.credentialService = credentialService;
        this.messageUrlComposer = messageUrlComposer;
    }

    @PostMapping("/credential")
    public String addOrUpdate(Authentication authentication, CredentialForm form) {
        final Integer credentialId = form.getId();

        boolean userAlreadyExist = credentialService.userNameExist(form.getUsername());
        if (userAlreadyExist) {
            return messageUrlComposer.error("User name should be unique.");
        }

        if (credentialId == null) {
            String userName = authentication.getName();
            credentialService.createCredential(form, userName);
            return messageUrlComposer.success("Credentials have been successfully saved.");
        } else {
            credentialService.updateCredential(form);
            return messageUrlComposer.success("Credentials have been successfully updated.");
        }
    }

    @PostMapping("/deleteCredential/{id}")
    public String delete(@PathVariable Integer id) {
        credentialService.deleteCredential(id);
        return messageUrlComposer.success("Credentials have been successfully deleted.");
    }
}
