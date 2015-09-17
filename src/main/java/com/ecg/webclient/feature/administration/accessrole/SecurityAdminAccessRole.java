package com.ecg.webclient.feature.administration.accessrole;

import org.springframework.stereotype.Component;

import com.ecg.webclient.common.authentication.accessrole.WebClientAccessRole;

/**
 * Rolle, welche das Administrieren der Benutzer- und Berechtigungsverwaltung ermöglicht.
 * 
 * @author arndtmar
 */
@Component
public class SecurityAdminAccessRole extends WebClientAccessRole
{
    public SecurityAdminAccessRole()
    {
        super("SEC", "SEC_ADMIN");
    }
}
