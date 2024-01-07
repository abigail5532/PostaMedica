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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alessandra
 */
@Entity
@Table(name = "consultas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consultas.findAll", query = "SELECT c FROM Consultas c"),
    @NamedQuery(name = "Consultas.findByIdcons", query = "SELECT c FROM Consultas c WHERE c.idcons = :idcons"),
    @NamedQuery(name = "Consultas.findByDiagnosticocons", query = "SELECT c FROM Consultas c WHERE c.diagnosticocons = :diagnosticocons"),
    @NamedQuery(name = "Consultas.findByTratamientocons", query = "SELECT c FROM Consultas c WHERE c.tratamientocons = :tratamientocons"),
    @NamedQuery(name = "Consultas.findByEstadocons", query = "SELECT c FROM Consultas c WHERE c.estadocons = :estadocons"),
    @NamedQuery(name = "Consultas.findByActivecons", query = "SELECT c FROM Consultas c WHERE c.activecons = :activecons")})
public class Consultas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcons")
    private Integer idcons;
    @Basic(optional = false)
    @Column(name = "diagnosticocons")
    private String diagnosticocons;
    @Basic(optional = false)
    @Column(name = "tratamientocons")
    private String tratamientocons;
    @Basic(optional = false)
    @Column(name = "estadocons")
    private String estadocons;
    @Column(name = "activecons")
    private Character activecons;
    @JoinColumn(name = "triajecons", referencedColumnName = "idtri")
    @ManyToOne(optional = false)
    private Triaje triajecons;

    public Consultas() {
    }

    public Consultas(Integer idcons) {
        this.idcons = idcons;
    }

    public Consultas(Integer idcons, Triaje triajecons, String diagnosticocons, String tratamientocons, String estadocons, char activecons) {
        this.idcons = idcons;
        this.triajecons = triajecons;
        this.diagnosticocons = diagnosticocons;
        this.tratamientocons = tratamientocons;
        this.estadocons = estadocons;
        this.activecons = activecons;
    }

    public Integer getIdcons() {
        return idcons;
    }

    public void setIdcons(Integer idcons) {
        this.idcons = idcons;
    }

    public String getDiagnosticocons() {
        return diagnosticocons;
    }

    public void setDiagnosticocons(String diagnosticocons) {
        this.diagnosticocons = diagnosticocons;
    }

    public String getTratamientocons() {
        return tratamientocons;
    }

    public void setTratamientocons(String tratamientocons) {
        this.tratamientocons = tratamientocons;
    }

    public String getEstadocons() {
        return estadocons;
    }

    public void setEstadocons(String estadocons) {
        this.estadocons = estadocons;
    }

    public Character getActivecons() {
        return activecons;
    }

    public void setActivecons(Character activecons) {
        this.activecons = activecons;
    }

    public Triaje getTriajecons() {
        return triajecons;
    }

    public void setTriajecons(Triaje triajecons) {
        this.triajecons = triajecons;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcons != null ? idcons.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consultas)) {
            return false;
        }
        Consultas other = (Consultas) object;
        if ((this.idcons == null && other.idcons != null) || (this.idcons != null && !this.idcons.equals(other.idcons))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Consultas[ idcons=" + idcons + " ]";
    }
    
}
