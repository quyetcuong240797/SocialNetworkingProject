package com.ducnd.controller;

import com.ducnd.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping(Constants.ENPOINT_REGISTER)
    public String getMessgae(){
        return "rest";
    }
}
