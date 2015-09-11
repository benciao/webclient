package com.ecg.webclient.feature.administration.persistence.modell;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entität einer Eigenschaft.
 * 
 * @author arndtmar
 */
@Entity
@Table(name = "SEC_PROPERTY")
public class Property
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long   id;
    private String prop_key;
    private String prop_value;

    public Property()
    {}

    @Transient
    public Property bind(Property newProperty)
    {
        setKey(newProperty.getKey());
        setValue(newProperty.getValue());

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
        if (!(obj instanceof Property))
        {
            return false;
        }
        Property other = (Property) obj;
        if (id != other.id)
        {
            return false;
        }
        if (prop_key == null)
        {
            if (other.prop_key != null)
            {
                return false;
            }
        }
        else if (!prop_key.equals(other.prop_key))
        {
            return false;
        }
        if (prop_value == null)
        {
            if (other.prop_value != null)
            {
                return false;
            }
        }
        else if (!prop_value.equals(other.prop_value))
        {
            return false;
        }
        return true;
    }

    public long getId()
    {
        return id;
    }

    public String getKey()
    {
        return prop_key;
    }

    public String getValue()
    {
        return prop_value;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((prop_key == null) ? 0 : prop_key.hashCode());
        result = prime * result + ((prop_value == null) ? 0 : prop_value.hashCode());
        return result;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setKey(String key)
    {
        this.prop_key = key;
    }

    public void setValue(String value)
    {
        this.prop_value = value;
    }
}
