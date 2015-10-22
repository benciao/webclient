package com.ecg.webclient.feature.administration.viewmodell;

/**
 * Implementierung eines von der Persistenz detachten RemoteLogins.
 * 
 * @author arndtmar
 */
public class RemoteLoginDto extends BaseObjectDto
{
	private String	userId;
	private String  remoteSystemId;
	private String	remoteSystemName;
	private String	remoteSystemDescription;
	private boolean	enabled;
	private String	remoteSystemLoginName;
	private String	remoteSystemPassword;

	public RemoteLoginDto()
	{
	}

	public String getRemoteSystemId()
	{
		return remoteSystemId;
	}

	public void setRemoteSystemId(String remoteSystemId)
	{
		this.remoteSystemId = remoteSystemId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getRemoteSystemName()
	{
		return remoteSystemName;
	}

	public void setRemoteSystemName(String remoteSystemName)
	{
		this.remoteSystemName = remoteSystemName;
	}

	public String getRemoteSystemDescription()
	{
		return remoteSystemDescription;
	}

	public void setRemoteSystemDescription(String remoteSystemDescription)
	{
		this.remoteSystemDescription = remoteSystemDescription;
	}

	public String getRemoteSystemLoginName()
	{
		return remoteSystemLoginName;
	}

	public String getRemoteSystemPassword()
	{
		return remoteSystemPassword;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public void setRemoteSystemLoginName(String remoteSystemLoginName)
	{
		this.remoteSystemLoginName = remoteSystemLoginName;
	}

	public void setRemoteSystemPassword(String remoteSystemPassword)
	{
		this.remoteSystemPassword = remoteSystemPassword;
	}
}
