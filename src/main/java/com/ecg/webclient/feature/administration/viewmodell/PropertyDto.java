package com.ecg.webclient.feature.administration.viewmodell;

import javax.validation.constraints.NotNull;

import com.ecg.webclient.feature.administration.persistence.api.IPropertyDto;

/**
 * Implementierung einer von der Persistenz detachten Eigenschaft.
 * 
 * @author arndtmar
 */
public class PropertyDto extends BaseObjectDto implements IPropertyDto
{
    @NotNull
    private String key;
    @NotNull
    private String value;

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
