package com.ecg.webclient.feature.administration.viewmodell;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.api.IClientDto;
import com.ecg.webclient.feature.administration.persistence.api.IPropertyDto;

/**
 * Implementierung eines von der Persistenz detachten Mandanten.
 * 
 * @author arndtmar
 */
public class ClientDto extends BaseObjectDto implements IClientDto
{
    private String            color;

    @Size(min = 10, max = 50)
    @NotNull
    private String            description;

    @Size(min = 2, max = 30)
    @NotNull
    private String            name;
    private boolean           enabled;
    private List<IPropertyDto> properties;
    private boolean           selected;

    public ClientDto()
    {}

    @Override
    public String getColor()
    {
        return color;
    }

    @Override
    public String getDescription()
    {
        return description;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public List<IPropertyDto> getProperties()
    {
        if (properties == null)
        {
            properties = new AutoPopulatingList<IPropertyDto>(IPropertyDto.class);
        }
        return properties;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    @Override
    public boolean isSelected()
    {
        return selected;
    }

    @Override
    public void setColor(String color)
    {
        this.color = color;
    }

    @Override
    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public void setProperties(List<IPropertyDto> properties)
    {
        this.properties = properties;
    }

    @Override
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

}
