package com.ducnd.controller;

import com.ducnd.manager.MessageManager;
import com.ducnd.model.request.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lap trinh on 3/28/2018.
 */
@RestController
public class MessageController {
    @Autowired
    private MessageManager manager;

    @PostMapping(value = "api/sendMessage")
    public ResponseEntity sendMessage(
        @RequestBody SendMessageRequest request
    ){
        return new ResponseEntity(manager.sendMessage(request),
                HttpStatus.OK);
    }

    @GetMapping(value = "/api/messages")
    public ResponseEntity messages(){
        return new ResponseEntity(manager.messages(),
                HttpStatus.OK);
    }

}
