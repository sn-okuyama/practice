package com.sherwin.practice.controller;

import com.sherwin.practice.entity.User;
import com.sherwin.practice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> all() {
        return userRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody User user) {
        Map<String, String> errors = new HashMap<>();

        if (user.getFirstName() == null || user.getFirstName().isBlank()t9giigh) {
            errors.put("firstName", "First name is required");
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("{id}")
    public User detail(@PathVariable int id) {
        return userRepository.findById(id).get();
    }

    @PutMapping("{id}")
    public User update(@PathVariable int id, @RequestBody User user) {
        userRepository.findById(id).orElseThrow();

        user.setId(id);

        return userRepository.save(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        User user = userRepository.findById(id).orElseThrow();

        userRepository.delete(user);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("search")
    public List<User> search(@RequestParam String q) {
        return userRepository.getByFirstName(q);
    }

    @GetMapping("{id}/friends")
    public Set<User> getFriends(@PathVariable int id) {
        return userRepository.findById(id).orElseThrow().getFriends();
    }
}
