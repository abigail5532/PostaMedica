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
@Table(name = "especialidades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Especialidades.findAll", query = "SELECT e FROM Especialidades e"),
    @NamedQuery(name = "Especialidades.findByIdesp", query = "SELECT e FROM Especialidades e WHERE e.idesp = :idesp"),
    @NamedQuery(name = "Especialidades.findByNombreesp", query = "SELECT e FROM Especialidades e WHERE e.nombreesp = :nombreesp"),
    @NamedQuery(name = "Especialidades.findByNrooficinaesp", query = "SELECT e FROM Especialidades e WHERE e.nrooficinaesp = :nrooficinaesp"),
    @NamedQuery(name = "Especialidades.findByActiveesp", query = "SELECT e FROM Especialidades e WHERE e.activeesp = :activeesp")})
public class Especialidades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idesp")
    private Integer idesp;
    @Basic(optional = false)
    @Column(name = "nombreesp")
    private String nombreesp;
    @Basic(optional = false)
    @Column(name = "nrooficinaesp")
    private String nrooficinaesp;
    @Column(name = "activeesp")
    private Character activeesp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "especialidadmed")
    private List<Medicos> medicosList;

    public Especialidades() {
    }

    public Especialidades(Integer idesp) {
        this.idesp = idesp;
    }

    public Especialidades(Integer idesp, String nombreesp, String nrooficinaesp, char activeesp) {
        this.idesp = idesp;
        this.nombreesp = nombreesp;
        this.nrooficinaesp = nrooficinaesp;
        this.activeesp = activeesp;
    }

    public Integer getIdesp() {
        return idesp;
    }

    public void setIdesp(Integer idesp) {
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

    public Character getActiveesp() {
        return activeesp;
    }

    public void setActiveesp(Character activeesp) {
        this.activeesp = activeesp;
    }

    @XmlTransient
    public List<Medicos> getMedicosList() {
        return medicosList;
    }

    public void setMedicosList(List<Medicos> medicosList) {
        this.medicosList = medicosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idesp != null ? idesp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Especialidades)) {
            return false;
        }
        Especialidades other = (Especialidades) object;
        if ((this.idesp == null && other.idesp != null) || (this.idesp != null && !this.idesp.equals(other.idesp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Especialidades[ idesp=" + idesp + " ]";
    }
    
}
