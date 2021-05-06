package com.demo.todos.controller;

import com.demo.todos.model.request.TodoListInsertRequest;
import com.demo.todos.model.response.CommonResponse;
import com.demo.todos.service.TodoListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoListController {

    private static final Logger logger = LogManager.getLogger(TodoListController.class);

    @Autowired
    private TodoListService todoListService;

    @PostMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> insertTodo(@RequestBody TodoListInsertRequest request){
        logger.info("START IMPLEMENTING INSERT TODOLIST, message : {} ",request.getMessage());
        CommonResponse commonResponse = todoListService.insertTodoTransaction(request);
        ResponseEntity<CommonResponse> response = new ResponseEntity<>(commonResponse,commonResponse.getHttpStatus());
        logger.info("END IMPLEMENTING INSERT TODOLIST, response : {} ",commonResponse);
        return response;
    }

}
