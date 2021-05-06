package com.demo.todos.service;

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

import static com.demo.todos.constant.CommonConstant.ACTIVATED_MESSAGE;

@Service
public class TodoListService {

    private static final Logger logger = LogManager.getLogger(TodoListService.class);
    @Autowired
    private TodoTransactionRepository todoTransactionRepository;

    public CommonResponse insertTodoTransaction(TodoListInsertRequest request){

        TodoListTransaction transaction = prepareInsertTodoTransaction(request);
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


    public CommonResponse updateTodoTransaction(TodoListInsertRequest request,String messageIdStr) {

        UUID messageId = UUID.fromString(messageIdStr);
        TodoListTransaction transaction = todoTransactionRepository.findAllByIdAndActivated(messageId,ACTIVATED_MESSAGE);
        CommonResponse commonResponse = new CommonResponse();
        if(transaction != null){
            logger.info("FOUND TRANSACTION");
            TodoListTransaction updateTodoTransaction =  prepareUpdateTodoTransaction(transaction,request,messageId);
            TodoListTransaction saveResult = todoTransactionRepository.save(updateTodoTransaction);
            if(saveResult != null){
                logger.info("UPDATE SUCCESSFULLY");
                commonResponse.setStatus("SUCCESS");
                TodoListInsertResponse todoListInsertResponse =  new TodoListInsertResponse();
                todoListInsertResponse.setMessage(saveResult.getMessage());
                todoListInsertResponse.setMessageId(saveResult.getId().toString());
                commonResponse.setData(todoListInsertResponse);
                commonResponse.setHttpStatus(HttpStatus.OK);
            }else {
                logger.error("UPDATE FAILED");
                commonResponse.setStatus("ERROR");
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setError("Can't update to db");
                commonResponse.setData(errorResponse);
                commonResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            }
//            transaction exists
        }else {
            logger.error("Transaction not found messageId :{}" ,messageIdStr);
            commonResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            commonResponse.setStatus("NOT_FOUND");
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setError("transaction todo list not found");
            commonResponse.setData(errorResponse);
        }
        return commonResponse;
    }


    private TodoListTransaction prepareInsertTodoTransaction(TodoListInsertRequest request) {
        TodoListTransaction transaction = new TodoListTransaction();
        transaction.setId(UUID.randomUUID());
        Date date = Calendar.getInstance().getTime();
        transaction.setCreatedDate(date);
        transaction.setUpdatedDate(date);
        transaction.setActivated("Y");
        transaction.setMessage(request.getMessage());
        return transaction;
    }

    private TodoListTransaction prepareUpdateTodoTransaction(TodoListTransaction oldTransaction,TodoListInsertRequest request, UUID uuid) {
        TodoListTransaction transaction = new TodoListTransaction();
        transaction.setId(uuid);
        transaction.setCreatedDate(oldTransaction.getCreatedDate());
        transaction.setUpdatedDate(Calendar.getInstance().getTime());
        transaction.setActivated(oldTransaction.getActivated());
        transaction.setMessage(request.getMessage());
        return transaction;
    }

}
