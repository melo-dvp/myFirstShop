package com.ates.myfirstshop.exceptions;

public class AuthentificationFailException extends IllegalArgumentException{

    public AuthentificationFailException(String msg){
        super(msg);
    }
}
