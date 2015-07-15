package com.ecg.webclient.feature.administration.persistence.dbmodell;

public class Role extends BaseObject
{
    private String  name;
    private String  description;
    private boolean enabled;

    public Role()
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

    public void update(Role newRole)
    {
        setName(newRole.getName());
        setDescription(newRole.getDescription());
        setEnabled(newRole.isEnabled());
    }
}
