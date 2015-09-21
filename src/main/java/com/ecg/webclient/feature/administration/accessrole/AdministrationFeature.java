package com.ecg.webclient.feature.administration.accessrole;

import org.springframework.stereotype.Component;

import com.ecg.webclient.common.authentication.WebClientFeature;

/**
 * Feature, welches das Administrieren des Gesamtsystems ermöglicht.
 *
 * @author arndtmar
 */
@Component
public class AdministrationFeature extends WebClientFeature
{
	public AdministrationFeature()
	{
		super("SEC");
	}
}
