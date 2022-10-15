package com.ates.myfirstshop.common;

import java.time.LocalDateTime;

public class UpdateReponse {

    private final boolean success;
    private final String message;

    private final Object entity;

    public UpdateReponse(boolean success, String message, Object entity){
        this.success = success;
        this.message = message;
        this.entity = entity;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getEntity() { return entity; }

    public String getTimestamp(){
        return LocalDateTime.now().toString();
    }
}
