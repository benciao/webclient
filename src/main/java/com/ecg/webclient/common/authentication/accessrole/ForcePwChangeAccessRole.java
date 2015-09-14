package com.ecg.webclient.common.authentication.accessrole;

import org.springframework.stereotype.Component;

import com.ecg.webclient.common.authentication.accessrole.api.WebClientAccessRole;

/**
 * Eine Rolle, welche das Ändern des Passwortes nach Anmeldung erzwingt.
 *
 * @author arndtmar
 */
@Component
public class ForcePwChangeAccessRole extends WebClientAccessRole
{
	public ForcePwChangeAccessRole()
	{
		super("SEC", "FORCE_PASSWORD_CHANGE");
	}
}
