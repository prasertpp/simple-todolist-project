package com.demo.todos.service;

import com.demo.todos.model.entity.TodoListTransaction;
import com.demo.todos.model.request.TodoListInsertRequest;
import com.demo.todos.model.response.CommonResponse;
import com.demo.todos.model.response.ErrorResponse;
import com.demo.todos.model.response.TodoListInsertResponse;
import com.demo.todos.repository.TodoTransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static com.demo.todos.constant.CommonConstant.ACTIVATED_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class TodoListServiceTest {

    @InjectMocks
    private TodoListService todoListService;

    @Mock
    private TodoTransactionRepository todoTransactionRepository;

    @Test
    public void success_insert(){

        TodoListTransaction transaction = new TodoListTransaction();
        UUID resultUUID = UUID.randomUUID();
        transaction.setId(resultUUID);
        transaction.setActivated(ACTIVATED_MESSAGE);
        transaction.setMessage("mock message");
        Date date = Calendar.getInstance().getTime();
        transaction.setUpdatedDate(date);
        transaction.setCreatedDate(date);

        Mockito.when(todoTransactionRepository.save(any())).thenReturn(transaction);


        TodoListInsertRequest request = new TodoListInsertRequest();
        request.setMessage("aaaa");
        CommonResponse commonResponse = todoListService.insertTodoTransaction(request);
        TodoListInsertResponse todoListInsertResponse = (TodoListInsertResponse) commonResponse.getData();

        assertEquals("mock message",todoListInsertResponse.getMessage());
        assertEquals(resultUUID.toString(),todoListInsertResponse.getMessageId());
    }

    @Test
    public void fail_insert(){

        TodoListInsertRequest request = new TodoListInsertRequest();
        request.setMessage("aaaa");
        CommonResponse commonResponse = todoListService.insertTodoTransaction(request);
        ErrorResponse errorResponse = (ErrorResponse) commonResponse.getData();

        assertEquals("ERROR",commonResponse.getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,commonResponse.getHttpStatus());
        assertEquals("Can't insert to db",errorResponse.getError());
    }
}

