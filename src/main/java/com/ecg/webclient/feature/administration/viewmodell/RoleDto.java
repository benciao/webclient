package com.ecg.webclient.feature.administration.viewmodell;

import javax.validation.constraints.NotNull;

import com.ecg.webclient.feature.administration.persistence.api.IRoleDto;

/**
 * Implementierung einer von der Persistenz detachten Benutzerrolle.
 * 
 * @author arndtmar
 */
public class RoleDto extends BaseObjectDto implements IRoleDto
{
    @NotNull
    private String  name;
    @NotNull
    private String  description;
    private boolean enabled;

    public RoleDto()
    {}

    @Override
    public String getDescription()
    {
        return description;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    @Override
    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
    }
}
