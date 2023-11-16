package com.example.ticketBooking.service;

import com.example.ticketBooking.entity.UserInfo;
import com.example.ticketBooking.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepo userInfoRepo;
    @Autowired
    private PasswordEncoder encoder;

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userInfoRepo.save(userInfo);
        return "user added successfully...";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = userInfoRepo.findByName(username);
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found ..!!"));
    }

    public UserInfo getUserById(int id) {
        return userInfoRepo.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("user not found....!!"));
    }

    public String deleteUser(int id) {
        userInfoRepo.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("user not found....!!"));
        userInfoRepo.deleteById(id);
        return "user deleted successfully...";
    }

    public String updateUser(int id, UserInfo userInfo) {
        UserInfo existingUser = userInfoRepo.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("user not found....!!"));
        existingUser.setName(userInfo.getName());
        existingUser.setEmail(userInfo.getEmail());
        existingUser.setPassword(encoder.encode(userInfo.getPassword()));
        existingUser.setRole(userInfo.getRole());
        userInfoRepo.save(existingUser);
        return "user updated successfully...";
    }
}
