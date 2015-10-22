package com.ecg.webclient.feature.administration.viewmodell;

/**
 * Implementierung eines von der Persistenz detachten RemoteLogins.
 * 
 * @author arndtmar
 */
public class RemoteLoginDto extends BaseObjectDto
{
    private String  userId;
    private String  remoteSystemId;
    private boolean enabled;
    private String  remoteSystemLoginName;
    private String  remoteSystemPassword;

    public RemoteLoginDto()
    {

    }

    public String getRemoteSystemId()
    {
        return remoteSystemId;
    }

    public String getRemoteSystemLoginName()
    {
        return remoteSystemLoginName;
    }

    public String getRemoteSystemPassword()
    {
        return remoteSystemPassword;
    }

    public String getUserId()
    {
        return userId;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public void setRemoteSystemId(String remoteSystemId)
    {
        this.remoteSystemId = remoteSystemId;
    }

    public void setRemoteSystemLoginName(String remoteSystemLoginName)
    {
        this.remoteSystemLoginName = remoteSystemLoginName;
    }

    public void setRemoteSystemPassword(String remoteSystemPassword)
    {
        this.remoteSystemPassword = remoteSystemPassword;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }
}
