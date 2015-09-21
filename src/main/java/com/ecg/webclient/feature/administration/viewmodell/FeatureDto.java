package com.ecg.webclient.feature.administration.viewmodell;

/**
 * Implementierung eines von der Persistenz detachten Features.
 * 
 * @author arndtmar
 */
public class FeatureDto extends BaseObjectDto
{
	private String	name;
	private boolean	enabled;

	public FeatureDto()
	{
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
}
