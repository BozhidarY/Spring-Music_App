package com.example.demo.SpringControllers;

import com.example.demo.DTO.LoginFormDTO;
import com.example.demo.SpringService.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/{username}/dashboard")
    public HashMap<String, String> showAdminCommands(){
        return adminService.showAdminCommands();
    }

    @PostMapping("/{username}/dashboard/deleteUser")
    public ResponseEntity<?> deleteUser(@PathVariable String username, @RequestBody LoginFormDTO loginFormDTO){
        String usernameD = loginFormDTO.getUsername();
        String password = loginFormDTO.getPassword();
        if(adminService.deleteUserAccount(usernameD, password)){
            return ResponseEntity.status(HttpStatus.OK).body("User deleted");
        }
        return ResponseEntity.status(HttpStatus.OK).body("User not found");

    }

    @PostMapping("{username}/dashboard/recoverUser")
    public ResponseEntity<?> recoverUser(@PathVariable String username, @RequestBody LoginFormDTO loginFormDTO){
        String usernameD = loginFormDTO.getUsername();
        String password = loginFormDTO.getPassword();
        if(adminService.recoverUserAccount(username, password)){
            return ResponseEntity.status(HttpStatus.OK).body("User recovered");
        }
        return ResponseEntity.status(HttpStatus.OK).body("User not found");
    }


}
