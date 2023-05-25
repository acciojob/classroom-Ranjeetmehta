package com.driver;

public class StudentNameInvalidException extends RuntimeException{
    public StudentNameInvalidException(String msg){
        super(msg);
    }
}
