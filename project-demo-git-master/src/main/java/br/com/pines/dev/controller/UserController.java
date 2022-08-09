package br.com.pines.dev.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pines.dev.model.Users;
import br.com.pines.dev.model.dto.UserForm;
import br.com.pines.dev.repository.RoleRepository;
import br.com.pines.dev.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Users> createUser(@RequestBody @Valid UserForm userForm){
        Users user = userForm.conversor(userForm);
        Optional<Users> userOptional = userRepository.findById(user.getUsername());
        if (userOptional.isPresent()){
            return ResponseEntity.badRequest().build();
        } else {
            user.roleDefaultAdd(roleRepository);
            user.updatePasswordEncoder(passwordEncoder);
            userRepository.save(user);
            return ResponseEntity.ok().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{username}")
    @Transactional
    public ResponseEntity<Users> deleteUser(@PathVariable String username){
        Optional<Users> user = userRepository.findById(username);
        if (user.isPresent()){
            userRepository.deleteById(username);
            return ResponseEntity.ok().build();
        } return ResponseEntity.badRequest().build();
    }

    
    @GetMapping
    @Transactional
    public List<Users> listUsers(){
        List<Users> users = userRepository.findAll();
        return users;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{username}")
    @Transactional
    public ResponseEntity<Users> UserByUsername(@PathVariable String username){
        Optional<Users> user = userRepository.findById(username);
        if (user.isPresent()){
            return ResponseEntity.ok(user.get());
        } return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{username}")
    @Transactional
    public ResponseEntity<Users> updateUserToAdmin(@PathVariable String username){
        Optional<Users> user = userRepository.findById(username);
        if (user.isPresent()){
            user.get().updateAdmin(roleRepository);
            return ResponseEntity.ok(user.get());
        } return ResponseEntity.notFound().build();
    }
}
