package gm.tareas.service;

import java.util.List;

import gm.tareas.model.Tarea;

public interface ITareaServicio {
    public List<Tarea> listarTareas();
    
    public Tarea buscarTareaPorId(Integer idTarea);

    public void guardarTarea(Tarea tarea);

    public void eliminarTarea(Tarea tarea);
}
