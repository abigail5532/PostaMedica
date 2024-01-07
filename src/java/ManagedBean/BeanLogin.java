/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBean;

import Controladores.UsuariosJpaController;
import Entidades.Usuarios;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

/**
 *
 * @author Alessandra
 */
@ManagedBean
@SessionScoped
public class BeanLogin {
    private int iduser;
    private String dniuser;
    private String nombresuser;
    private String claveuser;
    private String roluser;
    private String fecreguser;
    private char activeuser;
    
    public BeanLogin() {}
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
    //Método para validar datos del login
    public String validarlogin(){
        try{
            UsuariosJpaController ctrl = new UsuariosJpaController();
            Usuarios u = ctrl.validarUsuarios(dniuser, claveuser);
            if(u!=null){
                nombresuser = u.getNombresuser();
                roluser = u.getRoluser();
                if ("Administrador".equals(roluser)) {
                    return "Pacientes";
                } else if ("Medico".equals(roluser)){
                    return "TriajeMedico";
                }
            }else{
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", "Ingresa correctamente las credenciales, por favor verificar"));
            }
        }catch(Exception ex){
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", "Ingresa correctamente las credenciales, por favor verificar"));
        }return null;
    }
}
