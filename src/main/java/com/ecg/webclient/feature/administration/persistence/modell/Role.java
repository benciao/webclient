package com.ecg.webclient.feature.administration.persistence.modell;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * Entität einer Benutzerrolle.
 * 
 * @author arndtmar
 */
@Entity
@Table(name = "SEC_ROLE")
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long    id;
    private String  name;
    private String  description;
    private boolean enabled;

    public Role()
    {}

    @Transient
    public Role bind(Role newRole)
    {
        setName(newRole.getName());
        setDescription(newRole.getDescription());
        setEnabled(newRole.isEnabled());

        return this;
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
        if (!(obj instanceof Role))
        {
            return false;
        }
        Role other = (Role) obj;
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
        if (id != other.id)
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

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
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

    public void setId(long id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
