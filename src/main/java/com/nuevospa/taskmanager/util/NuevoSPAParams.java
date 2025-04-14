package com.nuevospa.taskmanager.util;

public class NuevoSPAParams {

    // =======================
    // Códigos de Respuesta
    // =======================
    public static final String CODIGO_OK = "200";
    public static final String DESCRIPCION_OK = "Operación realizada con éxito";

    public static final String CODIGO_ERROR = "500";
    public static final String DESCRIPCION_ERROR = "Ocurrió un error inesperado";

    public static final String CODIGO_NO_AUTORIZADO = "401";
    public static final String DESCRIPCION_NO_AUTORIZADO = "Usuario no autorizado";

    public static final String CODIGO_NO_ENCONTRADO = "404";
    public static final String DESCRIPCION_NO_ENCONTRADO = "Recurso no encontrado";

    public static final String CODIGO_CONFLICTO = "409";
    public static final String DESCRIPCION_CONFLICTO = "Conflicto con los datos solicitados";
    
    
    public static final String CODIIGO_ERROR_CREAR_TAREA ="01";
    public static final String DESC_ERROR_CREAR_TAREA = "No se ha podido crear la tarea";
    
    public static final String CODIGO_USUARIO_NO_ENCONTRADO = "02";
    public static final String DESC_USUARIO_NO_ENCONTRADO = "Usuario no conetrado";
    
    public static final String CODIGO_CREDENCIALES_NO_VALIDAS = "03";
    public static final String DESC_CREDENCIALES_NO_VALIDAS = "Credenciales no validas";
    
    public static final String CODIGO_ERROR_GENERACION_TOKEN = "04";
    public static final String DESC_ERROR_GENERACION_TOKEN = "Ha ocurrido un error al generar el token";
    
    public static final String CODIGO_TAREAS_NO_ENCONTRADAS = "05";
    public static final String DESC_TAREAS_NO_ENCONTRADAS = "No se han encontrado tareas";
    
    public static final String CODIGO_TAREA_NO_ENCONTRADA = "06";
    public static final String DESC_TAREA_NO_ENCONTRADA = "Tarea no encontrada";
    
    public static final String CODIIGO_ERROR_ACTUALIZAR_TAREA ="07";
    public static final String DESC_ERROR_ACTUALIZAR_TAREA = "No se ha podido actualizar la tarea";
    
    public static final String CODIIGO_ESTADO_TAREA_NO_VALIDO ="08";
    public static final String DESC_ESTADO_TAREA_NO_VALIDO = "El campo 'Estado Tarea' no es valido";
    

    public static final String ESTADO_TAREA_PENDIENTE = "PE";
    public static final String ESTADO_TAREA_EN_PROCESO = "EP";
    public static final String ESTADO_TAREA_COMPLETADA = "CO";
    public static final String ESTADO_TAREA_ELIMINADA = "EL";
}