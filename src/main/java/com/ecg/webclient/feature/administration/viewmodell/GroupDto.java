package com.ecg.webclient.feature.administration.viewmodell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Implementierung einer von der Persistenz detachten Benutzergruppe.
 * 
 * @author arndtmar
 */
public class GroupDto extends BaseObjectDto
{
	@NotNull
	private String		name;
	@NotNull
	private String		description;
	private boolean		enabled;
	private String		roleIds;
	private ClientDto	client;

	public ClientDto getClient()
	{
		return client;
	}

	public void setClient(ClientDto client)
	{
		this.client = client;
	}

	public GroupDto()
	{
	}

	public String getDescription()
	{
		return description;
	}

	public String getName()
	{
		return name;
	}

	public List<Long> getRoleIdObjects()
	{
		List<Long> result = new ArrayList<Long>();

		if (roleIds != null && !roleIds.isEmpty())
		{
			List<String> ids = Arrays.asList(roleIds.split(","));

			for (String id : ids)
			{
				result.add(Long.parseLong(id));
			}
		}

		return result.size() != 0 ? result : null;
	}

	public String getRoleIds()
	{
		return roleIds;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setRoleIds(String roleIds)
	{
		this.roleIds = roleIds;
	}
}
