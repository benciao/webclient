package com.ecg.webclient.common.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ecg.webclient.feature.administration.persistence.api.IClientRepository;
import com.ecg.webclient.feature.administration.persistence.api.IUserRepository;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Group;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Role;
import com.ecg.webclient.feature.administration.persistence.dbmodell.User;
import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.ecg.webclient.feature.administration.viewmodell.mapper.ClientMapper;

@Component
public class AuthenticationUtil
{
    private List<ClientDto>   clients;
    private boolean           isMenuMinimized;
    private ClientDto         selectedClient;
    private IClientRepository clientRepository;
    private IUserRepository   userRepository;

    @Autowired
    public AuthenticationUtil(IClientRepository clientRepository, IUserRepository userRepository)
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

        if (auth.getAuthorities().contains(new OdbGrantedAuthoritiy("SETUP_ROLE")))
        {
            clients = ClientMapper.mapToDtos(clientRepository.getAllClients(true));
        }
        else
        {
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

    public void setSelectedClientWithNewAuthority(ClientDto selectedClient)
    {
        this.selectedClient = selectedClient;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        String password = auth.getCredentials().toString();

        User user = userRepository.getUserByLogin(login);

        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
        // zugeordnete Rollen für den Client setzen
        for (Group group : user.getGroups())
        {
            if (auth.getAuthorities().contains(new OdbGrantedAuthoritiy("SETUP_ROLE")))
            {
                for (Role role : group.getRoles())
                {
                    OdbGrantedAuthoritiy newAuth = new OdbGrantedAuthoritiy(role.getName());
                    grantedAuths.add(newAuth);
                }
            }
            else
            {
                if (group.getClient().equals(ClientMapper.mapToEntity(selectedClient)))
                {
                    for (Role role : group.getRoles())
                    {
                        OdbGrantedAuthoritiy newAuth = new OdbGrantedAuthoritiy(role.getName());
                        grantedAuths.add(newAuth);
                    }
                }
            }
        }

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(login, password, grantedAuths));
    }

    private void initSelectedClient()
    {
        List<ClientDto> clients = ClientMapper.mapToDtos(clientRepository.getAllClients(true));
        if (!clients.isEmpty())
        {
            this.selectedClient = clients.get(0);
        }
    }
}
