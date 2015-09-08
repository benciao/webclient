package com.ecg.webclient.feature.administration.viewmodell;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.AutoPopulatingList;

/**
 * Implementierung eines von der Persistenz detachten Mandanten.
 * 
 * @author arndtmar
 */
public class ClientDto extends BaseObjectDto
{
    private String            color;

    @Size(min = 10, max = 50)
    @NotNull
    private String            description;
    @Size(min = 2, max = 30)
    @NotNull
    private String            name;
    private boolean           enabled;
    private List<PropertyDto> properties;
    private boolean           selected;

    public ClientDto()
    {}

    public String getColor()
    {
        return color;
    }

    public String getDescription()
    {
        return description;
    }

    public String getName()
    {
        return name;
    }

    public List<PropertyDto> getProperties()
    {
        if (properties == null)
        {
            properties = new AutoPopulatingList<PropertyDto>(PropertyDto.class);
        }
        return properties;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setProperties(List<PropertyDto> properties)
    {
        this.properties = properties;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

}
