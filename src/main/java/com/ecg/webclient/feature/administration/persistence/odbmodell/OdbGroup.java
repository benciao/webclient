package com.ecg.webclient.feature.administration.persistence.odbmodell;

import java.util.List;

import javax.persistence.Transient;

import com.ecg.webclient.feature.administration.persistence.api.IClient;
import com.ecg.webclient.feature.administration.persistence.api.IGroup;
import com.ecg.webclient.feature.administration.persistence.api.IRole;

/**
 * Implementierung einer BenutzerGruppe. OrientDb spezifisch.
 * 
 * @author arndtmar
 */
public class OdbGroup extends OdbBaseObject implements IGroup
{
    private String       name;
    private String       description;
    private boolean      enabled;
    private List<IRole>  roles;
    private IClient      client;
    @Transient
    private List<Object> roleRids;

    public OdbGroup()
    {}

    @Override
    public void bind(IGroup newGroup)
    {
        setName(newGroup.getName());
        setDescription(newGroup.getDescription());
        setEnabled(newGroup.isEnabled());
        setClient(newGroup.getClient());
    }

    @Override
    public IClient getClient()
    {
        return client;
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
    public List<Object> getRoleRids()
    {
        return roleRids;
    }

    @Override
    public List<IRole> getRoles()
    {
        return roles;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    @Override
    public void setClient(IClient client)
    {
        this.client = client;
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

    @Override
    public void setRoleRids(List<Object> roleRids)
    {
        this.roleRids = roleRids;
    }

    @Override
    public void setRoles(List<IRole> roles)
    {
        this.roles = roles;
    }
}
