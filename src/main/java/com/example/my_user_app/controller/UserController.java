package com.example.my_user_app.controller;

import com.example.my_user_app.model.request.UserRequest;
import com.example.my_user_app.model.response.UserResponse;
import com.example.my_user_app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid UserRequest request){
        return service.create(request);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id){
    return service.getById(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id,
                       @RequestBody @Valid UserRequest request ) {
        service.updateById(id, request);
    }

    @PatchMapping("/delete/{id}")
    public UserResponse deleteById(@PathVariable Long id) {
        return service.deleteById(id);
    }
        @GetMapping
        public Page<UserResponse> getAllUsers (@PageableDefault(size = 10, sort = "id")
                Pageable pageable){
        return service.getAllUsers(pageable);
        }

    }



