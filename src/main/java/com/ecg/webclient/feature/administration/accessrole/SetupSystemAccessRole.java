package com.ecg.webclient.feature.administration.accessrole;

import org.springframework.stereotype.Component;

import com.ecg.webclient.common.authentication.accessrole.WebClientAccessRole;

/**
 * Rolle, welche Konfigurieren des Gesamtsystems ermöglicht.
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
