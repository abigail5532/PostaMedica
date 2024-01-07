/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBean;

import Controladores.EspecialidadesJpaController;
import Entidades.Especialidades;
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
public class BeanEspecialidades {
    private int idesp;
    private String nombreesp;
    private String nrooficinaesp;
    private char activeesp;
    
    public BeanEspecialidades() {}
    //Get and Set
    public int getIdesp() {
        return idesp;
    }

    public void setIdesp(int idesp) {
        this.idesp = idesp;
    }

    public String getNombreesp() {
        return nombreesp;
    }

    public void setNombreesp(String nombreesp) {
        this.nombreesp = nombreesp;
    }

    public String getNrooficinaesp() {
        return nrooficinaesp;
    }

    public void setNrooficinaesp(String nrooficinaesp) {
        this.nrooficinaesp = nrooficinaesp;
    }

    public char getActiveesp() {
        return activeesp;
    }

    public void setActiveesp(char activeesp) {
        this.activeesp = activeesp;
    }
    
    //Métodos
    //Método para listar datos
    public List<Especialidades> tablaEspecialidades(){
        try {
            EspecialidadesJpaController ctrl = new EspecialidadesJpaController();
            List<Especialidades> list = ctrl.tablaEspecialidades();
            return list;
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR no se puede listar los datos" + ex); 
        }
        return null;
    }
    
    //Método para agregar datos
    public void agregarEspecialidades(){
        try{
            EspecialidadesJpaController ctrl = new EspecialidadesJpaController();
           ctrl.create(new Especialidades(idesp,nombreesp,nrooficinaesp,'A'));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR no se puede agregar los datos" + ex);  
        }
    }
    
    //Método para editar datos
    public void actualizarEspecialidades(){
        try{
            EspecialidadesJpaController ctrl = new EspecialidadesJpaController();
            ctrl.editEsp(new Especialidades(idesp,nombreesp,nrooficinaesp,'A'));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR no se puede actualizar los datos" + ex);  
        }
    }
    
    //Método para eliminar datos
    public void eliminarEspecialidades(Especialidades especialidades){
        try{
            EspecialidadesJpaController ctrl = new EspecialidadesJpaController();
            ctrl.editEsp(new Especialidades(especialidades.getIdesp(),especialidades.getNombreesp(),especialidades.getNrooficinaesp(),'N'));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR no se puede eliminar los datos" + ex);  
        }
    }
}
