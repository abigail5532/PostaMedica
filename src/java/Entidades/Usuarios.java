/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alessandra
 */
@Entity
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByIduser", query = "SELECT u FROM Usuarios u WHERE u.iduser = :iduser"),
    @NamedQuery(name = "Usuarios.findByDniuser", query = "SELECT u FROM Usuarios u WHERE u.dniuser = :dniuser"),
    @NamedQuery(name = "Usuarios.findByNombresuser", query = "SELECT u FROM Usuarios u WHERE u.nombresuser = :nombresuser"),
    @NamedQuery(name = "Usuarios.findByClaveuser", query = "SELECT u FROM Usuarios u WHERE u.claveuser = :claveuser"),
    @NamedQuery(name = "Usuarios.findByRoluser", query = "SELECT u FROM Usuarios u WHERE u.roluser = :roluser"),
    @NamedQuery(name = "Usuarios.findByFecreguser", query = "SELECT u FROM Usuarios u WHERE u.fecreguser = :fecreguser"),
    @NamedQuery(name = "Usuarios.findByActiveuser", query = "SELECT u FROM Usuarios u WHERE u.activeuser = :activeuser")})
public class Usuarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "iduser")
    private Integer iduser;
    @Basic(optional = false)
    @Column(name = "dniuser")
    private String dniuser;
    @Basic(optional = false)
    @Column(name = "nombresuser")
    private String nombresuser;
    @Basic(optional = false)
    @Column(name = "claveuser")
    private String claveuser;
    @Basic(optional = false)
    @Column(name = "roluser")
    private String roluser;
    @Basic(optional = false)
    @Column(name = "fecreguser")
    private String fecreguser;
    @Column(name = "activeuser")
    private Character activeuser;

    public Usuarios() {
    }

    public Usuarios(Integer iduser) {
        this.iduser = iduser;
    }

    public Usuarios(Integer iduser, String dniuser, String nombresuser, String claveuser, String roluser, String fecreguser, char activeuser) {
        this.iduser = iduser;
        this.dniuser = dniuser;
        this.nombresuser = nombresuser;
        this.claveuser = claveuser;
        this.roluser = roluser;
        this.fecreguser = fecreguser;
        this.activeuser = activeuser;
    }

    public Integer getIduser() {
        return iduser;
    }

    public void setIduser(Integer iduser) {
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

    public Character getActiveuser() {
        return activeuser;
    }

    public void setActiveuser(Character activeuser) {
        this.activeuser = activeuser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iduser != null ? iduser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.iduser == null && other.iduser != null) || (this.iduser != null && !this.iduser.equals(other.iduser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Usuarios[ iduser=" + iduser + " ]";
    }
    
}
