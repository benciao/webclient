package com.ecg.webclient.feature.administration.persistence.modell;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entität einer Umgebung.
 * 
 * @author arndtmar
 */
@Entity
@Table(name = "SEC_ENVIRONMENT")
public class Environment
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int  passwordChangeInterval;

    public Environment()
    {}

    @Transient
    public Environment bind(Environment newSystem)
    {
        setPasswordChangeInterval(newSystem.getPasswordChangeInterval());
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
        if (!(obj instanceof Environment))
        {
            return false;
        }
        Environment other = (Environment) obj;
        if (id != other.id)
        {
            return false;
        }
        return true;
    }

    public long getId()
    {
        return id;
    }

    public int getPasswordChangeInterval()
    {
        return passwordChangeInterval;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setPasswordChangeInterval(int passwordChangeInterval)
    {
        this.passwordChangeInterval = passwordChangeInterval;
    }
}
