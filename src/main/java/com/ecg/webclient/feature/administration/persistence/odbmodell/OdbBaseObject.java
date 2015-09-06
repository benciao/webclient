package com.ecg.webclient.feature.administration.persistence.odbmodell;

import javax.persistence.Id;

import com.ecg.webclient.feature.administration.persistence.api.IBaseObject;

/**
 * Implementierung eines Basisobjektes. OrientDb spezifisch.
 * 
 * @author arndtmar
 */
public class OdbBaseObject implements IBaseObject
{
    @Id
    private Object rid;

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        OdbBaseObject other = (OdbBaseObject) obj;
        if (rid == null)
        {
            if (other.rid != null) return false;
        }
        else if (!rid.equals(other.rid)) return false;
        return true;
    }

    @Override
    public Object getRid()
    {
        return rid;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rid == null) ? 0 : rid.hashCode());
        return result;
    }

    @Override
    public void setRid(Object rid)
    {
        this.rid = rid;
    }
}
