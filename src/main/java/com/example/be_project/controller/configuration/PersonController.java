package com.example.be_project.controller.configuration;

import com.example.be_project.model.entities.configuration.Form;
import com.example.be_project.model.entities.configuration.Person;
import com.example.be_project.model.request.ConfigRequest;
import com.example.be_project.model.response.DataResponse;
import com.example.be_project.repository.config.PersonRepository;
import com.example.be_project.service.configuration.PersonService;
import com.example.be_project.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("/all")
    public ResponseEntity<?> getPeople(@RequestHeader(name="Authorization") String token,
                                       @RequestParam(required = false) long storeId,
                                       @RequestParam int action,
                                       @RequestParam(required = false) int pageNum) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            //long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ",""));
            return ResponseEntity.ok(personRepository.findAllByStoreIdAndAction(storeId, action, PageRequest.of(pageNum, 8)).getContent());
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("")
    public ResponseEntity<?> getPeopleWithoutPage(@RequestHeader(name="Authorization") String token,
                                                  @RequestParam int action,
                                       @RequestParam(required = false) long storeId) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            //long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ",""));
            return ResponseEntity.ok(personRepository.findAllByStoreIdAndAction(storeId, action));
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.PUT)
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editPerson(@RequestHeader(name="Authorization") String token,
                                        @PathVariable long id, @RequestParam String name) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.OK.value(), personService.editPerson(id, name)) );
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.DELETE)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePerson(@RequestHeader(name="Authorization") String token,
                                        @PathVariable long id) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            Person person = personRepository.findById(id).orElse(null);
            personRepository.delete(person);
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.OK.value(), "Xóa đối tượng thành công"));
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.POST)
    @PostMapping("/create")
    public ResponseEntity<?> creatPerson(@RequestHeader(name="Authorization") String token,
                                        @RequestBody ConfigRequest request) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ",""));
            Person person = new Person();
            person.setName(request.getName());
            person.setAction(request.getAction());
            person.setCreatedBy(userId);
            person.setStoreId(request.getStoreId());
            person.setStatus(true);
            personRepository.save(person);
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.CREATED.value(), "Tạo mới thành công"));
        }
        throw new Exception("Un-authentication");
    }
}
