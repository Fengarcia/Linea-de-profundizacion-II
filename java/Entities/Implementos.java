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
@Table(name = "implementos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Implementos.findAll", query = "SELECT i FROM Implementos i")
    , @NamedQuery(name = "Implementos.findByImplemento", query = "SELECT i FROM Implementos i WHERE i.implemento = :implemento")
    , @NamedQuery(name = "Implementos.findByStockImplemento", query = "SELECT i FROM Implementos i WHERE i.stockImplemento = :stockImplemento")
    , @NamedQuery(name = "Implementos.findByEstadoImplemento", query = "SELECT i FROM Implementos i WHERE i.estadoImplemento = :estadoImplemento")})
public class Implementos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "implemento")
    private String implemento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stock_implemento")
    private int stockImplemento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "estado_implemento")
    private String estadoImplemento;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion_implemento")
    private String descripcionImplemento;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "tipoImplemento")
    private UsuarioImplementos usuarioImplementos;

    public Implementos() {
    }

    public Implementos(String implemento) {
        this.implemento = implemento;
    }

    public Implementos(String implemento, int stockImplemento, String estadoImplemento, String descripcionImplemento) {
        this.implemento = implemento;
        this.stockImplemento = stockImplemento;
        this.estadoImplemento = estadoImplemento;
        this.descripcionImplemento = descripcionImplemento;
    }

    public String getImplemento() {
        return implemento;
    }

    public void setImplemento(String implemento) {
        this.implemento = implemento;
    }

    public int getStockImplemento() {
        return stockImplemento;
    }

    public void setStockImplemento(int stockImplemento) {
        this.stockImplemento = stockImplemento;
    }

    public String getEstadoImplemento() {
        return estadoImplemento;
    }

    public void setEstadoImplemento(String estadoImplemento) {
        this.estadoImplemento = estadoImplemento;
    }

    public String getDescripcionImplemento() {
        return descripcionImplemento;
    }

    public void setDescripcionImplemento(String descripcionImplemento) {
        this.descripcionImplemento = descripcionImplemento;
    }

    public UsuarioImplementos getUsuarioImplementos() {
        return usuarioImplementos;
    }

    public void setUsuarioImplementos(UsuarioImplementos usuarioImplementos) {
        this.usuarioImplementos = usuarioImplementos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (implemento != null ? implemento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Implementos)) {
            return false;
        }
        Implementos other = (Implementos) object;
        if ((this.implemento == null && other.implemento != null) || (this.implemento != null && !this.implemento.equals(other.implemento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Implementos[ implemento=" + implemento + " ]";
    }
    
}
