package com.ecg.webclient.feature.administration.persistence.odbmapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.api.IClient;
import com.ecg.webclient.feature.administration.persistence.api.IClientDto;
import com.ecg.webclient.feature.administration.persistence.api.IProperty;
import com.ecg.webclient.feature.administration.persistence.api.IPropertyDto;
import com.ecg.webclient.feature.administration.persistence.odbmodell.OdbClient;
import com.ecg.webclient.feature.administration.viewmodell.ClientDto;

/**
 * Mapped die Eigenschaften einer in OrientDb bekannten Entität auf einen detachten Mandanten oder umgekehrt.
 * 
 * @author arndtmar
 */
public class OdbClientMapper
{
    /**
     * Wandelt einen persistenten Mandanten in einen detachten um
     * 
     * @param client
     *            persistenter Mandant
     * @return Detacheter Mandant
     */
    public static IClientDto mapToDto(IClient client)
    {
        if (client != null)
        {
            IClientDto dto = new ClientDto();
            dto.setColor(client.getColor());
            dto.setDescription(client.getDescription());
            dto.setName(client.getName());
            dto.setEnabled(client.isEnabled());
            dto.setDelete(false);
            dto.setRid(client.getRid());

            for (IProperty property : client.getProperties())
            {
                dto.getProperties().add(OdbPropertyMapper.mapToDto(property));
            }

            return dto;
        }
        else
        {
            return null;
        }
    }

    /**
     * Wandelt eine Liste von persistenten Mandanten in eine Liste von detachten Mandanten um
     * 
     * @param clients
     *            Liste von persistenten Mandanten
     * @return Liste von detachten Mandanten
     */
    public static List<IClientDto> mapToDtos(List<IClient> clients)
    {
        List<IClientDto> result = new AutoPopulatingList<IClientDto>(IClientDto.class);

        for (IClient client : clients)
        {
            IClientDto dto = new ClientDto();
            dto.setColor(client.getColor());
            dto.setDescription(client.getDescription());
            dto.setName(client.getName());
            dto.setEnabled(client.isEnabled());
            dto.setDelete(false);
            dto.setRid(client.getRid());

            for (IProperty property : client.getProperties())
            {
                dto.getProperties().add(OdbPropertyMapper.mapToDto(property));
            }

            result.add(dto);
        }

        return result;
    }

    /**
     * Wandelt eine Liste von detachten Mandanten in eine Liste von persistenten Mandanten um
     * 
     * @param dtos
     *            Liste von detachten Mandanten
     * @return Liste von persistenten Mandanten
     */
    public static List<IClient> mapToEntities(List<IClientDto> dtos)
    {
        List<IClient> result = new ArrayList<IClient>();

        for (IClientDto dto : dtos)
        {
            IClient client = new OdbClient();
            client.setColor(dto.getColor());
            client.setDescription(dto.getDescription());
            client.setName(dto.getName());
            client.setEnabled(dto.isEnabled());
            client.setRid(dto.getRid());

            for (IPropertyDto propertyDto : dto.getProperties())
            {
                client.getProperties().add(OdbPropertyMapper.mapToEntity(propertyDto));
            }

            result.add(client);
        }

        return result;
    }

    /**
     * Wandelt einen detachten Mandanten in einen persistenten um
     * 
     * @param dto
     *            Detachter Mandanten
     * @return Persistenter Mandanten
     */
    public static IClient mapToEntity(IClientDto dto)
    {
        IClient client = new OdbClient();
        client.setColor(dto.getColor());
        client.setDescription(dto.getDescription());
        client.setName(dto.getName());
        client.setEnabled(dto.isEnabled());
        client.setRid(dto.getRid());

        for (IPropertyDto propertyDto : dto.getProperties())
        {
            client.getProperties().add(OdbPropertyMapper.mapToEntity(propertyDto));
        }

        return client;
    }
}
