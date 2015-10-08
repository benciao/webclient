package com.ecg.webclient.feature.administration.viewmodell;

/**
 * Implementierung einer von der Persistenz detachten LDAP-Konfiguration.
 * 
 * @author arndtmar
 */
public class LdapConfigDto extends BaseObjectDto
{
	private boolean	enabled;
	private String	url;
	private String	username;
	private String	password;
	private String	base;
	private String	filter;
	
	public LdapConfigDto()
	{
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getBase()
	{
		return base;
	}

	public void setBase(String base)
	{
		this.base = base;
	}

	public String getFilter()
	{
		return filter;
	}

	public void setFilter(String filter)
	{
		this.filter = filter;
	}
}
