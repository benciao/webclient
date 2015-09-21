package com.ecg.webclient.feature.administration.accessrole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecg.webclient.common.authentication.WebClientAccessRole;

/**
 * Rolle, welche das Administrieren der Benutzer- und Berechtigungsverwaltung
 * ermöglicht.
 * 
 * @author arndtmar
 */
@Component
public class SecurityAdminAccessRole extends WebClientAccessRole
{
	@Autowired
	public SecurityAdminAccessRole(AdministrationFeature feature)
	{
		super(feature, "ADMIN");
	}
}
