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
@Table(name = "historias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historias.findAll", query = "SELECT h FROM Historias h"),
    @NamedQuery(name = "Historias.findByIdlista", query = "SELECT h FROM Historias h WHERE h.idlista = :idlista"),
    @NamedQuery(name = "Historias.findByNrohistlista", query = "SELECT h FROM Historias h WHERE h.nrohistlista = :nrohistlista"),
    @NamedQuery(name = "Historias.findByNroboletalista", query = "SELECT h FROM Historias h WHERE h.nroboletalista = :nroboletalista"),
    @NamedQuery(name = "Historias.findByFecreglista", query = "SELECT h FROM Historias h WHERE h.fecreglista = :fecreglista"),
    @NamedQuery(name = "Historias.findByActivelista", query = "SELECT h FROM Historias h WHERE h.activelista = :activelista")})
public class Historias implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idlista")
    private Integer idlista;
    @Basic(optional = false)
    @Column(name = "nrohistlista")
    private String nrohistlista;
    @Basic(optional = false)
    @Column(name = "nroboletalista")
    private String nroboletalista;
    @Basic(optional = false)
    @Column(name = "fecreglista")
    private String fecreglista;
    @Column(name = "activelista")
    private Character activelista;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "historiapac")
    private List<Pacientes> pacientesList;

    public Historias() {
    }

    public Historias(Integer idlista) {
        this.idlista = idlista;
    }

    public Historias(Integer idlista, String nrohistlista, String nroboletalista, String fecreglista, char activelista) {
        this.idlista = idlista;
        this.nrohistlista = nrohistlista;
        this.nroboletalista = nroboletalista;
        this.fecreglista = fecreglista;
        this.activelista = activelista;
    }

    public Integer getIdlista() {
        return idlista;
    }

    public void setIdlista(Integer idlista) {
        this.idlista = idlista;
    }

    public String getNrohistlista() {
        return nrohistlista;
    }

    public void setNrohistlista(String nrohistlista) {
        this.nrohistlista = nrohistlista;
    }

    public String getNroboletalista() {
        return nroboletalista;
    }

    public void setNroboletalista(String nroboletalista) {
        this.nroboletalista = nroboletalista;
    }

    public String getFecreglista() {
        return fecreglista;
    }

    public void setFecreglista(String fecreglista) {
        this.fecreglista = fecreglista;
    }

    public Character getActivelista() {
        return activelista;
    }

    public void setActivelista(Character activelista) {
        this.activelista = activelista;
    }

    @XmlTransient
    public List<Pacientes> getPacientesList() {
        return pacientesList;
    }

    public void setPacientesList(List<Pacientes> pacientesList) {
        this.pacientesList = pacientesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlista != null ? idlista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historias)) {
            return false;
        }
        Historias other = (Historias) object;
        if ((this.idlista == null && other.idlista != null) || (this.idlista != null && !this.idlista.equals(other.idlista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Historias[ idlista=" + idlista + " ]";
    }
    
}
