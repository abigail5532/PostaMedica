/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBean;

import Controladores.MedicosJpaController;
import Entidades.Especialidades;
import Entidades.Medicos;
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
public class BeanMedicos {
    private int idmed;
    private String nombresmed;
    private String apellidosmed;
    private String dnimed;
    private Especialidades especialidadmed;
    private String cmpmed;
    private String telefonomed;
    private String emailmed;
    private String domiciliomed;
    private String fecregmed;
    private char activemed;
    
    public BeanMedicos() {}
    //Get and Set
    public int getIdmed() {
        return idmed;
    }

    public void setIdmed(int idmed) {
        this.idmed = idmed;
    }

    public String getNombresmed() {
        return nombresmed;
    }

    public void setNombresmed(String nombresmed) {
        this.nombresmed = nombresmed;
    }

    public String getApellidosmed() {
        return apellidosmed;
    }

    public void setApellidosmed(String apellidosmed) {
        this.apellidosmed = apellidosmed;
    }

    public String getDnimed() {
        return dnimed;
    }

    public void setDnimed(String dnimed) {
        this.dnimed = dnimed;
    }

    public Especialidades getEspecialidadmed() {
        return especialidadmed;
    }

    public void setEspecialidadmed(Especialidades especialidadmed) {
        this.especialidadmed = especialidadmed;
    }

    public String getCmpmed() {
        return cmpmed;
    }

    public void setCmpmed(String cmpmed) {
        this.cmpmed = cmpmed;
    }

    public String getTelefonomed() {
        return telefonomed;
    }

    public void setTelefonomed(String telefonomed) {
        this.telefonomed = telefonomed;
    }

    public String getEmailmed() {
        return emailmed;
    }

    public void setEmailmed(String emailmed) {
        this.emailmed = emailmed;
    }

    public String getDomiciliomed() {
        return domiciliomed;
    }

    public void setDomiciliomed(String domiciliomed) {
        this.domiciliomed = domiciliomed;
    }

    public String getFecregmed() {
        return fecregmed;
    }

    public void setFecregmed(String fecregmed) {
        this.fecregmed = fecregmed;
    }

    public char getActivemed() {
        return activemed;
    }

    public void setActivemed(char activemed) {
        this.activemed = activemed;
    }
    
    //Métodos
    //Método para listar datos
    public List<Medicos> tablaMedicos(){
        try{
            MedicosJpaController ctrl = new MedicosJpaController();
            List<Medicos> list = ctrl.tablaMedicos();
            return list;
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR no se puede listar los datos" + ex); 
        }
        return null;
    }
    
    //Método para agregar datos
    public void agregarMedicos(){
        try{
           MedicosJpaController ctrl = new MedicosJpaController();
           ctrl.create(new Medicos(idmed,nombresmed,apellidosmed,especialidadmed,dnimed,cmpmed,telefonomed,emailmed,domiciliomed,fecregmed,'A'));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR no se puede agregar los datos" + ex);  
        }
    }
    
    //Método para editar datos
    public void actualizarMedicos(){
        try{
            MedicosJpaController ctrl = new MedicosJpaController();
            ctrl.editMed(new Medicos(idmed,nombresmed,apellidosmed,especialidadmed,dnimed,cmpmed,telefonomed,emailmed,domiciliomed,fecregmed,'A'));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR no se puede actualizar los datos" + ex);  
        }
    }
    
    //Método para eliminar datos
    public void eliminarMedicos(Medicos medicos){
        try{
           MedicosJpaController ctrl = new MedicosJpaController();
           ctrl.editMed(new Medicos(medicos.getIdmed(),medicos.getNombresmed(),medicos.getApellidosmed(),medicos.getEspecialidadmed(),
                    medicos.getDnimed(),medicos.getCmpmed(),medicos.getTelefonomed(),medicos.getEmailmed(),medicos.getDomiciliomed(),medicos.getFecregmed(),'N'));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR no se puede eliminar los datos" + ex);   
        }
    }
    
    //Método para solicitar la fecha al sistema
    @PostConstruct
    public void init() {
        // Obtener la fecha actual del sistema y asignarla al atributo fechaingpac
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        fecregmed = dateFormat.format(new Date());
    }
}
