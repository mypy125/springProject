package com.development.task.domain.exception;

public class ImageUploadException extends RuntimeException{
    public ImageUploadException(final  String massage){
        super(massage);
    }
}
