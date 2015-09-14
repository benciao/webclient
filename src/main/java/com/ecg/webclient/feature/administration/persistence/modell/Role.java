package com.ecg.webclient.feature.administration.persistence.modell;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entität einer Benutzerrolle.
 * 
 * @author arndtmar
 */
@Entity
@Table(name = "SEC_ROLE")
public class Role
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long	id;
	private String	name;
	private boolean	enabled;

	public Role()
	{
	}

	@Transient
	public Role bind(Role newRole)
	{
		setName(newRole.getName());
		setEnabled(newRole.isEnabled());

		return this;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id != other.id)
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}

	public long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public void setId(long id)
	{
		if (id != -1)
		{
			this.id = id;
		}
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
