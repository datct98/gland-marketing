package com.example.be_project.controller;

import com.example.be_project.repository.WalletRepository;
import com.example.be_project.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marketing/api-wallet")
@Slf4j
public class WalletController {
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private WalletRepository walletRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getStatus(@RequestHeader(name="Authorization") String token,
                                       @RequestParam(required = false) long storeId,
                                       @RequestParam(required = false) int pageNum) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            //long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ",""));
            return ResponseEntity.ok(walletRepository.findAllWallets(storeId, PageRequest.of(pageNum, 10)));
        }
        throw new Exception("Un-authentication");
    }
}
