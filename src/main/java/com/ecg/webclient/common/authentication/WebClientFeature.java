package com.ecg.webclient.common.authentication;

import com.ecg.webclient.feature.administration.service.FeatureService;

/**
 * Basisklasse für ein zu registrierendes Feature. Registriert wird ein Feature
 * ausschließlich bei Anwendungsstart über {@link FeatureService}
 * 
 * @author arndtmar
 */
public abstract class WebClientFeature
{
	String	featureId;
	boolean deactivatable;
	
	protected WebClientFeature(String featureId, boolean deactivatable)
	{
		this.featureId = featureId;
		this.deactivatable = deactivatable;
	}

    public String getFeatureKey()
	{
		return featureId;
	}

	public boolean isDeactivatable()
	{
		return deactivatable;
	}
}
