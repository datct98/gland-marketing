package com.example.be_project.service.configuration;

import com.example.be_project.model.entities.configuration.Person;
import com.example.be_project.repository.config.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    //@Cacheable(key = "userId", value = "people")
    public List<Person> getPeopleFromStoreId(long storeId){
        return personRepository.findAllByStoreId(storeId);
    }

    public String editPerson(long id, String name){
        Person person = personRepository.findById(id).orElse(null);
        if(person!=null){
            person.setName(name);
            personRepository.save(person);
            return "Cập nhật thành công";
        }
        return "Không tìm thấy đối tượng cần cỉnh sửa";
    }
}
