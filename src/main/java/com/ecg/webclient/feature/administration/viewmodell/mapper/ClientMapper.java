package com.ecg.webclient.feature.administration.viewmodell.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.dbmodell.Client;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Property;
import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.ecg.webclient.feature.administration.viewmodell.PropertyDto;

public class ClientMapper
{
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
            dto.setRid(client.getRid());

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
            dto.setRid(client.getRid());

            for (Property property : client.getProperties())
            {
                dto.getProperties().add(PropertyMapper.mapToDto(property));
            }

            result.add(dto);
        }

        return result;
    }

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
            client.setRid(dto.getRid());

            for (PropertyDto propertyDto : dto.getProperties())
            {
                client.getProperties().add(PropertyMapper.mapToEntity(propertyDto));
            }

            result.add(client);
        }

        return result;
    }

    public static Client mapToEntity(ClientDto dto)
    {
        Client client = new Client();
        client.setColor(dto.getColor());
        client.setDescription(dto.getDescription());
        client.setName(dto.getName());
        client.setEnabled(dto.isEnabled());
        client.setRid(dto.getRid());

        for (PropertyDto propertyDto : dto.getProperties())
        {
            client.getProperties().add(PropertyMapper.mapToEntity(propertyDto));
        }

        return client;
    }
}
