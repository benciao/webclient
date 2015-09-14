package com.ecg.webclient.common.authentication.accessrole.api;

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
