package com.example.studentmanagementsystem.exception;

public class DBOperationException extends RuntimeException{
    public DBOperationException(String message){
        super(message);
    }
}
