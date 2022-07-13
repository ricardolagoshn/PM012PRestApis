package com.aplicacion.pm012prestapis;

public class Message
{
    public String codigo;
    public String message;

    public Message(String codigo, String message) {
        this.codigo = codigo;
        this.message = message;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

