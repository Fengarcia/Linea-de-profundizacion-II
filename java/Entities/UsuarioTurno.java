/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "usuario_turno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioTurno.findAll", query = "SELECT u FROM UsuarioTurno u")
    , @NamedQuery(name = "UsuarioTurno.findByIdUsuTurn", query = "SELECT u FROM UsuarioTurno u WHERE u.idUsuTurn = :idUsuTurn")})
public class UsuarioTurno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "id_usu_turn")
    private String idUsuTurn;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @OneToOne(optional = false)
    private Usuario idUsuario;
    @JoinColumn(name = "id_turno", referencedColumnName = "id_turno")
    @OneToOne(optional = false)
    private Turno idTurno;

    public UsuarioTurno() {
    }

    public UsuarioTurno(String idUsuTurn) {
        this.idUsuTurn = idUsuTurn;
    }

    public String getIdUsuTurn() {
        return idUsuTurn;
    }

    public void setIdUsuTurn(String idUsuTurn) {
        this.idUsuTurn = idUsuTurn;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Turno getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Turno idTurno) {
        this.idTurno = idTurno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuTurn != null ? idUsuTurn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioTurno)) {
            return false;
        }
        UsuarioTurno other = (UsuarioTurno) object;
        if ((this.idUsuTurn == null && other.idUsuTurn != null) || (this.idUsuTurn != null && !this.idUsuTurn.equals(other.idUsuTurn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.UsuarioTurno[ idUsuTurn=" + idUsuTurn + " ]";
    }
    
}
