package com.ecg.webclient.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecg.webclient.feature.administration.persistence.api.IClientRepository;
import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.ecg.webclient.feature.administration.viewmodell.mapper.ClientMapper;

@Component
public class Util
{
    private List<ClientDto>   clients;
    private boolean           isMenuMinimized;
    private ClientDto         selectedClient;
    private IClientRepository clientRepository;

    @Autowired
    public Util(IClientRepository clientRepository)
    {
        this.clientRepository = clientRepository;

        List<ClientDto> clients = ClientMapper.mapToDtos(clientRepository.getAllClients());
        if (!clients.isEmpty())
        {
            this.selectedClient = clients.get(0);
        }
    }

    public String getClientChangePath()
    {
        return "/changeClient";
    }

    public List<ClientDto> getClients()
    {
        clients = new ArrayList<ClientDto>();

        for (ClientDto client : ClientMapper.mapToDtos(clientRepository.getAllClients()))
        {
            if (client.isEnabled())
            {
                clients.add(client);
            }
        }

        boolean selectedClientNotIncluded = true;
        for (ClientDto clientDto : clients)
        {
            if (clientDto.equals(selectedClient))
            {
                selectedClientNotIncluded = false;
                break;
            }
        }

        if (selectedClientNotIncluded)
        {
            setSelectedClient(clients.get(0));
        }

        return clients;
    }

    public ClientDto getSelectedClient()
    {
        if (!selectedClient.getRid().toString().equalsIgnoreCase("0"))
        {
            selectedClient = ClientMapper.mapToDto(clientRepository.getClientById(selectedClient.getRid()));
        }
        return selectedClient;
    }

    public boolean isMenuMinimized()
    {
        return isMenuMinimized;
    }

    public void setMenuMinimized(boolean isMenuMinimized)
    {
        this.isMenuMinimized = isMenuMinimized;
    }

    public void setSelectedClient(ClientDto selectedClient)
    {
        this.selectedClient = selectedClient;
    }
}
