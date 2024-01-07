/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBean;

import Controladores.UsuariosJpaController;
import Entidades.Usuarios;
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
public class BeanUsuarios {
    private int iduser;
    private String dniuser;
    private String nombresuser;
    private String claveuser;
    private String roluser;
    private String fecreguser;
    private char activeuser;
    
    public BeanUsuarios() {}
    //Get and Set
    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getDniuser() {
        return dniuser;
    }

    public void setDniuser(String dniuser) {
        this.dniuser = dniuser;
    }

    public String getNombresuser() {
        return nombresuser;
    }

    public void setNombresuser(String nombresuser) {
        this.nombresuser = nombresuser;
    }

    public String getClaveuser() {
        return claveuser;
    }

    public void setClaveuser(String claveuser) {
        this.claveuser = claveuser;
    }

    public String getRoluser() {
        return roluser;
    }

    public void setRoluser(String roluser) {
        this.roluser = roluser;
    }

    public String getFecreguser() {
        return fecreguser;
    }

    public void setFecreguser(String fecreguser) {
        this.fecreguser = fecreguser;
    }

    public char getActiveuser() {
        return activeuser;
    }

    public void setActiveuser(char activeuser) {
        this.activeuser = activeuser;
    }
    
    //Métodos
    //Método para listar datos
    public List<Usuarios> tablaUsuarios(){
        try {
            UsuariosJpaController ctrl = new UsuariosJpaController();
            List<Usuarios> list = ctrl.tablaUsuarios();
            return list;
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR no se puede listar los datos" + ex);
        }
        return null;
    }
    
    //Método para agregar datos
    public void agregarUsuarios(){
        try{
           UsuariosJpaController ctrl = new UsuariosJpaController();
           ctrl.create(new Usuarios (iduser,dniuser,nombresuser,claveuser,roluser,fecreguser,'A'));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR no se puede agregar los datos" + ex);
        }
    }
    
    //Método para editar datos
    public void actualizarUsuarios(){
        try{
           UsuariosJpaController ctrl = new UsuariosJpaController();
           ctrl.editUser(new Usuarios (iduser,dniuser,nombresuser,claveuser,roluser,fecreguser,'A'));
        }catch(Exception ex){
          JOptionPane.showMessageDialog(null, "ERROR no se puede actualizar los datos" + ex);  
        }
    }
    
    //Método para eliminar datos
    public void eliminar(Usuarios usuarios){
        try{
           UsuariosJpaController ctrl = new UsuariosJpaController();
           ctrl.editUser(new Usuarios(usuarios.getIduser(),usuarios.getDniuser(),usuarios.getNombresuser(),usuarios.getClaveuser(),usuarios.getRoluser(),usuarios.getFecreguser(),'N'));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR no se puede eliminar los datos" + ex);
            
        }
    }
    
    //Método para solicitar la fecha al sistema
    @PostConstruct
    public void init() {
        // Obtener la fecha actual del sistema y asignarla al atributo fechaingpac
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        fecreguser = dateFormat.format(new Date());
    }
}
