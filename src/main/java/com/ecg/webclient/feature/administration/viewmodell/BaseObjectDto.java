package com.ecg.webclient.feature.administration.viewmodell;

public class BaseObjectDto
{
    private Object  rid;
    private boolean delete = false;

    public boolean getDelete()
    {
        return delete;
    }

    public Object getRid()
    {
        return rid;
    }

    public void setDelete(boolean delete)
    {
        this.delete = delete;
    }

    public void setRid(Object rid)
    {
        this.rid = rid;
    }
}
