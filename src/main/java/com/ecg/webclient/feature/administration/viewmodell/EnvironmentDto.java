package com.ecg.webclient.feature.administration.viewmodell;

/**
 * Implementierung einer von der Persistenz detachten Umgebung.
 * 
 * @author arndtmar
 */
public class EnvironmentDto extends BaseObjectDto
{
    private int passwordChangeInterval;
    private int allowedLoginAttempts;
    private String systemIdentifier;

    public EnvironmentDto()
    {}

    public int getAllowedLoginAttempts()
    {
        return allowedLoginAttempts;
    }

    public int getPasswordChangeInterval()
    {
        return passwordChangeInterval;
    }

    public String getSystemIdentifier()
    {
        return systemIdentifier;
    }

    public void setAllowedLoginAttempts(int allowedLoginAttempts)
    {
        this.allowedLoginAttempts = allowedLoginAttempts;
    }

    public void setPasswordChangeInterval(int passwordChangeInterval)
    {
        this.passwordChangeInterval = passwordChangeInterval;
    }

    public void setSystemIdentifier(String systemIdentifier)
    {
        this.systemIdentifier = systemIdentifier;
    }
}
