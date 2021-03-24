/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "dotacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dotacion.findAll", query = "SELECT d FROM Dotacion d")
    , @NamedQuery(name = "Dotacion.findByIdDotacion", query = "SELECT d FROM Dotacion d WHERE d.idDotacion = :idDotacion")
    , @NamedQuery(name = "Dotacion.findByStockDotacion", query = "SELECT d FROM Dotacion d WHERE d.stockDotacion = :stockDotacion")
    , @NamedQuery(name = "Dotacion.findByTallaDotacion", query = "SELECT d FROM Dotacion d WHERE d.tallaDotacion = :tallaDotacion")})
public class Dotacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "id_dotacion")
    private String idDotacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stock_dotacion")
    private short stockDotacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "talla_dotacion")
    private String tallaDotacion;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idDota")
    private UsuarioDotacion usuarioDotacion;

    public Dotacion() {
    }

    public Dotacion(String idDotacion) {
        this.idDotacion = idDotacion;
    }

    public Dotacion(String idDotacion, short stockDotacion, String tallaDotacion, String descripcion) {
        this.idDotacion = idDotacion;
        this.stockDotacion = stockDotacion;
        this.tallaDotacion = tallaDotacion;
        this.descripcion = descripcion;
    }

    public String getIdDotacion() {
        return idDotacion;
    }

    public void setIdDotacion(String idDotacion) {
        this.idDotacion = idDotacion;
    }

    public short getStockDotacion() {
        return stockDotacion;
    }

    public void setStockDotacion(short stockDotacion) {
        this.stockDotacion = stockDotacion;
    }

    public String getTallaDotacion() {
        return tallaDotacion;
    }

    public void setTallaDotacion(String tallaDotacion) {
        this.tallaDotacion = tallaDotacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public UsuarioDotacion getUsuarioDotacion() {
        return usuarioDotacion;
    }

    public void setUsuarioDotacion(UsuarioDotacion usuarioDotacion) {
        this.usuarioDotacion = usuarioDotacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDotacion != null ? idDotacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dotacion)) {
            return false;
        }
        Dotacion other = (Dotacion) object;
        if ((this.idDotacion == null && other.idDotacion != null) || (this.idDotacion != null && !this.idDotacion.equals(other.idDotacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Dotacion[ idDotacion=" + idDotacion + " ]";
    }
    
}
