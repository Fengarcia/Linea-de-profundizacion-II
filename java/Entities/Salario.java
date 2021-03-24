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
@Table(name = "salario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salario.findAll", query = "SELECT s FROM Salario s")
    , @NamedQuery(name = "Salario.findByIdSalario", query = "SELECT s FROM Salario s WHERE s.idSalario = :idSalario")
    , @NamedQuery(name = "Salario.findByValorSalario", query = "SELECT s FROM Salario s WHERE s.valorSalario = :valorSalario")
    , @NamedQuery(name = "Salario.findByLimiteComision", query = "SELECT s FROM Salario s WHERE s.limiteComision = :limiteComision")
    , @NamedQuery(name = "Salario.findByFechaPago", query = "SELECT s FROM Salario s WHERE s.fechaPago = :fechaPago")})
public class Salario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "id_salario")
    private String idSalario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_salario")
    private long valorSalario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "limite_comision")
    private long limiteComision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion_salario")
    private String descripcionSalario;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @OneToOne(optional = false)
    private Usuario idUsuario;

    public Salario() {
    }

    public Salario(String idSalario) {
        this.idSalario = idSalario;
    }

    public Salario(String idSalario, long valorSalario, long limiteComision, Date fechaPago, String descripcionSalario) {
        this.idSalario = idSalario;
        this.valorSalario = valorSalario;
        this.limiteComision = limiteComision;
        this.fechaPago = fechaPago;
        this.descripcionSalario = descripcionSalario;
    }

    public String getIdSalario() {
        return idSalario;
    }

    public void setIdSalario(String idSalario) {
        this.idSalario = idSalario;
    }

    public long getValorSalario() {
        return valorSalario;
    }

    public void setValorSalario(long valorSalario) {
        this.valorSalario = valorSalario;
    }

    public long getLimiteComision() {
        return limiteComision;
    }

    public void setLimiteComision(long limiteComision) {
        this.limiteComision = limiteComision;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getDescripcionSalario() {
        return descripcionSalario;
    }

    public void setDescripcionSalario(String descripcionSalario) {
        this.descripcionSalario = descripcionSalario;
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
        hash += (idSalario != null ? idSalario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salario)) {
            return false;
        }
        Salario other = (Salario) object;
        if ((this.idSalario == null && other.idSalario != null) || (this.idSalario != null && !this.idSalario.equals(other.idSalario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Salario[ idSalario=" + idSalario + " ]";
    }
    
}
