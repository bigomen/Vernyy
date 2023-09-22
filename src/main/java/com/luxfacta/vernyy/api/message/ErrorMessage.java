package com.luxfacta.vernyy.api.message;

public class ErrorMessage  implements IBaseMessage {

    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return "ERRO";
    }

    public boolean getError() {
        return true;
    }

    public String getId() {
        return null;
    }
    

}
