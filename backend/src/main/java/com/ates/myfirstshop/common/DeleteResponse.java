package com.ates.myfirstshop.common;

import java.time.LocalDateTime;

public class DeleteResponse {

    private final boolean success;
    private final String message;

    private final Integer id;

    public DeleteResponse(boolean success, String message, int id){
        this.success = success;
        this.message = message;
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getId() { return id; }

    public String getTimestamp(){
        return LocalDateTime.now().toString();
    }
}
