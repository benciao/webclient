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
    public static final String KEY = "SETUP_SYSTEM";

	@Autowired
	public SetupSystemAccessRole(AdministrationFeature feature)
	{
        super(feature, KEY);
	}
}
