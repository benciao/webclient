package com.ecg.webclient.common.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ecg.webclient.feature.administration.persistence.api.IClientDto;
import com.ecg.webclient.feature.administration.persistence.api.IClientRepository;
import com.ecg.webclient.feature.administration.persistence.api.IGroupDto;
import com.ecg.webclient.feature.administration.persistence.api.IGroupRepository;
import com.ecg.webclient.feature.administration.persistence.api.IRoleDto;
import com.ecg.webclient.feature.administration.persistence.api.IRoleRepository;
import com.ecg.webclient.feature.administration.persistence.api.IUserDto;
import com.ecg.webclient.feature.administration.persistence.api.IUserRepository;

@Component
public class AuthenticationUtil
{
    private List<IClientDto>  clients;
    private boolean           isMenuMinimized;
    private IClientDto        selectedClient;
    private IClientRepository clientRepository;
    private IUserRepository   userRepository;
    private IGroupRepository  groupRepository;
    private IRoleRepository   roleRepository;

    @Autowired
    public AuthenticationUtil(IClientRepository clientRepository, IUserRepository userRepository,
            IGroupRepository groupRepository, IRoleRepository roleRepository)
    {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.roleRepository = roleRepository;
        initSelectedClient();
    }

    public String getClientChangePath()
    {
        return "/changeClient";
    }

    public List<IClientDto> getClients()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();

        IUserDto user = userRepository.getUserByLogin(login);

        clients = new ArrayList<IClientDto>();

        if (auth.getAuthorities().contains(new OdbGrantedAuthoritiy("SETUP_ROLE")))
        {
            clients = clientRepository.getAllClients(true);
        }
        else
        {
            List<Object> groupRids = new ArrayList<Object>();
            List<Object> assignedGroupIds = user.getGroupRidObjects();
            for (Object id : assignedGroupIds)
            {
                groupRids.add(id);
            }

            for (IClientDto client : clientRepository.getAssignedClientsForGroups(groupRids))
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
        for (IClientDto clientDto : clients)
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

    public IClientDto getSelectedClient()
    {
        if (!selectedClient.getRid().toString().equalsIgnoreCase("0"))
        {
            selectedClient = clientRepository.getClient(selectedClient.getRid());
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

    public void setSelectedClient(IClientDto selectedClient)
    {
        this.selectedClient = selectedClient;
    }

    public void setSelectedClientWithNewAuthority(IClientDto selectedClient)
    {
        this.selectedClient = selectedClient;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        String password = auth.getCredentials().toString();

        IUserDto user = userRepository.getUserByLogin(login);

        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
        // zugeordnete Rollen für den Client setzen
        for (IGroupDto group : groupRepository.getGroupsForIds(user.getGroupRidObjects()))
        {
            // Nutzer mit dieser Rolle erhält alle ihm zugeordneten Rollen über alle Mandanten
            if (auth.getAuthorities().contains(new OdbGrantedAuthoritiy("SETUP_ROLE")))
            {
                for (IRoleDto role : roleRepository.getRolesForIds(group.getRoleRidObjects()))
                {
                    OdbGrantedAuthoritiy newAuth = new OdbGrantedAuthoritiy(role.getName());
                    grantedAuths.add(newAuth);
                }
            }
            // jeder andere Nutzer erhält nur die ihm zugeordneten Rollen des gerade ausgewählten Mandanten
            else
            {
                if (groupRepository.getClientForGroupId(group.getRid()).getRid().toString()
                        .equals(selectedClient.getRid().toString()))
                {
                    for (IRoleDto role : roleRepository.getRolesForIds(group.getRoleRidObjects()))
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
        List<IClientDto> clients = clientRepository.getAllClients(true);
        if (!clients.isEmpty())
        {
            this.selectedClient = clients.get(0);
        }
    }
}
