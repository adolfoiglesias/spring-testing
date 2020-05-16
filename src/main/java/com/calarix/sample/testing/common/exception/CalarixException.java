package com.calarix.sample.testing.common.exception;

import lombok.Data;

@Data
public class CalarixException extends RuntimeException {

    public final static String CUSTOMER_ALREADY_EXIST = "Email already registered";

    public String code;

    public CalarixException(String code, String msg ) {
        super(msg);
        this.code = code;
    }
}
