package com.ecg.webclient.feature.administration.persistence.odbmapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.modell.Client;
import com.ecg.webclient.feature.administration.persistence.modell.Property;
import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.ecg.webclient.feature.administration.viewmodell.PropertyDto;

/**
 * Mapped die Eigenschaften einer in OrientDb bekannten Entität auf einen detachten Mandanten oder umgekehrt.
 * 
 * @author arndtmar
 */
public class ClientMapper
{
    /**
     * Wandelt einen persistenten Mandanten in einen detachten um
     * 
     * @param client
     *            persistenter Mandant
     * @return Detacheter Mandant
     */
    public static ClientDto mapToDto(Client client)
    {
        if (client != null)
        {
            ClientDto dto = new ClientDto();
            dto.setColor(client.getColor());
            dto.setDescription(client.getDescription());
            dto.setName(client.getName());
            dto.setEnabled(client.isEnabled());
            dto.setDelete(false);
            dto.setId(client.getId());

            for (Property property : client.getProperties())
            {
                dto.getProperties().add(PropertyMapper.mapToDto(property));
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
    public static List<ClientDto> mapToDtos(List<Client> clients)
    {
        List<ClientDto> result = new AutoPopulatingList<ClientDto>(ClientDto.class);

        for (Client client : clients)
        {
            ClientDto dto = new ClientDto();
            dto.setColor(client.getColor());
            dto.setDescription(client.getDescription());
            dto.setName(client.getName());
            dto.setEnabled(client.isEnabled());
            dto.setDelete(false);
            dto.setId(client.getId());

            for (Property property : client.getProperties())
            {
                dto.getProperties().add(PropertyMapper.mapToDto(property));
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
    public static List<Client> mapToEntities(List<ClientDto> dtos)
    {
        List<Client> result = new ArrayList<Client>();

        for (ClientDto dto : dtos)
        {
            Client client = new Client();
            client.setColor(dto.getColor());
            client.setDescription(dto.getDescription());
            client.setName(dto.getName());
            client.setEnabled(dto.isEnabled());

            for (PropertyDto propertyDto : dto.getProperties())
            {
                client.getProperties().add(PropertyMapper.mapToEntity(propertyDto));
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
    public static Client mapToEntity(ClientDto dto)
    {
        Client client = new Client();
        client.setColor(dto.getColor());
        client.setDescription(dto.getDescription());
        client.setName(dto.getName());
        client.setEnabled(dto.isEnabled());

        for (PropertyDto propertyDto : dto.getProperties())
        {
            client.getProperties().add(PropertyMapper.mapToEntity(propertyDto));
        }

        return client;
    }
}
