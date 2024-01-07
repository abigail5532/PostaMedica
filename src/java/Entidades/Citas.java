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
@Table(name = "citas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Citas.findAll", query = "SELECT c FROM Citas c"),
    @NamedQuery(name = "Citas.findByIdcita", query = "SELECT c FROM Citas c WHERE c.idcita = :idcita"),
    @NamedQuery(name = "Citas.findByFechacita", query = "SELECT c FROM Citas c WHERE c.fechacita = :fechacita"),
    @NamedQuery(name = "Citas.findByHoracita", query = "SELECT c FROM Citas c WHERE c.horacita = :horacita"),
    @NamedQuery(name = "Citas.findByActivecita", query = "SELECT c FROM Citas c WHERE c.activecita = :activecita")})
public class Citas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idcita")
    private Integer idcita;
    @Basic(optional = false)
    @Column(name = "fechacita")
    private String fechacita;
    @Basic(optional = false)
    @Column(name = "horacita")
    private String horacita;
    @Column(name = "activecita")
    private Character activecita;
    @JoinColumn(name = "pacientecita", referencedColumnName = "idpac")
    @ManyToOne(optional = false)
    private Pacientes pacientecita;
    @JoinColumn(name = "especialidadcita", referencedColumnName = "idmed")
    @ManyToOne(optional = false)
    private Medicos especialidadcita;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "citatri")
    private List<Triaje> triajeList;

    public Citas() {
    }

    public Citas(Integer idcita) {
        this.idcita = idcita;
    }

    public Citas(Integer idcita, Pacientes pacientecita, Medicos especialidadcita, String fechacita, String horacita, char activecita) {
        this.idcita = idcita;
        this.pacientecita = pacientecita;
        this.especialidadcita = especialidadcita;
        this.fechacita = fechacita;
        this.horacita = horacita;
        this.activecita = activecita;
    }

    public Integer getIdcita() {
        return idcita;
    }

    public void setIdcita(Integer idcita) {
        this.idcita = idcita;
    }

    public String getFechacita() {
        return fechacita;
    }

    public void setFechacita(String fechacita) {
        this.fechacita = fechacita;
    }

    public String getHoracita() {
        return horacita;
    }

    public void setHoracita(String horacita) {
        this.horacita = horacita;
    }

    public Character getActivecita() {
        return activecita;
    }

    public void setActivecita(Character activecita) {
        this.activecita = activecita;
    }

    public Pacientes getPacientecita() {
        return pacientecita;
    }

    public void setPacientecita(Pacientes pacientecita) {
        this.pacientecita = pacientecita;
    }

    public Medicos getEspecialidadcita() {
        return especialidadcita;
    }

    public void setEspecialidadcita(Medicos especialidadcita) {
        this.especialidadcita = especialidadcita;
    }

    @XmlTransient
    public List<Triaje> getTriajeList() {
        return triajeList;
    }

    public void setTriajeList(List<Triaje> triajeList) {
        this.triajeList = triajeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcita != null ? idcita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Citas)) {
            return false;
        }
        Citas other = (Citas) object;
        if ((this.idcita == null && other.idcita != null) || (this.idcita != null && !this.idcita.equals(other.idcita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Citas[ idcita=" + idcita + " ]";
    }
    
}
