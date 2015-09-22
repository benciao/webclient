package com.ecg.webclient.feature.administration.accessrole;

import org.springframework.stereotype.Component;

import com.ecg.webclient.common.authentication.WebClientFeature;

@Component
public class TestFeature extends WebClientFeature
{
	public TestFeature()
	{
		super("TEST", true);
	}
}
