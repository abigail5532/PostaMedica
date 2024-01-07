/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBean;

import Controladores.CitasJpaController;
import Controladores.TriajeJpaController;
import Entidades.Citas;
import Entidades.Triaje;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.swing.JOptionPane;

/**
 *
 * @author Alessandra
 */
@ManagedBean
@SessionScoped
public class BeanTriaje {
    private int idtri;
    private Citas citatri;
    private String pesotri;
    private String estaturatri;
    private String temperaturatri;
    private String presiontri;
    private int pulsotri;
    private int respiraciontri;
    private char activetri;
    
    public BeanTriaje() {}
    //Get and Set
    public int getIdtri() {
        return idtri;
    }

    public void setIdtri(int idtri) {
        this.idtri = idtri;
    }

    public Citas getCitatri() {
        return citatri;
    }

    public void setCitatri(Citas citatri) {
        this.citatri = citatri;
    }

    public String getPesotri() {
        return pesotri;
    }

    public void setPesotri(String pesotri) {
        this.pesotri = pesotri;
    }

    public String getEstaturatri() {
        return estaturatri;
    }

    public void setEstaturatri(String estaturatri) {
        this.estaturatri = estaturatri;
    }

    public String getTemperaturatri() {
        return temperaturatri;
    }

    public void setTemperaturatri(String temperaturatri) {
        this.temperaturatri = temperaturatri;
    }

    public String getPresiontri() {
        return presiontri;
    }

    public void setPresiontri(String presiontri) {
        this.presiontri = presiontri;
    }

    public int getPulsotri() {
        return pulsotri;
    }

    public void setPulsotri(int pulsotri) {
        this.pulsotri = pulsotri;
    }

    public int getRespiraciontri() {
        return respiraciontri;
    }

    public void setRespiraciontri(int respiraciontri) {
        this.respiraciontri = respiraciontri;
    }

    public char getActivetri() {
        return activetri;
    }

    public void setActivetri(char activetri) {
        this.activetri = activetri;
    }
    
    //Métodos
    //Método para listar datos
    public List<Triaje> tablaTriaje(){
        try {
            TriajeJpaController ctrl = new TriajeJpaController();
            List<Triaje> list = ctrl.tablaTriaje();
            return list;
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR no se puede listar los datos" + ex); 
        }
        return null;
    }
    
    //Método para agregar datos
    public void agregarTriaje(){
        try{
            TriajeJpaController ctrl = new TriajeJpaController();
            ctrl.create(new Triaje(0,citatri,pesotri,estaturatri,temperaturatri,presiontri,pulsotri,respiraciontri,'A'));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR no se puede agregar los datos" + ex);
        }
    }
    
    //Método para obtener datos
    public void obtenerTriaje (Triaje triaje){
        idtri = triaje.getIdtri();
        citatri = triaje.getCitatri();
        pesotri = triaje.getPesotri();
        estaturatri = triaje.getEstaturatri();
        temperaturatri = triaje.getTemperaturatri();
        presiontri = triaje.getPresiontri();
        pulsotri = triaje.getPulsotri();
        respiraciontri = triaje.getRespiraciontri();
    }
    
    //Método para eliminar datos
    public void triajesRealizados(Triaje triaje){
        try{
            TriajeJpaController ctrl = new TriajeJpaController();
            ctrl.editTri(new Triaje(triaje.getIdtri(),triaje.getCitatri(),triaje.getPesotri(),triaje.getEstaturatri(),triaje.getTemperaturatri(),
                    triaje.getPresiontri(),triaje.getPulsotri(),triaje.getRespiraciontri(),'N'));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR no se puede eliminar los datos" + ex);   
        }
    }
}
