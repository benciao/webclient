package com.ecg.webclient.feature.administration.persistence.odbmodell;

import com.ecg.webclient.feature.administration.persistence.api.IRole;

/**
 * Implementierung einer Benutzerrolle. OrientDb spezifisch.
 * 
 * @author arndtmar
 */
public class OdbRole extends OdbBaseObject implements IRole
{
    private String  name;
    private String  description;
    private boolean enabled;

    public OdbRole()
    {}

    @Override
    public void bind(IRole newRole)
    {
        setName(newRole.getName());
        setDescription(newRole.getDescription());
        setEnabled(newRole.isEnabled());
    }

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
