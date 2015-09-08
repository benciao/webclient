package com.ecg.webclient.common;

import org.springframework.stereotype.Component;

@Component
public class ApplicationUtil
{
	boolean isMenuMinimized;

	public ApplicationUtil()
	{
	}

	public boolean isMenuMinimized()
	{
		return isMenuMinimized;
	}

	public void setMenuMinimized(boolean isMenuMinimized)
	{
		this.isMenuMinimized = isMenuMinimized;
	}
}
