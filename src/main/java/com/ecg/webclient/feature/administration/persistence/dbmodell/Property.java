package com.ecg.webclient.feature.administration.persistence.dbmodell;

public class Property extends BaseObject
{
    private String key;
    private String value;

    public Property()
    {}

    public String getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public void update(Property newProperty)
    {
        setKey(newProperty.getKey());
        setValue(newProperty.getValue());
    }
}
