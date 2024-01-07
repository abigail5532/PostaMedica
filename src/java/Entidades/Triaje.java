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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "triaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Triaje.findAll", query = "SELECT t FROM Triaje t"),
    @NamedQuery(name = "Triaje.findByIdtri", query = "SELECT t FROM Triaje t WHERE t.idtri = :idtri"),
    @NamedQuery(name = "Triaje.findByPesotri", query = "SELECT t FROM Triaje t WHERE t.pesotri = :pesotri"),
    @NamedQuery(name = "Triaje.findByEstaturatri", query = "SELECT t FROM Triaje t WHERE t.estaturatri = :estaturatri"),
    @NamedQuery(name = "Triaje.findByTemperaturatri", query = "SELECT t FROM Triaje t WHERE t.temperaturatri = :temperaturatri"),
    @NamedQuery(name = "Triaje.findByPresiontri", query = "SELECT t FROM Triaje t WHERE t.presiontri = :presiontri"),
    @NamedQuery(name = "Triaje.findByPulsotri", query = "SELECT t FROM Triaje t WHERE t.pulsotri = :pulsotri"),
    @NamedQuery(name = "Triaje.findByRespiraciontri", query = "SELECT t FROM Triaje t WHERE t.respiraciontri = :respiraciontri"),
    @NamedQuery(name = "Triaje.findByActivetri", query = "SELECT t FROM Triaje t WHERE t.activetri = :activetri")})
public class Triaje implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtri")
    private Integer idtri;
    @Basic(optional = false)
    @Column(name = "pesotri")
    private String pesotri;
    @Basic(optional = false)
    @Column(name = "estaturatri")
    private String estaturatri;
    @Basic(optional = false)
    @Column(name = "temperaturatri")
    private String temperaturatri;
    @Basic(optional = false)
    @Column(name = "presiontri")
    private String presiontri;
    @Basic(optional = false)
    @Column(name = "pulsotri")
    private int pulsotri;
    @Basic(optional = false)
    @Column(name = "respiraciontri")
    private int respiraciontri;
    @Basic(optional = false)
    @Column(name = "activetri")
    private Character activetri;
    @JoinColumn(name = "citatri", referencedColumnName = "idcita")
    @ManyToOne(optional = false)
    private Citas citatri;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "triajecons")
    private List<Consultas> consultasList;

    public Triaje() {
    }

    public Triaje(Integer idtri) {
        this.idtri = idtri;
    }

    public Triaje(Integer idtri, Citas citatri, String pesotri, String estaturatri, String temperaturatri, String presiontri, int pulsotri, int respiraciontri, char activetri) {
        this.idtri = idtri;
        this.citatri = citatri;
        this.pesotri = pesotri;
        this.estaturatri = estaturatri;
        this.temperaturatri = temperaturatri;
        this.presiontri = presiontri;
        this.pulsotri = pulsotri;
        this.respiraciontri = respiraciontri;
        this.activetri = activetri;
    }

    public Integer getIdtri() {
        return idtri;
    }

    public void setIdtri(Integer idtri) {
        this.idtri = idtri;
    }

    public String getPesotri() {
        return pesotri;
    }

    public void setPesotri(String pesotri) {
        this.pesotri = pesotri;
    }

    public String getEstaturatri() {
        return estaturatri;
    }

    public void setEstaturatri(String estaturatri) {
        this.estaturatri = estaturatri;
    }

    public String getTemperaturatri() {
        return temperaturatri;
    }

    public void setTemperaturatri(String temperaturatri) {
        this.temperaturatri = temperaturatri;
    }

    public String getPresiontri() {
        return presiontri;
    }

    public void setPresiontri(String presiontri) {
        this.presiontri = presiontri;
    }

    public int getPulsotri() {
        return pulsotri;
    }

    public void setPulsotri(int pulsotri) {
        this.pulsotri = pulsotri;
    }

    public int getRespiraciontri() {
        return respiraciontri;
    }

    public void setRespiraciontri(int respiraciontri) {
        this.respiraciontri = respiraciontri;
    }

    public Character getActivetri() {
        return activetri;
    }

    public void setActivetri(Character activetri) {
        this.activetri = activetri;
    }

    public Citas getCitatri() {
        return citatri;
    }

    public void setCitatri(Citas citatri) {
        this.citatri = citatri;
    }

    @XmlTransient
    public List<Consultas> getConsultasList() {
        return consultasList;
    }

    public void setConsultasList(List<Consultas> consultasList) {
        this.consultasList = consultasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtri != null ? idtri.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Triaje)) {
            return false;
        }
        Triaje other = (Triaje) object;
        if ((this.idtri == null && other.idtri != null) || (this.idtri != null && !this.idtri.equals(other.idtri))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Triaje[ idtri=" + idtri + " ]";
    }
    
}
