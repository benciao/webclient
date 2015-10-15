package com.ecg.webclient.feature.administration.viewmodell;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.util.AutoPopulatingList;

public class ClientProperties
{
	@Valid
	private List<PropertyDto>	properties;
	private long				clientId;

	public List<PropertyDto> getProperties()
	{
		if (properties == null)
		{
			properties = new AutoPopulatingList<PropertyDto>(PropertyDto.class);
		}
		return properties;
	}

	public void removeDeleted()
	{
		List<PropertyDto> propertiesToRemove = new ArrayList<PropertyDto>();
		for (PropertyDto property : properties)
		{
			if (property.isDelete())
			{
				propertiesToRemove.add(property);
			}
		}
		properties.removeAll(propertiesToRemove);
	}

	public void setProperties(List<PropertyDto> properties)
	{
		this.properties = properties;
	}

	public long getClientId()
	{
		return clientId;
	}

	public void setClientId(long clientId)
	{
		this.clientId = clientId;
	}
}
