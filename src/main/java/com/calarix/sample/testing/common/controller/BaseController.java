package com.calarix.pipa.common.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

    protected ResponseEntity<Response> buildResponseEntityOK(Object data){
        Response response = new Response();
        response.data = data;
        return new ResponseEntity(response, HttpStatus.OK);
    }

    protected ResponseEntity<Response> buildResponseEntityError(String code, String msg){
        Response response = new Response();
        response.code = code;
        response.msg = msg;
        response.error = true;
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
