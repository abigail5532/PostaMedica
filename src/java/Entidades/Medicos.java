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
@Table(name = "medicos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Medicos.findAll", query = "SELECT m FROM Medicos m"),
    @NamedQuery(name = "Medicos.findByIdmed", query = "SELECT m FROM Medicos m WHERE m.idmed = :idmed"),
    @NamedQuery(name = "Medicos.findByNombresmed", query = "SELECT m FROM Medicos m WHERE m.nombresmed = :nombresmed"),
    @NamedQuery(name = "Medicos.findByApellidosmed", query = "SELECT m FROM Medicos m WHERE m.apellidosmed = :apellidosmed"),
    @NamedQuery(name = "Medicos.findByDnimed", query = "SELECT m FROM Medicos m WHERE m.dnimed = :dnimed"),
    @NamedQuery(name = "Medicos.findByCmpmed", query = "SELECT m FROM Medicos m WHERE m.cmpmed = :cmpmed"),
    @NamedQuery(name = "Medicos.findByTelefonomed", query = "SELECT m FROM Medicos m WHERE m.telefonomed = :telefonomed"),
    @NamedQuery(name = "Medicos.findByEmailmed", query = "SELECT m FROM Medicos m WHERE m.emailmed = :emailmed"),
    @NamedQuery(name = "Medicos.findByDomiciliomed", query = "SELECT m FROM Medicos m WHERE m.domiciliomed = :domiciliomed"),
    @NamedQuery(name = "Medicos.findByFecregmed", query = "SELECT m FROM Medicos m WHERE m.fecregmed = :fecregmed"),
    @NamedQuery(name = "Medicos.findByActivemed", query = "SELECT m FROM Medicos m WHERE m.activemed = :activemed")})
public class Medicos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idmed")
    private Integer idmed;
    @Basic(optional = false)
    @Column(name = "nombresmed")
    private String nombresmed;
    @Basic(optional = false)
    @Column(name = "apellidosmed")
    private String apellidosmed;
    @Basic(optional = false)
    @Column(name = "dnimed")
    private String dnimed;
    @Basic(optional = false)
    @Column(name = "cmpmed")
    private String cmpmed;
    @Basic(optional = false)
    @Column(name = "telefonomed")
    private String telefonomed;
    @Basic(optional = false)
    @Column(name = "emailmed")
    private String emailmed;
    @Basic(optional = false)
    @Column(name = "domiciliomed")
    private String domiciliomed;
    @Basic(optional = false)
    @Column(name = "fecregmed")
    private String fecregmed;
    @Column(name = "activemed")
    private Character activemed;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "especialidadcita")
    private List<Citas> citasList;
    @JoinColumn(name = "especialidadmed", referencedColumnName = "idesp")
    @ManyToOne(optional = false)
    private Especialidades especialidadmed;

    public Medicos() {
    }

    public Medicos(Integer idmed) {
        this.idmed = idmed;
    }

    public Medicos(Integer idmed, String nombresmed, String apellidosmed, Especialidades especialidadmed, String dnimed, String cmpmed, String telefonomed, String emailmed, String domiciliomed, String fecregmed, char activemed) {
        this.idmed = idmed;
        this.nombresmed = nombresmed;
        this.apellidosmed = apellidosmed;
        this.especialidadmed = especialidadmed;
        this.dnimed = dnimed;
        this.cmpmed = cmpmed;
        this.telefonomed = telefonomed;
        this.emailmed = emailmed;
        this.domiciliomed = domiciliomed;
        this.fecregmed = fecregmed;
        this.activemed = activemed;
    }

    public Integer getIdmed() {
        return idmed;
    }

    public void setIdmed(Integer idmed) {
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

    public Character getActivemed() {
        return activemed;
    }

    public void setActivemed(Character activemed) {
        this.activemed = activemed;
    }

    @XmlTransient
    public List<Citas> getCitasList() {
        return citasList;
    }

    public void setCitasList(List<Citas> citasList) {
        this.citasList = citasList;
    }

    public Especialidades getEspecialidadmed() {
        return especialidadmed;
    }

    public void setEspecialidadmed(Especialidades especialidadmed) {
        this.especialidadmed = especialidadmed;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmed != null ? idmed.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medicos)) {
            return false;
        }
        Medicos other = (Medicos) object;
        if ((this.idmed == null && other.idmed != null) || (this.idmed != null && !this.idmed.equals(other.idmed))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Medicos[ idmed=" + idmed + " ]";
    }
    
}
