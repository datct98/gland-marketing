package com.example.be_project.controller;

import com.example.be_project.model.dto.Token;
import com.example.be_project.model.entities.Wallet;
import com.example.be_project.model.request.LoginRequest;
import com.example.be_project.model.entities.User;
import com.example.be_project.model.entities.UserDetail;
import com.example.be_project.model.response.DataResponse;
import com.example.be_project.repository.UserRepository;
import com.example.be_project.repository.WalletRepository;
import com.example.be_project.service.AccountService;
import com.example.be_project.service.UserService;
import com.example.be_project.util.JWTUtil;
import com.example.be_project.util.Status;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api-authen")
@Slf4j
public class UserController {
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.POST)
    @PostMapping("/register")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> addUser(@RequestBody User body,
                                     @RequestHeader(name="Authorization") String token,
                                     @RequestParam(required = false) long storeId) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ",""));
            User user = userRepository.findUserById(userId);
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.CREATED.value(), accountService.createUser(body, storeId, user.getUsername())));
        }
        throw new Exception("Un-authentication");
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

        System.out.println(jwtUtil.getUsernameFromJwt(jwt));
        return ResponseEntity.ok(new Token(jwt));
    }

}
