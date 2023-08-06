package com.example.be_project.controller;

import com.example.be_project.model.dto.Token;
import com.example.be_project.model.entities.Wallet;
import com.example.be_project.model.request.LoginRequest;
import com.example.be_project.model.entities.User;
import com.example.be_project.model.entities.UserDetail;
import com.example.be_project.repository.UserRepository;
import com.example.be_project.repository.WalletRepository;
import com.example.be_project.util.JWTUtil;
import com.example.be_project.util.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/marketing/api")
@Slf4j
public class UserController {
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.POST)
    @PostMapping("/register")
    @Transactional(rollbackFor = Exception.class)
    public String addUser(@RequestBody User user){
        User userNew = new User();
        try{
            //check user
            userNew.setEmail(user.getEmail());
            userNew.setUsername(user.getUsername());
            userNew.setPositionId(user.getPositionId());
            userNew.setFullName(user.getFullName());
            userNew.setStatus(Status.ACTIVE);
            userNew.setAddress(user.getAddress());
            userNew.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(userNew);
            Wallet wallet = new Wallet();
            wallet.setUserId(user.getId());
            wallet.setAmount(new BigDecimal(0));
            wallet.setName("Ví của "+user.getFullName());
            walletRepository.save(wallet);
            return "Register successfully";
        } catch (Exception e){
           return "Somethings wrong!";
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.POST)
    @PostMapping("/signing")
    public ResponseEntity<?> authenticateUser2(@RequestBody LoginRequest loginRequest) {

        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetail detail = (UserDetail) authentication.getPrincipal();

        // Trả về jwt cho người dùng.
        String jwt = jwtUtil.generateToken(detail);
        return ResponseEntity.ok(new Token(jwt));
    }

}
