package com.example.be_project.service;

import com.example.be_project.model.entities.User;
import com.example.be_project.model.entities.UserStore;
import com.example.be_project.model.entities.Wallet;
import com.example.be_project.repository.UserRepository;
import com.example.be_project.repository.UserStoreRepository;
import com.example.be_project.repository.WalletRepository;
import com.example.be_project.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {
    @Autowired
    private UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UserStoreRepository userStoreRepository;

    @Transactional
    public String createUser(User user, long storeId, String createdBy){
        User userNew = new User();
        try{
            //check user
            userService.loadUserByUsername(user.getUsername());
            return "Username đã tồn tại";
        } catch (UsernameNotFoundException e){
            userNew.setEmail(user.getEmail());
            userNew.setUsername(user.getUsername());
            userNew.setPositionId(user.getPositionId());
            userNew.setFullName(user.getFullName());
            userNew.setStatus(Status.ACTIVE);
            userNew.setPhone(user.getPhone());
            userNew.setDepartmentKey(user.getDepartmentKey());
            userNew.setPosition(user.getPosition());
            userNew.setPassword(passwordEncoder.encode(user.getPassword()));
            userNew.setCreatedBy(createdBy);
            userRepository.save(userNew);
            Wallet wallet = new Wallet();
            wallet.setUserId(userNew.getId());
            wallet.setAmount(new BigDecimal(0));
            wallet.setName("Ví của "+user.getFullName());
            walletRepository.save(wallet);
            UserStore userStore = new UserStore();
            userStore.setStoreId(storeId);
            userStore.setUserId(userNew.getId());
            userStoreRepository.save(userStore);
            return "Tạo tài khoản thành công" ;
        } catch (Exception e){
            return "Somethings wrong!";
        }
    }
}
