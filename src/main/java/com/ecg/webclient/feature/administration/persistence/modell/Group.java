package com.ecg.webclient.feature.administration.persistence.modell;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

/**
 * Entität einer BenutzerGruppe.
 * 
 * @author arndtmar
 */
@Entity
public class Group
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long       id;
    private String     name;
    private String     description;
    private boolean    enabled;
    @OneToMany(targetEntity = Role.class)
    private List<Role> roles;
    @OneToOne
    private Client     client;
    private List<Long> roleIds;

    public Group()
    {}

    @Transient
    public void bind(Group newGroup)
    {
        setName(newGroup.getName());
        setDescription(newGroup.getDescription());
        setEnabled(newGroup.isEnabled());
        setClient(newGroup.getClient());
        setRoles(newGroup.getRoles());
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Group))
        {
            return false;
        }
        Group other = (Group) obj;
        if (client == null)
        {
            if (other.client != null)
            {
                return false;
            }
        }
        else if (!client.equals(other.client))
        {
            return false;
        }
        if (description == null)
        {
            if (other.description != null)
            {
                return false;
            }
        }
        else if (!description.equals(other.description))
        {
            return false;
        }
        if (name == null)
        {
            if (other.name != null)
            {
                return false;
            }
        }
        else if (!name.equals(other.name))
        {
            return false;
        }
        return true;
    }

    public Client getClient()
    {
        return client;
    }

    public String getDescription()
    {
        return description;
    }

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public List<Role> getRoles()
    {
        return roles;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((client == null) ? 0 : client.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
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

    @Transient
    public void setRoleIds(List<Long> roleIdObjects)
    {
        this.roleIds = roleIdObjects;

    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }
}
