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
    @NotNull
    private String  description;
    private boolean enabled;

    public RoleDto()
    {}

    public String getDescription()
    {
        return description;
    }

    public String getName()
    {
        return name;
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
}
