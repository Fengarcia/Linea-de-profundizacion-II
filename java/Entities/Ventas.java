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
import javax.persistence.Lob;
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
@Table(name = "ventas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ventas.findAll", query = "SELECT v FROM Ventas v")
    , @NamedQuery(name = "Ventas.findByIdVenta", query = "SELECT v FROM Ventas v WHERE v.idVenta = :idVenta")
    , @NamedQuery(name = "Ventas.findByTotalVenta", query = "SELECT v FROM Ventas v WHERE v.totalVenta = :totalVenta")
    , @NamedQuery(name = "Ventas.findByNumApertura", query = "SELECT v FROM Ventas v WHERE v.numApertura = :numApertura")
    , @NamedQuery(name = "Ventas.findBySucursal", query = "SELECT v FROM Ventas v WHERE v.sucursal = :sucursal")
    , @NamedQuery(name = "Ventas.findByFechaVenta", query = "SELECT v FROM Ventas v WHERE v.fechaVenta = :fechaVenta")
    , @NamedQuery(name = "Ventas.findByNombreEmpresa", query = "SELECT v FROM Ventas v WHERE v.nombreEmpresa = :nombreEmpresa")})
public class Ventas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id_venta")
    private String idVenta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_venta")
    private long totalVenta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_apertura")
    private int numApertura;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "sucursal")
    private String sucursal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_venta")
    @Temporal(TemporalType.DATE)
    private Date fechaVenta;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "comprobante")
    private byte[] comprobante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nombre_empresa")
    private String nombreEmpresa;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @OneToOne(optional = false)
    private Usuario idUsuario;
    @JoinColumn(name = "id_ruta", referencedColumnName = "id_ruta")
    @OneToOne(optional = false)
    private Ruta idRuta;

    public Ventas() {
    }

    public Ventas(String idVenta) {
        this.idVenta = idVenta;
    }

    public Ventas(String idVenta, long totalVenta, int numApertura, String sucursal, Date fechaVenta, byte[] comprobante, String nombreEmpresa) {
        this.idVenta = idVenta;
        this.totalVenta = totalVenta;
        this.numApertura = numApertura;
        this.sucursal = sucursal;
        this.fechaVenta = fechaVenta;
        this.comprobante = comprobante;
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public long getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(long totalVenta) {
        this.totalVenta = totalVenta;
    }

    public int getNumApertura() {
        return numApertura;
    }

    public void setNumApertura(int numApertura) {
        this.numApertura = numApertura;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public byte[] getComprobante() {
        return comprobante;
    }

    public void setComprobante(byte[] comprobante) {
        this.comprobante = comprobante;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Ruta getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Ruta idRuta) {
        this.idRuta = idRuta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVenta != null ? idVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ventas)) {
            return false;
        }
        Ventas other = (Ventas) object;
        if ((this.idVenta == null && other.idVenta != null) || (this.idVenta != null && !this.idVenta.equals(other.idVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Ventas[ idVenta=" + idVenta + " ]";
    }
    
}
