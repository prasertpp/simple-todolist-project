package com.demo.todos.service;

import com.demo.todos.controller.TodoListController;
import com.demo.todos.model.entity.TodoListTransaction;
import com.demo.todos.model.request.TodoListInsertRequest;
import com.demo.todos.model.response.CommonResponse;
import com.demo.todos.model.response.ErrorResponse;
import com.demo.todos.model.response.TodoListInsertResponse;
import com.demo.todos.repository.TodoTransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class TodoListService {

    private static final Logger logger = LogManager.getLogger(TodoListService.class);
    @Autowired
    private TodoTransactionRepository todoTransactionRepository;

    public CommonResponse insertTodoTransaction(TodoListInsertRequest request){

        TodoListTransaction transaction = prepareTodoTransaction(request);
        TodoListTransaction saveResult = todoTransactionRepository.save(transaction);
        CommonResponse commonResponse = new CommonResponse();
        if(saveResult != null){
            logger.info("INSERT SUCCESSFULLY");
            commonResponse.setStatus("SUCCESS");
            TodoListInsertResponse todoListInsertResponse =  new TodoListInsertResponse();
            todoListInsertResponse.setMessage(saveResult.getMessage());
            todoListInsertResponse.setMessageId(saveResult.getId().toString());
            commonResponse.setData(todoListInsertResponse);
            commonResponse.setHttpStatus(HttpStatus.CREATED);
        }else {
            logger.error("INSERT FAILED");
            commonResponse.setStatus("ERROR");
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setError("Can't insert to db");
            commonResponse.setData(errorResponse);
            commonResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return commonResponse;

    }

    private TodoListTransaction prepareTodoTransaction(TodoListInsertRequest request) {
        TodoListTransaction transaction = new TodoListTransaction();
        transaction.setId(UUID.randomUUID());
        Date date = Calendar.getInstance().getTime();
        transaction.setCreatedDate(date);
        transaction.setUpdatedDate(date);
        transaction.setActivated("Y");
        transaction.setMessage(request.getMessage());
        return transaction;
    }

}
