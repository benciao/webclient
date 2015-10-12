package com.ecg.webclient.feature.administration.viewmodell;

/**
 * Implementierung eines von der Persistenz detachten Fremdsystems.
 * 
 * @author arndtmar
 */
public class RemoteSystemDto extends BaseObjectDto
{
    private String  name;
    private String  description;
    private boolean enabled;
    private String  loginUrl;
    private String  loginParameter;
    private String  passwordParameter;
    private String  logoutUrl;

    public RemoteSystemDto()
    {}

    public String getDescription()
    {
        return description;
    }

    public String getLoginParameter()
    {
        return loginParameter;
    }

    public String getLoginUrl()
    {
        return loginUrl;
    }

    public String getLogoutUrl()
    {
        return logoutUrl;
    }

    public String getName()
    {
        return name;
    }

    public String getPasswordParameter()
    {
        return passwordParameter;
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

    public void setLoginParameter(String loginParameter)
    {
        this.loginParameter = loginParameter;
    }

    public void setLoginUrl(String loginUrl)
    {
        this.loginUrl = loginUrl;
    }

    public void setLogoutUrl(String logoutUrl)
    {
        this.logoutUrl = logoutUrl;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPasswordParameter(String passwordParameter)
    {
        this.passwordParameter = passwordParameter;
    }
}
