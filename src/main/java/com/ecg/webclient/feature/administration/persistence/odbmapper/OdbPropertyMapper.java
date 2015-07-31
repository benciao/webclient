package com.ecg.webclient.feature.administration.persistence.odbmapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.api.IProperty;
import com.ecg.webclient.feature.administration.persistence.api.IPropertyDto;
import com.ecg.webclient.feature.administration.persistence.odbmodell.OdbProperty;
import com.ecg.webclient.feature.administration.viewmodell.PropertyDto;

/**
 * Mapped die Eigenschaften einer in OrientDb bekannten Entität auf eine detachted Eigenschaft oder umgekehrt.
 * 
 * @author arndtmar
 */
public class OdbPropertyMapper
{
    /**
     * Wandelt eine persistente Eigenschaft in eine detachte um
     * 
     * @param property
     *            persistente Eigenschaft
     * @return Detachete Eigenschaft
     */
    public static IPropertyDto mapToDto(IProperty property)
    {
        IPropertyDto dto = new PropertyDto();
        dto.setKey(property.getKey());
        dto.setValue(property.getValue());
        dto.setRid(property.getRid());
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
    public static List<IPropertyDto> mapToDtos(List<IProperty> properties)
    {
        List<IPropertyDto> result = new AutoPopulatingList<IPropertyDto>(IPropertyDto.class);

        for (IProperty property : properties)
        {
            IPropertyDto dto = new PropertyDto();
            dto.setKey(property.getKey());
            dto.setValue(property.getValue());
            dto.setRid(property.getRid());

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
    public static List<IProperty> mapToEntities(List<IPropertyDto> dtos)
    {
        List<IProperty> result = new ArrayList<IProperty>();

        for (IPropertyDto dto : dtos)
        {
            IProperty property = new OdbProperty();
            property.setKey(dto.getKey());
            property.setValue(dto.getValue());
            property.setRid(dto.getRid());

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
    public static IProperty mapToEntity(IPropertyDto dto)
    {
        IProperty property = new OdbProperty();
        property.setKey(dto.getKey());
        property.setValue(dto.getValue());
        property.setRid(dto.getRid());

        return property;
    }
}
