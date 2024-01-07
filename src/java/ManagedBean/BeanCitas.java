/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBean;

import Controladores.CitasJpaController;
import Entidades.Citas;
import Entidades.Medicos;
import Entidades.Pacientes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.swing.JOptionPane;

/**
 *
 * @author Alessandra
 */
@ManagedBean
@RequestScoped
public class BeanCitas {
    private int idcita;
    private Pacientes pacientecita;
    private Medicos especialidadcita;
    private String fechacita;
    private String horacita;
    private char activecita;
    
    public BeanCitas() {}
    //Get and Set

    public int getIdcita() {
        return idcita;
    }

    public void setIdcita(int idcita) {
        this.idcita = idcita;
    }

    public Pacientes getPacientecita() {
        return pacientecita;
    }

    public void setPacientecita(Pacientes pacientecita) {
        this.pacientecita = pacientecita;
    }

    public Medicos getEspecialidadcita() {
        return especialidadcita;
    }

    public void setEspecialidadcita(Medicos especialidadcita) {
        this.especialidadcita = especialidadcita;
    }

    public String getFechacita() {
        return fechacita;
    }

    public void setFechacita(String fechacita) {
        this.fechacita = fechacita;
    }

    public String getHoracita() {
        return horacita;
    }

    public void setHoracita(String horacita) {
        this.horacita = horacita;
    }

    public char getActivecita() {
        return activecita;
    }

    public void setActivecita(char activecita) {
        this.activecita = activecita;
    }
    
    //Métodos
    //Método para listar datos
    public List<Citas> tablaCitas(){
        try {
            CitasJpaController ctrl = new CitasJpaController();
            List<Citas> list = ctrl.tablaCitas();
            return list;
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR no se puede listar los datos" + ex); 
        }
        return null;
    }
    
    
    //Método para listar datos para las citas no realizadas
    public List<Citas> tablaCitasNoRealizadas(){
        try {
            CitasJpaController ctrl = new CitasJpaController();
            List<Citas> list = ctrl.tablaCitasNoRealizadas();
            return list;
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR no se puede listar los datos" + ex); 
        }
        return null;
    }
    
    //Método para agregar datos
    public void agregarCitas(){
        try{
            CitasJpaController ctrl = new CitasJpaController();
            ctrl.create(new Citas(idcita,pacientecita,especialidadcita,fechacita,horacita,'A'));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR no se puede agregar los datos" + ex);
        }
    }
    
    //Método para editar datos
    public void actualizarCitas(){
        try{
            CitasJpaController ctrl = new CitasJpaController();
            ctrl.editCita(new Citas(idcita,pacientecita,especialidadcita,fechacita,horacita,'A'));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR no se puede agregar los datos" + ex);
        }
    }
    
    //Método para eliminar datos
    public void eliminarCitas(Citas citas){
        try{
            CitasJpaController ctrl = new CitasJpaController();
            ctrl.editCita(new Citas(citas.getIdcita(),citas.getPacientecita(),citas.getEspecialidadcita(),
                    citas.getFechacita(),citas.getHoracita(),'N'));
        }catch(Exception ex){
          JOptionPane.showMessageDialog(null, "ERROR no se puede eliminar los datos" + ex); 
        } 
    }
    
    //Método para obtener datos
    public void obtenerCitas (Citas citas){
        idcita = citas.getIdcita();
        pacientecita = citas.getPacientecita();
        especialidadcita = citas.getEspecialidadcita();
        fechacita = citas.getFechacita();
        horacita = citas.getHoracita();
    }
    
    //Método para cita realizada
    public void realizadasCitas(Citas citas){
        try{
            CitasJpaController ctrl = new CitasJpaController();
            ctrl.editCita(new Citas(citas.getIdcita(),citas.getPacientecita(),citas.getEspecialidadcita(),
                    citas.getFechacita(),citas.getHoracita(),'R'));
        }catch(Exception ex){
          JOptionPane.showMessageDialog(null, "ERROR no se puede registrar cita realizada" + ex); 
        } 
    }
    
    //Método para solicitar la fecha al sistema
    @PostConstruct
    public void init() {
        // Obtener la fecha actual del sistema y asignarla al atributo fechaingpac
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        fechacita = dateFormat.format(new Date());
    } 
}
