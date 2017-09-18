package com.zhuxs.result.Handler;

import com.zhuxs.result.Exception.ResultException;
import com.zhuxs.result.domain.enums.ErrorCode;
import com.zhuxs.result.dto.ErrorDto;
import com.zhuxs.result.utils.ApplicationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by shusesshou on 2017/9/18.
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger("com.zhuxs.result.GlobalExceptionHandler");

    @ExceptionHandler(value = {ResultException.class})
    public ResponseEntity<Object> handleException(ResultException ex, HttpServletRequest request) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(request.getRequestURI()));
        headers.setContentType(MediaType.APPLICATION_JSON);
        logger.error("-----Exception Handler---Host: {} invokes url: {} ERROR: {} Cause:",request.getRemoteHost(),request.getRequestURL(), ex.getMessage(),ex.getCause());
        return handleExceptionInternal(ex,headers,HttpStatus.INTERNAL_SERVER_ERROR,request);
    }

    protected ResponseEntity<Object> handleExceptionInternal(ResultException ex, HttpHeaders headers, HttpStatus status,HttpServletRequest request){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setStatus(status.toString());
        errorDto.setErrorCode(ex.getErrorCode());
        errorDto.setUrl(request.getRequestURL().toString());
        errorDto.setMsg(ex.getMessage());
        return new ResponseEntity(errorDto,headers,status);
    }
}
