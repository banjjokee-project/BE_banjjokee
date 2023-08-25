package com.project.BE_banjjokee.exception;

public class CommentNotFoundException extends BusinessException{

    public CommentNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
