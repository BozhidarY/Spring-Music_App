//package com.example.demo.SpringControllers;
//
//import com.example.demo.DTO.AppUserDTO;
//import com.example.demo.DTO.LoginResponceDTO;
//import com.example.demo.Entities.ApplicationUser;
//import com.example.demo.SpringService.AuthenticaitonService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping(path = "/auth")
//@CrossOrigin("*")
//public class AuthentictionController {
//
//    @Autowired
//    private AuthenticaitonService authenticaitonService;
//
//
//    @PostMapping("/register")
//    public ApplicationUser registerUserController(@RequestBody AppUserDTO appUserDTO){
//        return authenticaitonService.registerUser(appUserDTO.getUsername(), appUserDTO.getPassword());
//    }
//
//    @PostMapping("/login")
//    public LoginResponceDTO loginUser(@RequestBody AppUserDTO appUserDTO){
//        return authenticaitonService.loginUser(appUserDTO.getUsername(), appUserDTO.getPassword());
//    }
//}
