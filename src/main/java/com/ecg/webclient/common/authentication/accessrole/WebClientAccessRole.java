package com.ecg.webclient.common.authentication.accessrole;

import com.ecg.webclient.feature.administration.service.RoleService;

/**
 * Basisklasse für eine zu registrierende Rolle. Registriert wird eine Rolle ausschließlich bei
 * Anwendungsstart über {@link RoleService}
 * 
 * @author arndtmar
 */
public abstract class WebClientAccessRole
{
	String	featureId;
	String	roleKey;

	protected WebClientAccessRole(String featureId, String roleKey)
	{
		this.featureId = featureId;
		this.roleKey = roleKey;
	}

	public String getName()
	{
		return featureId.toUpperCase() + "_" + roleKey.toUpperCase();
	}
}
