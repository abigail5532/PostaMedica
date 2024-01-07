/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBean;

import Controladores.PacientesJpaController;
import Entidades.Historias;
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
public class BeanPacientes {
    private int idpac;
    private String dnipac;
    private String apellidospac;
    private String nombrespac;
    private String fechanacpac;
    private String telefonopac;
    private String sexopac;
    private String domiciliopac;
    private String seguropac;
    private String tiposangrepac;
    private String alergiaspac;
    private Historias historiapac;
    private String fecregpac;
    private char activepac;
    
    public BeanPacientes() {}
    //Get and Set
    public int getIdpac() {
        return idpac;
    }

    public void setIdpac(int idpac) {
        this.idpac = idpac;
    }

    public String getDnipac() {
        return dnipac;
    }

    public void setDnipac(String dnipac) {
        this.dnipac = dnipac;
    }

    public String getApellidospac() {
        return apellidospac;
    }

    public void setApellidospac(String apellidospac) {
        this.apellidospac = apellidospac;
    }

    public String getNombrespac() {
        return nombrespac;
    }

    public void setNombrespac(String nombrespac) {
        this.nombrespac = nombrespac;
    }

    public String getFechanacpac() {
        return fechanacpac;
    }

    public void setFechanacpac(String fechanacpac) {
        this.fechanacpac = fechanacpac;
    }

    public String getTelefonopac() {
        return telefonopac;
    }

    public void setTelefonopac(String telefonopac) {
        this.telefonopac = telefonopac;
    }

    public String getSexopac() {
        return sexopac;
    }

    public void setSexopac(String sexopac) {
        this.sexopac = sexopac;
    }

    public String getDomiciliopac() {
        return domiciliopac;
    }

    public void setDomiciliopac(String domiciliopac) {
        this.domiciliopac = domiciliopac;
    }

    public String getSeguropac() {
        return seguropac;
    }

    public void setSeguropac(String seguropac) {
        this.seguropac = seguropac;
    }

    public String getTiposangrepac() {
        return tiposangrepac;
    }

    public void setTiposangrepac(String tiposangrepac) {
        this.tiposangrepac = tiposangrepac;
    }

    public String getAlergiaspac() {
        return alergiaspac;
    }

    public void setAlergiaspac(String alergiaspac) {
        this.alergiaspac = alergiaspac;
    }

    public Historias getHistoriapac() {
        return historiapac;
    }

    public void setHistoriapac(Historias historiapac) {
        this.historiapac = historiapac;
    }

    public String getFecregpac() {
        return fecregpac;
    }

    public void setFecregpac(String fecregpac) {
        this.fecregpac = fecregpac;
    }

    public char getActivepac() {
        return activepac;
    }

    public void setActivepac(char activepac) {
        this.activepac = activepac;
    }
    
    //Métodos
    //Método para listar datos
    public List<Pacientes> tablaPacientes(){
        try {
            PacientesJpaController ctrl = new PacientesJpaController();
            List<Pacientes> list = ctrl.tablaPacientes();
            return list;
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR no se puede listar los datos" + ex); 
        }
        return null;
    }
    
    //Método para agregar datos
    public void agregarPacientes(){
        try{
           PacientesJpaController ctrl = new PacientesJpaController();
           ctrl.create(new Pacientes (idpac,dnipac,apellidospac,nombrespac,fechanacpac,telefonopac,sexopac,domiciliopac,seguropac,tiposangrepac,alergiaspac,historiapac,fecregpac,'A'));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR no se puede agregar los datos" + ex);
        }
    }
    
    //Método para editar datos
    public void actualizarPacientes(){
        try{
           PacientesJpaController ctrl = new PacientesJpaController();
           ctrl.editPac(new Pacientes (idpac,dnipac,apellidospac,nombrespac,fechanacpac,telefonopac,sexopac,domiciliopac,seguropac,tiposangrepac,alergiaspac,historiapac,fecregpac,'A'));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR no se puede agregar los datos" + ex);
        }
    }
    
    //Método para eliminar datos
    public void eliminarPacientes(Pacientes pacientes){
        try{
            PacientesJpaController ctrl = new PacientesJpaController();
            ctrl.editPac(new Pacientes(pacientes.getIdpac(),pacientes.getDnipac(),
                    pacientes.getApellidospac(),pacientes.getNombrespac(),
                    pacientes.getFechanacpac(),pacientes.getTelefonopac(),
                    pacientes.getSexopac(),pacientes.getDomiciliopac(),
                    pacientes.getSeguropac(),pacientes.getTiposangrepac(),
                    pacientes.getAlergiaspac(),pacientes.getHistoriapac(),pacientes.getFecregpac(),'N'));
        }catch(Exception ex){
          JOptionPane.showMessageDialog(null, "ERROR no se puede eliminar los datos" + ex); 
        } 
    }
    
    //Método para solicitar la fecha al sistema
    @PostConstruct
    public void init() {
        // Obtener la fecha actual del sistema y asignarla al atributo fechaingpac
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        fecregpac = dateFormat.format(new Date());
    }
}
