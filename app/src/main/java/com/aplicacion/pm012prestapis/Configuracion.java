package com.aplicacion.pm012prestapis;

public class Configuracion
{
    private static final String SHttp = "http://";
    private static final String ipaddres = "192.168.0.7/"; //FQDN
    private static final String WebApi = "PM01WEB/";
    private static final String GetListEmple = "Listempleados.php";
    private static final String PostCreateEmple = "CreateEmpleado.php";

    public static final String EndpointListEmple = SHttp + ipaddres + WebApi + GetListEmple;
    public static final String EndpointCreateEmple = SHttp + ipaddres + WebApi + PostCreateEmple;

    /*Campos tabla empleados*/
    public static final String FieldEdad = "edad";
}
