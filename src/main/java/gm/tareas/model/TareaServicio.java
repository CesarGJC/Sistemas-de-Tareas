package gm.tareas.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gm.tareas.repository.TareaRepositorio;
import gm.tareas.service.ITareaServicio;

@Service
public class TareaServicio implements ITareaServicio{
    @Autowired
    private TareaRepositorio tareaRepositorio;
    
    @Override
    public List<Tarea> listarTareas() {
        return tareaRepositorio.findAll();
    }

    @Override
    public Tarea buscarTareaPorId(Integer idTarea) {
        Tarea tarea = tareaRepositorio.findById(idTarea).orElse(null);
        return tarea;
    }

    @Override
    public void guardarTarea(Tarea tarea) {
        tareaRepositorio.save(tarea);
    }

    @Override
    public void eliminarTarea(Tarea tarea) {
        tareaRepositorio.delete(tarea);
    }
    
}
