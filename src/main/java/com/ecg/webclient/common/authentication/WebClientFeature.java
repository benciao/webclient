package com.ecg.webclient.common.authentication;

/**
 * Basisklasse für ein zu registrierendes Feature. Registriert wird ein Feature
 * ausschließlich bei Anwendungsstart über {@link FeatureService}
 * 
 * @author arndtmar
 */
public abstract class WebClientFeature
{
	String	featureId;
	
	protected WebClientFeature(String featureId)
	{
		this.featureId = featureId;
	}

	public String getFeatureId()
	{
		return featureId;
	}
}
