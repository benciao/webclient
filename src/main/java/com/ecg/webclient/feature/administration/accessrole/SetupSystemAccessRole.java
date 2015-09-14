package com.ecg.webclient.feature.administration.accessrole;

import org.springframework.stereotype.Component;

import com.ecg.webclient.common.authentication.accessrole.api.WebClientAccessRole;

/**
 * Rolle, welche das Administrieren der Benutzer- und Berechtigungsverwaltung
 * ermöglicht.
 *
 * @author arndtmar
 */
@Component
public class SetupSystemAccessRole extends WebClientAccessRole
{
	public SetupSystemAccessRole()
	{
		super("SEC", "SETUP_SYSTEM");
	}
}
