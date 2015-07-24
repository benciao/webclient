package com.ecg.webclient.feature.administration.persistence.dbmodell;

import java.util.List;

import javax.persistence.Transient;

public class Group extends BaseObject
{
    private String       name;
    private String       description;
    private boolean      enabled;
    private List<Role>   roles;
    private Client       client;
    @Transient
    private List<Object> roleRids;

    public Group()
    {}

    public void bind(Group newGroup)
    {
        setName(newGroup.getName());
        setDescription(newGroup.getDescription());
        setEnabled(newGroup.isEnabled());
        setClient(newGroup.getClient());
    }

    @Override
    public boolean equals(Object otherGroup)
    {
        return this.getRid().toString().equalsIgnoreCase(((Group) otherGroup).getRid().toString());
    }

    public Client getClient()
    {
        return client;
    }

    public String getDescription()
    {
        return description;
    }

    public String getName()
    {
        return name;
    }

    public List<Object> getRoleRids()
    {
        return roleRids;
    }

    public List<Role> getRoles()
    {
        return roles;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setClient(Client client)
    {
        this.client = client;
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

    public void setRoleRids(List<Object> roleRids)
    {
        this.roleRids = roleRids;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }
}
