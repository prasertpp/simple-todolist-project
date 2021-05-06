package com.demo.todos.controller;

import com.demo.todos.model.request.TodoListInsertRequest;
import com.demo.todos.model.response.CommonResponse;
import com.demo.todos.service.TodoListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class TodoListController {

    private static final Logger logger = LogManager.getLogger(TodoListController.class);

    @Autowired
    private TodoListService todoListService;

    @PostMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> insertTodo(@Valid @RequestBody TodoListInsertRequest request){
        logger.info("START IMPLEMENTING INSERT TODOLIST, message : {} ",request.getMessage());
        CommonResponse commonResponse = todoListService.insertTodoTransaction(request);
        ResponseEntity<CommonResponse> response = new ResponseEntity<>(commonResponse,commonResponse.getHttpStatus());
        logger.info("END IMPLEMENTING INSERT TODOLIST, response : {} ",commonResponse);
        return response;
    }


    @PutMapping(value = "/{messageId}",produces = MediaType.APPLICATION_JSON_VALUE,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> updateTodo(@Valid @PathVariable("messageId") String messageId, @RequestBody TodoListInsertRequest request){
        logger.info("START IMPLEMENTING UPDATE TODOLIST, message : {} ",request.getMessage());
        CommonResponse commonResponse = todoListService.updateTodoTransaction(request,messageId);
        ResponseEntity<CommonResponse> response = new ResponseEntity<>(commonResponse,commonResponse.getHttpStatus());
        logger.info("END IMPLEMENTING UPDATE TODOLIST, response : {} ",commonResponse);
        return response;
    }


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getTodoList(){
        logger.info("START IMPLEMENTING RETRIEVE TODOLIST");
        CommonResponse commonResponse = todoListService.getAllTodoTransaction();
        ResponseEntity<CommonResponse> response = new ResponseEntity<>(commonResponse,commonResponse.getHttpStatus());
        logger.info("END IMPLEMENTING RETRIEVE TODOLIST, response : {} ",commonResponse);
        return response;
    }


    @DeleteMapping(value = "/{messageId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> removeTodo(@PathVariable("messageId") String messageId){
        logger.info("START IMPLEMENTING REMOVE TODOLIST");
        CommonResponse commonResponse = todoListService.removeTodoTransaction(messageId);
        ResponseEntity<CommonResponse> response = new ResponseEntity<>(commonResponse,commonResponse.getHttpStatus());
        logger.info("END IMPLEMENTING REMOVE TODOLIST, response : {} ",commonResponse);
        return response;
    }


}
