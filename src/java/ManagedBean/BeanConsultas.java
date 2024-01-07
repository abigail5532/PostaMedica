/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBean;

import Controladores.ConsultasJpaController;
import Entidades.Consultas;
import Entidades.Triaje;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.swing.JOptionPane;

/**
 *
 * @author Alessandra
 */
@ManagedBean
@RequestScoped
public class BeanConsultas {
    private int idcons;
    private Triaje triajecons;
    private String diagnosticocons;
    private String tratamientocons;
    private String estadocons;
    private char activecons;
    //AGREGADO
    private Consultas selectedCons;
    private List<Consultas> consultas;
    
    
    public BeanConsultas() {
        consultas = new ArrayList<>();
        cargarConsultas();
    }
    
    //Get and Set
    public int getIdcons() {
        return idcons;
    }

    public void setIdcons(int idcons) {
        this.idcons = idcons;
    }

    public Triaje getTriajecons() {
        return triajecons;
    }

    public void setTriajecons(Triaje triajecons) {
        this.triajecons = triajecons;
    }

    public String getDiagnosticocons() {
        return diagnosticocons;
    }

    public void setDiagnosticocons(String diagnosticocons) {
        this.diagnosticocons = diagnosticocons;
    }

    public String getTratamientocons() {
        return tratamientocons;
    }

    public void setTratamientocons(String tratamientocons) {
        this.tratamientocons = tratamientocons;
    }

    public String getEstadocons() {
        return estadocons;
    }

    public void setEstadocons(String estadocons) {
        this.estadocons = estadocons;
    }

    public char getActivecons() {
        return activecons;
    }

    public void setActivecons(char activecons) {
        this.activecons = activecons;
    }
    
    //AGREGADO
    public List<Consultas> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consultas> consultas) {
        this.consultas = consultas;
    }

    public Consultas getSelectedCons() {
        return selectedCons;
    }

    public void setSelectedCons(Consultas selectedCons) {
        this.selectedCons = selectedCons;
    }
    
    //Métodos
    //Método para listar datos
    public List<Consultas> tablaConsultas(){
        try{
            ConsultasJpaController ctrl = new ConsultasJpaController();
            List<Consultas> list = ctrl.tablaConsultas();
            return list;
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR no se puede listar los datos" + ex); 
        }
        return null;
    }
    
    //Método para obtener datos
    public void obtenerConsulta (Consultas consultas){
        idcons = consultas.getIdcons();
        triajecons = consultas.getTriajecons();
        diagnosticocons = consultas.getDiagnosticocons();
        tratamientocons = consultas.getTratamientocons();
        estadocons = consultas.getEstadocons();
    }

    // Método para cargar las consultas al inicio
    private void cargarConsultas() {
        try {
            ConsultasJpaController ctrl = new ConsultasJpaController();
            consultas = ctrl.tablaConsultas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR no se puede cargar las consultas" + ex);
        }
    }
    
    //Método para agregar datos
    public void agregarConsultas(){
        try{
            ConsultasJpaController ctrl = new ConsultasJpaController();
            ctrl.create(new Consultas(0,triajecons,diagnosticocons,tratamientocons,estadocons,'A'));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR no se puede agregar los datos" + ex);
        }
    }
}
