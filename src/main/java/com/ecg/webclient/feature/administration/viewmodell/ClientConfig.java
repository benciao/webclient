package com.ecg.webclient.feature.administration.viewmodell;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.api.IClientDto;

public class ClientConfig
{
    @Valid
    private List<IClientDto> clients;

    public List<IClientDto> getClients()
    {
        if (clients == null)
        {
            clients = new AutoPopulatingList<IClientDto>(IClientDto.class);
        }
        return clients;
    }

    public void removeDeleted()
    {
        List<IClientDto> clientsToRemove = new ArrayList<IClientDto>();
        for (IClientDto client : clients)
        {
            if (client.isDelete())
            {
                clientsToRemove.add(client);
            }
        }
        clients.removeAll(clientsToRemove);
    }

    public void setClients(List<IClientDto> clients)
    {
        this.clients = clients;
    }
}
