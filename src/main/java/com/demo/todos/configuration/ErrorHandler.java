package com.demo.todos.configuration;


import com.demo.todos.model.response.CommonResponse;
import com.demo.todos.model.response.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {
    private static final Logger logger = LogManager.getLogger(ErrorHandler.class);



    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handlerAllError(Exception e){
        logger.error("UNEXPECTED ERROR {}", e);
        CommonResponse commonResponse = new CommonResponse();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("INTERNAL SERVER ERROR");
        commonResponse.setData(errorResponse);
        commonResponse.setStatus("INTERNAL_SERVER_ERROR");
        commonResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(commonResponse,commonResponse.getHttpStatus());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse> handlerValidate(MethodArgumentNotValidException e){
        logger.error("VALIDATION FAILED, {}", e);
        CommonResponse commonResponse = new CommonResponse();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(e.getFieldError().getDefaultMessage());
        commonResponse.setData(errorResponse);
        commonResponse.setStatus("BAD_REQUEST");
        commonResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(commonResponse,commonResponse.getHttpStatus());
    }

}
