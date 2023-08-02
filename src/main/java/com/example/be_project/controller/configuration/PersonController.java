package com.example.be_project.controller.configuration;

import com.example.be_project.model.entities.configuration.Person;
import com.example.be_project.repository.config.PersonRepository;
import com.example.be_project.service.configuration.PersonService;
import com.example.be_project.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marketing/api/person-config")
public class PersonController {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonService personService;

    @GetMapping("/all")
    public ResponseEntity<?> getPeople(@RequestHeader(name="Authorization") String token,
                                       @RequestParam(required = false) long storeId) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            //long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ",""));
            return ResponseEntity.ok(personService.getPeopleFromStoreId(storeId));
        }
        throw new Exception("Un-authentication");
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editPerson(@RequestHeader(name="Authorization") String token,
                                        @PathVariable long id, @RequestParam String name) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            return ResponseEntity.ok(personService.editPerson(id, name));
        }
        throw new Exception("Un-authentication");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePerson(@RequestHeader(name="Authorization") String token,
                                        @PathVariable long id) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            Person person = personRepository.findById(id).orElse(null);
            personRepository.delete(person);
            return ResponseEntity.ok("Xóa đối tượng thành công");
        }
        throw new Exception("Un-authentication");
    }
}
