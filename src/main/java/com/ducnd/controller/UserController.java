package com.ducnd.controller;

import com.ducnd.Constants;
import com.ducnd.manager.UserManager;
import com.ducnd.model.request.RegisterRequest;
import com.ducnd.model.request.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserManager userManager;

    @PostMapping(Constants.ENPOINT_REGISTER)
    public Object register(
            @RequestBody RegisterRequest request
    ) {
        return userManager.register(request);
    }

    @GetMapping(Constants.ENPOINT_FINDUSER)
    public Object getUser(@RequestParam("username") String username) {
        return userManager.findUser(username);
    }

    @PutMapping(Constants.ENPOINT_SAVEUSER)
    public Object updateUser(@PathVariable(value = "username") String username, @Valid @RequestBody UpdateUserRequest upDate) {
        return userManager.saveUser(username, upDate);

    }

}
