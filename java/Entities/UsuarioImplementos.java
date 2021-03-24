/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "usuario_implementos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioImplementos.findAll", query = "SELECT u FROM UsuarioImplementos u")
    , @NamedQuery(name = "UsuarioImplementos.findByIdUsuImp", query = "SELECT u FROM UsuarioImplementos u WHERE u.idUsuImp = :idUsuImp")
    , @NamedQuery(name = "UsuarioImplementos.findByCantidad", query = "SELECT u FROM UsuarioImplementos u WHERE u.cantidad = :cantidad")
    , @NamedQuery(name = "UsuarioImplementos.findByFechaEntregaImp", query = "SELECT u FROM UsuarioImplementos u WHERE u.fechaEntregaImp = :fechaEntregaImp")
    , @NamedQuery(name = "UsuarioImplementos.findByFechaDevolucion", query = "SELECT u FROM UsuarioImplementos u WHERE u.fechaDevolucion = :fechaDevolucion")})
public class UsuarioImplementos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "id_usu_imp")
    private String idUsuImp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_entrega_imp")
    @Temporal(TemporalType.DATE)
    private Date fechaEntregaImp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_devolucion")
    @Temporal(TemporalType.DATE)
    private Date fechaDevolucion;
    @JoinColumn(name = "tipo_implemento", referencedColumnName = "implemento")
    @OneToOne(optional = false)
    private Implementos tipoImplemento;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @OneToOne(optional = false)
    private Usuario idUsuario;

    public UsuarioImplementos() {
    }

    public UsuarioImplementos(String idUsuImp) {
        this.idUsuImp = idUsuImp;
    }

    public UsuarioImplementos(String idUsuImp, int cantidad, Date fechaEntregaImp, Date fechaDevolucion) {
        this.idUsuImp = idUsuImp;
        this.cantidad = cantidad;
        this.fechaEntregaImp = fechaEntregaImp;
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getIdUsuImp() {
        return idUsuImp;
    }

    public void setIdUsuImp(String idUsuImp) {
        this.idUsuImp = idUsuImp;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaEntregaImp() {
        return fechaEntregaImp;
    }

    public void setFechaEntregaImp(Date fechaEntregaImp) {
        this.fechaEntregaImp = fechaEntregaImp;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Implementos getTipoImplemento() {
        return tipoImplemento;
    }

    public void setTipoImplemento(Implementos tipoImplemento) {
        this.tipoImplemento = tipoImplemento;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuImp != null ? idUsuImp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioImplementos)) {
            return false;
        }
        UsuarioImplementos other = (UsuarioImplementos) object;
        if ((this.idUsuImp == null && other.idUsuImp != null) || (this.idUsuImp != null && !this.idUsuImp.equals(other.idUsuImp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.UsuarioImplementos[ idUsuImp=" + idUsuImp + " ]";
    }
    
}
