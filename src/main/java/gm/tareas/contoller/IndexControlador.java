package gm.tareas.contoller;

import gm.tareas.model.Tarea;
import gm.tareas.model.TareaServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class IndexControlador implements Initializable {
    private static final Logger logger= LoggerFactory.getLogger(IndexControlador.class);
    @Autowired
    private TareaServicio tareaServicio;
    @FXML
    private TableView<Tarea> tbTarea;

    @FXML
    private TableColumn<Tarea, Integer> idColumn;

    @FXML
    private TableColumn<Tarea, String> tareaColumn;

    @FXML
    private TableColumn<Tarea, String> responsableColumn;

    @FXML
    private TableColumn<Tarea, String> estadoColumn;

    private final ObservableList<Tarea> tareaList = FXCollections.observableArrayList();

    @FXML
    private TextField txtTarea;

    @FXML
    private TextField txtResponsable;

    @FXML
    private TextField txtEstado;

    private Integer idTareaInterno;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbTarea.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        listarTareas();
    }

    private void configurarColumnas() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tareaColumn.setCellValueFactory(new PropertyValueFactory<>("nombreTarea"));
        responsableColumn.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estatus"));
    }

    private void listarTareas() {
        tareaList.clear();
        tareaList.addAll(tareaServicio.listarTareas());
        tbTarea.setItems(tareaList);
    }

    public void agregarTarea() {
        if(txtTarea.getText().isEmpty()){
            mostrarMensaje("Error Validacion","Debe proporcionar una Tarea");
            txtTarea.requestFocus();
            return;
        }else{
            var tarea = new Tarea();
            recolectarDatosFormulario(tarea);
            tareaServicio.guardarTarea(tarea);
            mostrarMensaje("Informacion","Tarea guardada con exito");
            limpiarFormulario();
            listarTareas();
        }
    }

    public void eliminarTarea(){
        var tarea = tbTarea.getSelectionModel().getSelectedItem();
        if(tarea != null){
            logger.info("Registro a Eliminar: "+tarea.getNombreTarea());
            tareaServicio.eliminarTarea(tarea);
            mostrarMensaje("Informacion","Tarea eliminada: "+tarea.getNombreTarea());
            limpiarFormulario();
            listarTareas();
        }else {
            mostrarMensaje("Error Validacion","Debe seleccionar una Tarea");
        }
    }

    public void cargarDatosFormulario(){
        var tarea = tbTarea.getSelectionModel().getSelectedItem();
        if(tarea != null){
            idTareaInterno = tarea.getId();
            txtTarea.setText(tarea.getNombreTarea());
            txtResponsable.setText(tarea.getResponsable());
            txtEstado.setText(tarea.getEstatus());
        }
    }

    public void modificarTarea(){
        if(idTareaInterno == null){
            mostrarMensaje("Informacion","Seleccione una Tarea");
            return;
        }
        if(txtTarea.getText().isEmpty()){
            mostrarMensaje("Error Validacion","Debe proporcionar una Tarea");
            txtTarea.requestFocus();
            return;
        }
        var tarea = new Tarea();
        recolectarDatosFormulario(tarea);
        tareaServicio.guardarTarea(tarea);
        mostrarMensaje("Informacion","Tarea actualizada con exito");
        limpiarFormulario();
        listarTareas();
    }

    public void limpiarFormulario() {
        idTareaInterno = null;
        txtTarea.clear();
        txtResponsable.clear();
        txtEstado.clear();
    }

    private void recolectarDatosFormulario(Tarea tarea) {
        tarea.setId(idTareaInterno);
        tarea.setNombreTarea(txtTarea.getText());
        tarea.setResponsable(txtResponsable.getText());
        tarea.setEstatus(txtEstado.getText());
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
