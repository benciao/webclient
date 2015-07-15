package com.ecg.webclient.feature.administration.viewmodell.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.dbmodell.Property;
import com.ecg.webclient.feature.administration.viewmodell.PropertyDto;

public class PropertyMapper
{
    public static PropertyDto mapToDto(Property property)
    {
        PropertyDto dto = new PropertyDto();
        dto.setKey(property.getKey());
        dto.setValue(property.getValue());
        dto.setRid(property.getRid());
        dto.setDelete(false);

        return dto;
    }

    public static List<PropertyDto> mapToDtos(List<Property> properties)
    {
        List<PropertyDto> result = new AutoPopulatingList<PropertyDto>(PropertyDto.class);

        for (Property property : properties)
        {
            PropertyDto dto = new PropertyDto();
            dto.setKey(property.getKey());
            dto.setValue(property.getValue());
            dto.setRid(property.getRid());

            result.add(dto);
        }

        return result;
    }

    public static List<Property> mapToEntities(List<PropertyDto> dtos)
    {
        List<Property> result = new ArrayList<Property>();

        for (PropertyDto dto : dtos)
        {
            Property property = new Property();
            property.setKey(dto.getKey());
            property.setValue(dto.getValue());
            property.setRid(dto.getRid());

            result.add(property);
        }

        return result;
    }

    public static Property mapToEntity(PropertyDto dto)
    {
        Property property = new Property();
        property.setKey(dto.getKey());
        property.setValue(dto.getValue());
        property.setRid(dto.getRid());

        return property;
    }
}
