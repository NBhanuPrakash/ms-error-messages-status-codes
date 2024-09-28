package com.NNTeachie.controller;

import com.NNTeachie.dto.ErrorMessageDto;
import com.NNTeachie.dto.ErrorMessagesReponse;
import com.NNTeachie.service.ErrorMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ErrorMessageController {

    @Autowired
    private ErrorMessageService service;

    @PostMapping("/errors")
    public ResponseEntity<ErrorMessagesReponse> saveData(@RequestBody ErrorMessageDto dto) {
        return ResponseEntity.ok(service.saveMessages(dto));
    }

    @GetMapping("/errors")
    public ResponseEntity<List<ErrorMessagesReponse>> getAllData() {
        return ResponseEntity.ok(service.listOfMessages());
    }

    @GetMapping("/errors/{id}")
    public ResponseEntity<ErrorMessagesReponse> getAllData(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/errors/{id}")
    public ResponseEntity<ErrorMessagesReponse> upadateMessage(
            @RequestBody ErrorMessageDto errorMessageDto, @PathVariable String id) {
        return ResponseEntity.ok(service.updateMessage(errorMessageDto, id));
    }

    @GetMapping("/errors/status/{statusCode}")
    public ResponseEntity<List<ErrorMessagesReponse>> statusList(@PathVariable Integer statusCode) {
        return ResponseEntity.ok(service.listStatusCode(statusCode));
    }
}
