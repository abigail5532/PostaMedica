/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alessandra
 */
@Entity
@Table(name = "pacientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pacientes.findAll", query = "SELECT p FROM Pacientes p"),
    @NamedQuery(name = "Pacientes.findByIdpac", query = "SELECT p FROM Pacientes p WHERE p.idpac = :idpac"),
    @NamedQuery(name = "Pacientes.findByDnipac", query = "SELECT p FROM Pacientes p WHERE p.dnipac = :dnipac"),
    @NamedQuery(name = "Pacientes.findByApellidospac", query = "SELECT p FROM Pacientes p WHERE p.apellidospac = :apellidospac"),
    @NamedQuery(name = "Pacientes.findByNombrespac", query = "SELECT p FROM Pacientes p WHERE p.nombrespac = :nombrespac"),
    @NamedQuery(name = "Pacientes.findByFechanacpac", query = "SELECT p FROM Pacientes p WHERE p.fechanacpac = :fechanacpac"),
    @NamedQuery(name = "Pacientes.findByTelefonopac", query = "SELECT p FROM Pacientes p WHERE p.telefonopac = :telefonopac"),
    @NamedQuery(name = "Pacientes.findBySexopac", query = "SELECT p FROM Pacientes p WHERE p.sexopac = :sexopac"),
    @NamedQuery(name = "Pacientes.findByDomiciliopac", query = "SELECT p FROM Pacientes p WHERE p.domiciliopac = :domiciliopac"),
    @NamedQuery(name = "Pacientes.findBySeguropac", query = "SELECT p FROM Pacientes p WHERE p.seguropac = :seguropac"),
    @NamedQuery(name = "Pacientes.findByTiposangrepac", query = "SELECT p FROM Pacientes p WHERE p.tiposangrepac = :tiposangrepac"),
    @NamedQuery(name = "Pacientes.findByAlergiaspac", query = "SELECT p FROM Pacientes p WHERE p.alergiaspac = :alergiaspac"),
    @NamedQuery(name = "Pacientes.findByFecregpac", query = "SELECT p FROM Pacientes p WHERE p.fecregpac = :fecregpac"),
    @NamedQuery(name = "Pacientes.findByActivepac", query = "SELECT p FROM Pacientes p WHERE p.activepac = :activepac")})
public class Pacientes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idpac")
    private Integer idpac;
    @Basic(optional = false)
    @Column(name = "dnipac")
    private String dnipac;
    @Basic(optional = false)
    @Column(name = "apellidospac")
    private String apellidospac;
    @Basic(optional = false)
    @Column(name = "nombrespac")
    private String nombrespac;
    @Basic(optional = false)
    @Column(name = "fechanacpac")
    private String fechanacpac;
    @Basic(optional = false)
    @Column(name = "telefonopac")
    private String telefonopac;
    @Basic(optional = false)
    @Column(name = "sexopac")
    private String sexopac;
    @Basic(optional = false)
    @Column(name = "domiciliopac")
    private String domiciliopac;
    @Basic(optional = false)
    @Column(name = "seguropac")
    private String seguropac;
    @Basic(optional = false)
    @Column(name = "tiposangrepac")
    private String tiposangrepac;
    @Basic(optional = false)
    @Column(name = "alergiaspac")
    private String alergiaspac;
    @Basic(optional = false)
    @Column(name = "fecregpac")
    private String fecregpac;
    @Column(name = "activepac")
    private Character activepac;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pacientecita")
    private List<Citas> citasList;
    @JoinColumn(name = "historiapac", referencedColumnName = "idlista")
    @ManyToOne(optional = false)
    private Historias historiapac;

    public Pacientes() {
    }

    public Pacientes(Integer idpac) {
        this.idpac = idpac;
    }

    public Pacientes(Integer idpac, String dnipac, String apellidospac, String nombrespac, String fechanacpac, String telefonopac, String sexopac, String domiciliopac, String seguropac, String tiposangrepac, String alergiaspac, Historias historiapac, String fecregpac, char activepac) {
        this.idpac = idpac;
        this.dnipac = dnipac;
        this.apellidospac = apellidospac;
        this.nombrespac = nombrespac;
        this.fechanacpac = fechanacpac;
        this.telefonopac = telefonopac;
        this.sexopac = sexopac;
        this.domiciliopac = domiciliopac;
        this.seguropac = seguropac;
        this.tiposangrepac = tiposangrepac;
        this.alergiaspac = alergiaspac;
        this.historiapac = historiapac;
        this.fecregpac = fecregpac;
        this.activepac = activepac;
    }

    public Integer getIdpac() {
        return idpac;
    }

    public void setIdpac(Integer idpac) {
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

    public String getFecregpac() {
        return fecregpac;
    }

    public void setFecregpac(String fecregpac) {
        this.fecregpac = fecregpac;
    }

    public Character getActivepac() {
        return activepac;
    }

    public void setActivepac(Character activepac) {
        this.activepac = activepac;
    }

    @XmlTransient
    public List<Citas> getCitasList() {
        return citasList;
    }

    public void setCitasList(List<Citas> citasList) {
        this.citasList = citasList;
    }

    public Historias getHistoriapac() {
        return historiapac;
    }

    public void setHistoriapac(Historias historiapac) {
        this.historiapac = historiapac;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpac != null ? idpac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pacientes)) {
            return false;
        }
        Pacientes other = (Pacientes) object;
        if ((this.idpac == null && other.idpac != null) || (this.idpac != null && !this.idpac.equals(other.idpac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Pacientes[ idpac=" + idpac + " ]";
    }
    
}
