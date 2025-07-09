package com.ml.GestionDeTareas.services;

import com.ml.GestionDeTareas.dao.Tarea;

import java.util.List;

public interface TareaService {
    public List<Tarea> listaTodasLasTareas();
    public Tarea crearTarea(Tarea tarea);
    public Tarea actualizarTarea(int id, Tarea tarea);
    public void eliminarTarea(int id);
}
