package com.ecg.webclient.feature.administration.viewmodell;

import javax.validation.constraints.NotNull;

public class PropertyDto extends BaseObjectDto
{
    @NotNull
    private String key;
    @NotNull
    private String value;

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
}
