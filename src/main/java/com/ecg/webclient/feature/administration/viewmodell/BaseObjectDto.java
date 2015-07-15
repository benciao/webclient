package com.ecg.webclient.feature.administration.viewmodell;

public class BaseObjectDto
{
    private Object  rid;
    private Boolean delete = false;

    public Boolean getDelete()
    {
        if (delete == null)
        {
            delete = false;
        }
        return delete;
    }

    public Object getRid()
    {
        return rid;
    }

    public void setDelete(Boolean delete)
    {
        this.delete = delete;
    }

    public void setRid(Object rid)
    {
        this.rid = rid;
    }
}
