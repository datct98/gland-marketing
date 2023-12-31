package com.example.be_project;

import com.example.be_project.model.entities.User;
import com.example.be_project.repository.UserRepository;
import com.example.be_project.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BeProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BeProjectApplication.class, args);
    }
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        /*User userNew = new User();
        userNew.setEmail("it.gland84@gmail.com");
        userNew.setUsername("gnguyen84");
        userNew.setStatus(Status.ACTIVE);
        userNew.setStoreId(1);
        userNew.setPassword(passwordEncoder.encode("123"));
        userRepository.save(userNew);*/
    }
}
