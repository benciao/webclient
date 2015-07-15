package com.ecg.webclient.feature.administration.persistence.dbmodell;

import javax.persistence.Id;

public class BaseObject
{
    @Id
    private Object rid;

    public Object getRid()
    {
        return rid;
    }

    public void setRid(Object rid)
    {
        this.rid = rid;
    }
}
