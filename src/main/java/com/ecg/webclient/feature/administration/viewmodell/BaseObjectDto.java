package com.ecg.webclient.feature.administration.viewmodell;

import com.ecg.webclient.feature.administration.persistence.api.IBaseObjectDto;

/**
 * Implementierung eines von der Persistenz detachten Basisobjektes.
 * 
 * @author arndtmar
 */
public class BaseObjectDto implements IBaseObjectDto
{
    private Object  rid;
    private boolean delete = false;

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BaseObjectDto other = (BaseObjectDto) obj;
        if (delete != other.delete) return false;
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
        result = prime * result + (delete ? 1231 : 1237);
        result = prime * result + ((rid == null) ? 0 : rid.hashCode());
        return result;
    }

    @Override
    public boolean isDelete()
    {
        return delete;
    }

    @Override
    public void setDelete(boolean delete)
    {
        this.delete = delete;
    }

    @Override
    public void setRid(Object rid)
    {
        this.rid = rid;
    }
}
