package com.example.ticketBooking.controller;

import com.example.ticketBooking.entity.AuthRequest;
import com.example.ticketBooking.entity.UserInfo;
import com.example.ticketBooking.service.JwtService;
import com.example.ticketBooking.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserInfo userInfo) {
        return userInfoService.addUser(userInfo);
    }

    @GetMapping("/{id}")
    public UserInfo getUserById(@PathVariable("id") int id) {
        return userInfoService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        return userInfoService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") int id, @RequestBody UserInfo userInfo) {
        return userInfoService.updateUser(id, userInfo);
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request...!!!!");
        }
    }
}
