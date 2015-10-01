package com.ecg.webclient.feature.administration.viewmodell;

/**
 * Implementierung einer von der Persistenz detachten Umgebung.
 * 
 * @author arndtmar
 */
public class EnvironmentDto extends BaseObjectDto
{
    private int passwordChangeInterval;

    public EnvironmentDto()
    {}

    public int getPasswordChangeInterval()
    {
        return passwordChangeInterval;
    }

    public void setPasswordChangeInterval(int passwordChangeInterval)
    {
        this.passwordChangeInterval = passwordChangeInterval;
    }
}
