package com.aplicacion.pm012prestapis;

public class Empleado
{
    public int id;
    public String nombres;
    public String apellidos;
    public int edad;
    public String telefono;
    public String direccion;
    public String cedula;


    public Empleado(int id, String nombres, String apellidos, int edad, String telefono, String direccion, String cedula) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.edad = edad;
        this.telefono = telefono;
        this.direccion = direccion;
        this.cedula = cedula;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
}