package com.ecg.webclient.feature.administration.authentication;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ecg.webclient.feature.administration.accessrole.SecurityAdminAccessRole;
import com.ecg.webclient.feature.administration.accessrole.SetupSystemAccessRole;
import com.ecg.webclient.feature.administration.service.ClientService;
import com.ecg.webclient.feature.administration.service.GroupService;
import com.ecg.webclient.feature.administration.service.RoleService;
import com.ecg.webclient.feature.administration.service.UserService;
import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.ecg.webclient.feature.administration.viewmodell.GroupDto;
import com.ecg.webclient.feature.administration.viewmodell.RoleDto;
import com.ecg.webclient.feature.administration.viewmodell.UserDto;

@Component
public class AuthenticationUtil
{
    private List<ClientDto> clients;
    private ClientDto       selectedClient;
    private ClientService   clientService;
    private UserService     userService;
    private GroupService    groupService;
    private RoleService     roleService;

    @Autowired
    public AuthenticationUtil(ClientService clientService, UserService userService,
            GroupService groupService, RoleService roleService)
    {
        this.clientService = clientService;
        this.userService = userService;
        this.groupService = groupService;
        this.roleService = roleService;
        initSelectedClient();
    }

    @Transactional
    public void changeUserPassword(String simpleEncodedPw)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();

        UserDto user = userService.getUserByLogin(login);
        // Pw wird im Service ein 2. Mal encoded
        user.setPassword(simpleEncodedPw);
        user.setChangePasswordOnNextLogin(false);
        userService.saveUser(user);

        // da Service kein neues PW zurückliefert, wird hier für das Token nochmal verschlüsselt
        String password = PasswordEncoder.encodeComplex(simpleEncodedPw, Long.toString(user.getId()));

        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
        // zugeordnete Rollen für den Client setzen
        for (GroupDto group : groupService.getGroupsForIds(user.getGroupIdObjects()))
        {
            // Nutzer mit dieser Rolle erhält alle ihm zugeordneten Rollen über
            // alle Mandanten
            if (auth.getAuthorities().contains(
                    new DbGrantedAuthoritiy(new SecurityAdminAccessRole().getName()))
                    || auth.getAuthorities().contains(
                            new DbGrantedAuthoritiy(new SetupSystemAccessRole().getName())))
            {
                for (RoleDto role : roleService.getRolesForIds(group.getRoleIdObjects()))
                {
                    DbGrantedAuthoritiy newAuth = new DbGrantedAuthoritiy(role.getName());
                    grantedAuths.add(newAuth);
                }
            }
            // jeder andere Nutzer erhält nur die ihm zugeordneten Rollen des
            // gerade ausgewählten Mandanten
            else
            {
                if (groupService.getClientForGroupId(group.getId()).getId() == selectedClient.getId())
                {
                    for (RoleDto role : roleService.getRolesForIds(group.getRoleIdObjects()))
                    {
                        DbGrantedAuthoritiy newAuth = new DbGrantedAuthoritiy(role.getName());
                        grantedAuths.add(newAuth);
                    }
                }
            }
        }

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(login, password, grantedAuths));
    }

    public String getClientChangePath()
    {
        return "/changeClient";
    }

    public List<ClientDto> getClients()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();

        UserDto user = userService.getUserByLogin(login);

        clients = new ArrayList<ClientDto>();

        if (auth.getAuthorities().contains(new DbGrantedAuthoritiy(new SecurityAdminAccessRole().getName()))
                || auth.getAuthorities().contains(
                        new DbGrantedAuthoritiy(new SetupSystemAccessRole().getName())))
        {
            clients = clientService.getAllClients(true);
        }
        else
        {
            for (ClientDto client : clientService.getAssignedClientsForGroups(user.getGroupIdObjects()))
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
        if (selectedClient != null)
        {
            selectedClient = clientService.getClient(selectedClient.getId());
        }
        return selectedClient;
    }

    public void loginAsUser(Long userId)
    {
        UserDto user = userService.getUserById(userId);
        String password = userService.getPassword(userId);

        this.selectedClient = clientService.getClient(Long.parseLong(user.getDefaultClient()));

        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
        // zugeordnete Rollen für den Client setzen
        for (GroupDto group : groupService.getGroupsForIds(user.getGroupIdObjects()))
        {
            if (groupService.getClientForGroupId(group.getId()).getId() == selectedClient.getId())
            {
                for (RoleDto role : roleService.getRolesForIds(group.getRoleIdObjects()))
                {
                    DbGrantedAuthoritiy newAuth = new DbGrantedAuthoritiy(role.getName());
                    grantedAuths.add(newAuth);
                }
            }
        }

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user.getLogin(), password, grantedAuths));

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

        UserDto user = userService.getUserByLogin(login);

        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
        // zugeordnete Rollen für den Client setzen
        for (GroupDto group : groupService.getGroupsForIds(user.getGroupIdObjects()))
        {
            // Nutzer mit dieser Rolle erhält alle ihm zugeordneten Rollen über
            // alle Mandanten
            if (auth.getAuthorities().contains(
                    new DbGrantedAuthoritiy(new SecurityAdminAccessRole().getName()))
                    || auth.getAuthorities().contains(
                            new DbGrantedAuthoritiy(new SetupSystemAccessRole().getName())))
            {
                for (RoleDto role : roleService.getRolesForIds(group.getRoleIdObjects()))
                {
                    DbGrantedAuthoritiy newAuth = new DbGrantedAuthoritiy(role.getName());
                    grantedAuths.add(newAuth);
                }
            }
            // jeder andere Nutzer erhält nur die ihm zugeordneten Rollen des
            // gerade ausgewählten Mandanten
            else
            {
                if (groupService.getClientForGroupId(group.getId()).getId() == selectedClient.getId())
                {
                    for (RoleDto role : roleService.getRolesForIds(group.getRoleIdObjects()))
                    {
                        DbGrantedAuthoritiy newAuth = new DbGrantedAuthoritiy(role.getName());
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
        List<ClientDto> clients = clientService.getAllClients(true);
        if (!clients.isEmpty())
        {
            this.selectedClient = clients.get(0);
        }
    }
}