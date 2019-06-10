package com.bgi.demo.controller;

import com.bgi.demo.model.Contact;
import com.bgi.demo.repository.ContactRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/contacts"})
public class ContactController {

    private ContactRepository repository;

    ContactController(ContactRepository contactRepository){
        this.repository = contactRepository;
    }

    // CRUD Method - GET
    @GetMapping
    public List findAll(){
        return repository.findAll();
    }

    // we will go to the database and will try to retrieve the contact (select * from contact where id = ?).
    // If a contact is found, we return it (HTTP 200 - OK), else, we return HTTP 404 -Not Found.
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Contact> findByID(@PathVariable long id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    // CRUD Method - POST

    // The @RequestBody annotation indicates a method parameter should be bound to the body of the web request.
    // This means the method expects the following content from que request (in JSON format)
    @PostMapping
    public Contact create(@RequestBody Contact contact){
        // The id of the contact will be null, therefore the save method will perform an insert into the Contact table.
        return repository.save(contact);
    }

    // CRUD Method - PUT

    // In order to update a contact, we need to inform its ID in the path variable.
    // We also need to pass the updated contact.
    // Try to find the contact to be updated. If the contact is found, we update the values from the record from the
    // database with the values passed as parameter to the method and save it
    @PutMapping(value="/{id}")
    public ResponseEntity<Contact> update(@PathVariable("id") long id,
                                          @RequestBody Contact contact){
        return repository.findById(id)
                .map(record -> {
                    record.setName(contact.getName());
                    record.setEmail(contact.getEmail());
                    record.setPhone(contact.getPhone());
                    Contact updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    // CRUD Method - DELETE
    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
