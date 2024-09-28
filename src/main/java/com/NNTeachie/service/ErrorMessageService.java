package com.NNTeachie.service;


import com.NNTeachie.dto.ErrorMessageDto;
import com.NNTeachie.dto.ErrorMessagesReponse;
import com.NNTeachie.exception.ExceptionHandler;
import com.NNTeachie.model.ErrorMessages;
import com.NNTeachie.repo.ErrorMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ErrorMessageService {

    @Autowired
    private ErrorMessageRepo repo;


    public ErrorMessagesReponse saveMessages(ErrorMessageDto messageDto) {
        ErrorMessages messages = null;
        if (messageDto.getStatusCode() >= 200 && messageDto.getStatusCode() <= 600) {
            messages = mapToEntity(messageDto);
        } else {
            throw new ExceptionHandler("statusCode is between 200 and 500");
        }
        ErrorMessages save = repo.save(messages);
        ErrorMessagesReponse reponse = entityToDto(save);
        return reponse;
    }

    public List<ErrorMessagesReponse> listOfMessages() {
        List<ErrorMessages> list = repo.findAll();
        List<ErrorMessagesReponse> allData = getAllData(list);
        return allData;
    }

    public ErrorMessagesReponse getById(String id) {
        ErrorMessages messages = repo.findById(id).get();
        ErrorMessagesReponse response = entityToDto(messages);
        return response;
    }

    public ErrorMessagesReponse updateMessage(ErrorMessageDto messageDto, String id) {
        ErrorMessages updateData = repo.findById(id).get();
        ErrorMessages response = null;
        if (updateData != null) {
            updateData.setErrorMessage(messageDto.getErrorMessage());
            updateData.setUpdatedAt(LocalDateTime.now());
            response = repo.save(updateData);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User Doesn't Exit's");
        }
        return entityToDto(response);
    }

    public List<ErrorMessagesReponse> listStatusCode(Integer statusCode) {
        List<ErrorMessages> byStatusCode = null;
        if (statusCode >= 200 && statusCode <= 600) {
            byStatusCode = repo.findByStatusCode(statusCode);
        } else {
            throw new ExceptionHandler("statusCode is between 200 and 500");
        }
        return getAllData(byStatusCode);
    }


    public static ErrorMessages mapToEntity(ErrorMessageDto messageDto) {
        ErrorMessages messages = new ErrorMessages();
        messages.setErrorMessage(messageDto.getErrorMessage());
        messages.setStatusCode(messageDto.getStatusCode());
        messages.setCreatedAt(LocalDateTime.now());
        messages.setUpdatedAt(LocalDateTime.now());
        return messages;
    }

    public static ErrorMessagesReponse entityToDto(ErrorMessages messages1) {
        ErrorMessagesReponse reponse = new ErrorMessagesReponse();
        reponse.setId(messages1.getId());
        reponse.setErrorMessage(messages1.getErrorMessage());
        reponse.setCreatedAt(messages1.getCreatedAt());
        reponse.setUpdatedAt(messages1.getUpdatedAt());
        reponse.setStatusCode(messages1.getStatusCode());
        return reponse;
    }

    public static List<ErrorMessagesReponse> getAllData(List<ErrorMessages> messages) {
        List<ErrorMessagesReponse> responseList = new ArrayList<>();
        for (ErrorMessages errorMessage : messages) {
            ErrorMessagesReponse response = new ErrorMessagesReponse();
            response.setId(errorMessage.getId());
            response.setStatusCode(errorMessage.getStatusCode());
            response.setErrorMessage(errorMessage.getErrorMessage());
            response.setCreatedAt(errorMessage.getCreatedAt());
            response.setUpdatedAt(errorMessage.getUpdatedAt());
            responseList.add(response);
        }
        return responseList;
    }
}
