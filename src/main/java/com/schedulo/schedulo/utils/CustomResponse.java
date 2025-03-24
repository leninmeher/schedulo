package com.schedulo.schedulo.utils;

public class CustomResponse {

    private int statusCode;
    private String message;
    private Object data;

    // Constructors
    public CustomResponse() {
    }

    public CustomResponse(int statusCode, Object data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public CustomResponse(int statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    // Optional: Override toString() for debugging
    @Override
    public String toString() {
        return "CustomResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
