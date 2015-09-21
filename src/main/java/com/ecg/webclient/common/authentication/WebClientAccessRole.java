package com.ecg.webclient.common.authentication;

import com.ecg.webclient.feature.administration.service.RoleService;

/**
 * Basisklasse für eine zu registrierende Rolle. Registriert wird eine Rolle
 * ausschließlich bei Anwendungsstart über {@link RoleService}
 * 
 * @author arndtmar
 */
public abstract class WebClientAccessRole
{
	WebClientFeature	featureId;
	String				roleKey;

	protected WebClientAccessRole(WebClientFeature featureId, String roleKey)
	{
		this.featureId = featureId;
		this.roleKey = roleKey;
	}

	public String getName()
	{
		return featureId.getFeatureId().toUpperCase() + "_" + roleKey.toUpperCase();
	}

	public WebClientFeature getFeatureId()
	{
		return featureId;
	}

	public String getRoleKey()
	{
		return roleKey;
	}
}
