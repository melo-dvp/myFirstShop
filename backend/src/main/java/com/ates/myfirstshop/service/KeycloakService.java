package com.ates.myfirstshop.service;

import com.ates.myfirstshop.config.Credentials;
import com.ates.myfirstshop.config.KeycloakConfig;
import com.ates.myfirstshop.dto.user.RoleDTO;
import com.ates.myfirstshop.dto.user.UserDTO;
import com.ates.myfirstshop.dto.user.UserRoleDTO;
import com.ates.myfirstshop.exceptions.CustomException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeycloakService {

    @Value("${keycloak.realm}")
    public String realm;
    final static String clientId = "admin-client";


    private final KeycloakConfig kcConfig;

    public KeycloakService(KeycloakConfig keycloakConfig){
        this.kcConfig = keycloakConfig;
    }


    public Response addUser(UserDTO userDTO){
        UsersResource instance = KeycloakConfig.getInstance().realm(realm).users();
        CredentialRepresentation credential = Credentials.createPasswordCredentials(userDTO.getPassword());

        if(!this.getUser(userDTO.getUserName()).isEmpty()){
            throw new CustomException("username already exists: " + userDTO.getUserName());
        }

        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmailId());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);
        user.setEmailVerified(false);

        Response response = instance.create(user);
        return response;
    }

    public List<UserRepresentation> getUser(String userName){
        UsersResource usersResource = kcConfig.getInstance().realm(realm).users();
        List<UserRepresentation> user = usersResource.search(userName, true);
        return user;
    }

    public List<UserRepresentation> getAllUser(){
        UsersResource usersResource = kcConfig.getInstance().realm(realm).users();
        List<UserRepresentation> allUsers = usersResource.list();
        return allUsers;
    }

    public void updateUser(String userId, UserDTO userDTO){
        CredentialRepresentation credential = Credentials.createPasswordCredentials(userDTO.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmailId());
        user.setCredentials(Collections.singletonList(credential));

        UsersResource usersResource = kcConfig.getInstance().realm(realm).users();
        usersResource.get(userId).update(user);
    }

    public void deleteUser(String userId){
        UsersResource usersResource = kcConfig.getInstance().realm(realm).users();
        usersResource.get(userId).remove();
    }

    public void sendVerificationLink(String userId){
        UsersResource usersResource = kcConfig.getInstance().realm(realm).users();
        usersResource.get(userId).sendVerifyEmail();
    }

    public void sendResetPassword(String userId){
        UsersResource usersResource = kcConfig.getInstance().realm(realm).users();
        usersResource.get(userId).executeActionsEmail(Arrays.asList("UPDATE_PASSWORD"));
    }

    public List<String> getAllRoles(){
        List<String> availableRoles = kcConfig.getInstance()
                .realm(realm)
                .roles()
                .list()
                .stream()
                .map(role -> role.getName())
                .collect(Collectors.toList());
        return availableRoles;
    }

    public void addRealmRole(RoleDTO roleDTO){
        if(!getAllRoles().contains(roleDTO.getRoleName())){
            RoleRepresentation roleRep = new RoleRepresentation();
            roleRep.setName(roleDTO.getRoleName());
            roleRep.setDescription(roleDTO.getRoleDescription());
            kcConfig.getInstance().realm(realm).roles().create(roleRep);
        }
    }

    public void addRoleToUser(UserRoleDTO userRoleDTO){
        UsersResource usersResource = kcConfig.getInstance().realm(realm).users();
        UserResource user = usersResource.get(userRoleDTO.getUserId());

        List<RoleRepresentation> roleToAdd = new LinkedList<>();
        roleToAdd.add(kcConfig.getInstance()
                .realm(realm)
                .roles()
                .get(userRoleDTO.getRoleName())
                .toRepresentation()
        );

        user.roles().realmLevel().add(roleToAdd);
    }

}
