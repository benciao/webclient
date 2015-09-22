package com.ecg.webclient.feature.administration.viewmodell;

import java.io.Serializable;

/**
 * Implementierung eines von der Persistenz detachten Features.
 * 
 * @author arndtmar
 */
public class FeatureDto extends BaseObjectDto
{
	private String	name;
	private boolean	enabled;
	private boolean	deactivatable;

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

	public boolean isDeactivatable()
	{
		return deactivatable;
	}

	public void setDeactivatable(boolean deactivatable)
	{
		this.deactivatable = deactivatable;
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
