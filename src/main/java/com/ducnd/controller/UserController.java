package com.ducnd.controller;

import com.ducnd.Constants;
import com.ducnd.manager.UserManager;
import com.ducnd.model.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserManager userManager;

    @PostMapping(Constants.ENPOINT_REGISTER)
    public Object register(
            @RequestBody RegisterRequest request
            ){
        return userManager.register(request);
    }
}
