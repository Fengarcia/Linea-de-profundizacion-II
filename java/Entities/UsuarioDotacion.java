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
@Table(name = "usuario_dotacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioDotacion.findAll", query = "SELECT u FROM UsuarioDotacion u")
    , @NamedQuery(name = "UsuarioDotacion.findByIdUsuDota", query = "SELECT u FROM UsuarioDotacion u WHERE u.idUsuDota = :idUsuDota")
    , @NamedQuery(name = "UsuarioDotacion.findByCantidad", query = "SELECT u FROM UsuarioDotacion u WHERE u.cantidad = :cantidad")
    , @NamedQuery(name = "UsuarioDotacion.findByFechaEntregaDota", query = "SELECT u FROM UsuarioDotacion u WHERE u.fechaEntregaDota = :fechaEntregaDota")})
public class UsuarioDotacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "id_usu_dota")
    private String idUsuDota;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_entrega_dota")
    @Temporal(TemporalType.DATE)
    private Date fechaEntregaDota;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @OneToOne(optional = false)
    private Usuario idUsuario;
    @JoinColumn(name = "id_dota", referencedColumnName = "id_dotacion")
    @OneToOne(optional = false)
    private Dotacion idDota;

    public UsuarioDotacion() {
    }

    public UsuarioDotacion(String idUsuDota) {
        this.idUsuDota = idUsuDota;
    }

    public UsuarioDotacion(String idUsuDota, int cantidad, Date fechaEntregaDota) {
        this.idUsuDota = idUsuDota;
        this.cantidad = cantidad;
        this.fechaEntregaDota = fechaEntregaDota;
    }

    public String getIdUsuDota() {
        return idUsuDota;
    }

    public void setIdUsuDota(String idUsuDota) {
        this.idUsuDota = idUsuDota;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaEntregaDota() {
        return fechaEntregaDota;
    }

    public void setFechaEntregaDota(Date fechaEntregaDota) {
        this.fechaEntregaDota = fechaEntregaDota;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Dotacion getIdDota() {
        return idDota;
    }

    public void setIdDota(Dotacion idDota) {
        this.idDota = idDota;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuDota != null ? idUsuDota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioDotacion)) {
            return false;
        }
        UsuarioDotacion other = (UsuarioDotacion) object;
        if ((this.idUsuDota == null && other.idUsuDota != null) || (this.idUsuDota != null && !this.idUsuDota.equals(other.idUsuDota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.UsuarioDotacion[ idUsuDota=" + idUsuDota + " ]";
    }
    
}
