package com.ecg.webclient.feature.administration.accessrole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecg.webclient.common.authentication.WebClientAccessRole;

/**
 * Rolle, welche Konfigurieren des Gesamtsystems ermöglicht.
 *
 * @author arndtmar
 */
@Component
public class SetupSystemAccessRole extends WebClientAccessRole
{
	@Autowired
	public SetupSystemAccessRole(AdministrationFeature feature)
	{
		super(feature, "SETUP_SYSTEM");
	}
}
