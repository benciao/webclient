package com.ecg.webclient.feature.administration.persistence.odbmodell;

import com.ecg.webclient.feature.administration.persistence.api.IProperty;

/**
 * Implementierung einer Eigenschaft. OrientDb spezifisch.
 * 
 * @author arndtmar
 */
public class OdbProperty extends OdbBaseObject implements IProperty
{
    private String key;
    private String value;

    public OdbProperty()
    {}

    @Override
    public void bind(IProperty newProperty)
    {
        setKey(newProperty.getKey());
        setValue(newProperty.getValue());
    }

    @Override
    public String getKey()
    {
        return key;
    }

    @Override
    public String getValue()
    {
        return value;
    }

    @Override
    public void setKey(String key)
    {
        this.key = key;
    }

    @Override
    public void setValue(String value)
    {
        this.value = value;
    }
}
