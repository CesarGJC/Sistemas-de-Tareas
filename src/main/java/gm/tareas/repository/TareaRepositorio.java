package gm.tareas.repository;

import gm.tareas.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepositorio extends JpaRepository<Tarea,Integer> {
    
}
