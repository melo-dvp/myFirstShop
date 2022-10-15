package com.ates.myfirstshop.controller;

import com.ates.myfirstshop.common.ApiResponse;
import com.ates.myfirstshop.config.KeycloakConfig;
import com.ates.myfirstshop.dto.user.RoleDTO;
import com.ates.myfirstshop.dto.user.UserDTO;
import com.ates.myfirstshop.dto.user.UserRoleDTO;
import com.ates.myfirstshop.service.KeycloakService;
import lombok.AllArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/keycloak/user")
public class KeycloakController {

    KeycloakService service;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addUser(@RequestBody UserDTO userDTO){
        Response createdResponse = service.addUser(userDTO);
        return new ResponseEntity<>(new ApiResponse(true, "user has been created"), HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public List<UserRepresentation> getUser(@PathVariable("userName") String userName){
        List<UserRepresentation> user = service.getUser(userName);
        return user;
    }

    @GetMapping("/getAllUser")
    public List<UserRepresentation> getAllUser(){
        List<UserRepresentation> users = service.getAllUser();
        return users;
    }

    @PutMapping("/update/{userId}")
    public String updateUser(@PathVariable("userId") String userId, @RequestBody UserDTO userDTO){
        service.updateUser(userId, userDTO);
        return "User Details updated successfully";
    }

    @RolesAllowed("admin")
    @DeleteMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") String userId){
        service.deleteUser(userId);
        return "User deleted successfully";
    }

    @GetMapping("/verification-link/{userId}")
    public String sendVerificationLink(@PathVariable("userId") String userId){
        service.sendVerificationLink(userId);
        return "Verification Link Send to Registered E-Mail Address";
    }

    @GetMapping("/reset-password/{userId}")
    public String sendResetPassword(@PathVariable("userId") String userId){
        service.sendResetPassword(userId);
        return "Reset Password Link send successfully to registered E-Mail Address";
    }

    @GetMapping("/getAllRoles")
    public List<String> getAllRoles(){
        return service.getAllRoles();
    }

    @PostMapping("/addNewRole")
    public String addNewRole(@RequestBody RoleDTO roleDTO){
        service.addRealmRole(roleDTO);
        return "added successfully the new role";
    }

    @PostMapping("/addRoleToUser")
    public String addRoleToUser(@RequestBody UserRoleDTO userRoleDTO){
        service.addRoleToUser(userRoleDTO);
        return "added successfully the role to user";
    }

}
