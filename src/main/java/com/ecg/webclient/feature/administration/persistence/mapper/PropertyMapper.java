package com.ecg.webclient.feature.administration.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.modell.Property;
import com.ecg.webclient.feature.administration.persistence.repo.PropertyRepository;
import com.ecg.webclient.feature.administration.viewmodell.PropertyDto;

/**
 * Mapped die Eigenschaften einer der Persistenz bekannten Entität auf eine detachted Eigenschaft oder
 * umgekehrt.
 * 
 * @author arndtmar
 */
@Component
public class PropertyMapper
{
    @Autowired
    PropertyRepository propertyRepo;

    /**
     * Wandelt eine attachte Eigenschaft in eine detachte um.
     * 
     * @param property
     *            attachte Eigenschaft
     * @return Detachete Eigenschaft
     */
    public PropertyDto mapToDto(Property property)
    {
        PropertyDto dto = new PropertyDto();
        dto.setKey(property.getKey());
        dto.setValue(property.getValue());
        dto.setId(property.getId());
        dto.setDelete(false);

        return dto;
    }

    /**
     * Wandelt eine Liste von attachten Eigenschaften in eine Liste von detachten Eigenschaften um.
     * 
     * @param properties
     *            Liste von attachten Eigenschaften
     * @return Liste von detachten Eigenschaften
     */
    public List<PropertyDto> mapToDtos(List<Property> properties)
    {
        List<PropertyDto> result = new AutoPopulatingList<PropertyDto>(PropertyDto.class);

        properties.forEach(e -> result.add(mapToDto(e)));

        return result;
    }

    /**
     * Wandelt eine Liste von detachten Eigenschaften in eine Liste von attachten Eigenschaften um.
     * 
     * @param dtos
     *            Liste von detachten Eigenschaften
     * @return Liste von attachten Eigenschaften
     */
    public List<Property> mapToEntities(List<PropertyDto> dtos)
    {
        List<Property> result = new ArrayList<Property>();

        dtos.forEach(e -> result.add(mapToEntity(e)));

        return result;
    }

    /**
     * Wandelt eine detachte Eigenschaft in eine attachte um.
     * 
     * @param dto
     *            Detachte Eigenschaft
     * @return attachte Eigenschaft
     */
    public Property mapToEntity(PropertyDto dto)
    {
        Property property = new Property();
        property.setId(dto.getId());
        property.setKey(dto.getKey());
        property.setValue(dto.getValue());

        Property persistentProperty = propertyRepo.findOne(property.getId());
        if (persistentProperty != null)
        {
            return persistentProperty.bind(property);
        }

        return property;
    }
}
