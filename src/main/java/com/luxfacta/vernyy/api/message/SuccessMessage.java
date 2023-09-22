package com.luxfacta.vernyy.api.message;

public class SuccessMessage implements IBaseMessage {

    private String message;
    private String id;

    public SuccessMessage(String message, String id) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    
    public String getStatus() {
        return  "OK";
    }

    public boolean getError() {
        return false;
    }

    
    

}
