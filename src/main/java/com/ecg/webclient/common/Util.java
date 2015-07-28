package com.ecg.webclient.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ecg.webclient.feature.administration.persistence.api.IClientRepository;
import com.ecg.webclient.feature.administration.persistence.api.IUserRepository;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Group;
import com.ecg.webclient.feature.administration.persistence.dbmodell.User;
import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.ecg.webclient.feature.administration.viewmodell.mapper.ClientMapper;

@Component
public class Util
{
    private List<ClientDto>   clients;
    private boolean           isMenuMinimized;
    private ClientDto         selectedClient;
    private IClientRepository clientRepository;
    private IUserRepository   userRepository;

    @Autowired
    public Util(IClientRepository clientRepository, IUserRepository userRepository)
    {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        initSelectedClient();
    }

    public String getClientChangePath()
    {
        return "/changeClient";
    }

    public List<ClientDto> getClients()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();

        User user = userRepository.getUserByLogin(login);

        clients = new ArrayList<ClientDto>();

        List<Object> groupRids = new ArrayList<Object>();
        List<Group> assignedGroups = user.getGroups();
        for (Group group : assignedGroups)
        {
            groupRids.add(group.getRid());
        }

        for (ClientDto client : ClientMapper.mapToDtos(clientRepository
                .getAssignedClientsForGroups(groupRids)))
        {
            if (client.isEnabled())
            {
                clients.add(client);
            }
        }

        if (selectedClient == null)
        {
            initSelectedClient();
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

    private void initSelectedClient()
    {
        List<ClientDto> clients = ClientMapper.mapToDtos(clientRepository.getAllClients());
        if (!clients.isEmpty())
        {
            this.selectedClient = clients.get(0);
        }
    }
}
