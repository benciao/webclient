package com.ecg.webclient.feature.administration.persistence.odbmapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.modell.Property;
import com.ecg.webclient.feature.administration.viewmodell.PropertyDto;

/**
 * Mapped die Eigenschaften einer in OrientDb bekannten Entität auf eine detachted Eigenschaft oder umgekehrt.
 * 
 * @author arndtmar
 */
public class PropertyMapper
{
    /**
     * Wandelt eine persistente Eigenschaft in eine detachte um
     * 
     * @param property
     *            persistente Eigenschaft
     * @return Detachete Eigenschaft
     */
    public static PropertyDto mapToDto(Property property)
    {
        PropertyDto dto = new PropertyDto();
        dto.setKey(property.getKey());
        dto.setValue(property.getValue());
        dto.setId(property.getId());
        dto.setDelete(false);

        return dto;
    }

    /**
     * Wandelt eine Liste von persistenten Eigenschaften in eine Liste von detachten Eigenschaften um
     * 
     * @param properties
     *            Liste von persistenten Eigenschaften
     * @return Liste von detachten Eigenschaften
     */
    public static List<PropertyDto> mapToDtos(List<Property> properties)
    {
        List<PropertyDto> result = new AutoPopulatingList<PropertyDto>(PropertyDto.class);

        for (Property property : properties)
        {
            PropertyDto dto = new PropertyDto();
            dto.setKey(property.getKey());
            dto.setValue(property.getValue());
            dto.setId(property.getId());

            result.add(dto);
        }

        return result;
    }

    /**
     * Wandelt eine Liste von detachten Eigenschaften in eine Liste von persistenten Eigenschaften um
     * 
     * @param dtos
     *            Liste von detachten Eigenschaften
     * @return Liste von persistenten Eigenschaften
     */
    public static List<Property> mapToEntities(List<PropertyDto> dtos)
    {
        List<Property> result = new ArrayList<Property>();

        for (PropertyDto dto : dtos)
        {
            Property property = new Property();
            property.setKey(dto.getKey());
            property.setValue(dto.getValue());

            result.add(property);
        }

        return result;
    }

    /**
     * Wandelt eine detachte Eigenschaft in eine persistente um
     * 
     * @param dto
     *            Detachte Eigenschaft
     * @return Persistente Eigenschaft
     */
    public static Property mapToEntity(PropertyDto dto)
    {
        Property property = new Property();
        property.setKey(dto.getKey());
        property.setValue(dto.getValue());

        return property;
    }
}
