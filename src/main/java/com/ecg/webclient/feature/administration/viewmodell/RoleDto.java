package com.ecg.webclient.feature.administration.viewmodell;

import javax.validation.constraints.NotNull;

/**
 * Implementierung einer von der Persistenz detachten Benutzerrolle.
 * 
 * @author arndtmar
 */
public class RoleDto extends BaseObjectDto
{
    @NotNull
    private String  name;
    private boolean enabled;

    public RoleDto()
    {}

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

    public void setName(String name)
    {
        this.name = name;
    }
}
