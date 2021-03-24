/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario")
    , @NamedQuery(name = "Usuario.findByTipoDocumentoUsuario", query = "SELECT u FROM Usuario u WHERE u.tipoDocumentoUsuario = :tipoDocumentoUsuario")
    , @NamedQuery(name = "Usuario.findByNombreUsuario", query = "SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario")
    , @NamedQuery(name = "Usuario.findByTipoUsuario", query = "SELECT u FROM Usuario u WHERE u.tipoUsuario = :tipoUsuario")
    , @NamedQuery(name = "Usuario.findByFechaNacimiento", query = "SELECT u FROM Usuario u WHERE u.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "Usuario.findByNumeroCelular", query = "SELECT u FROM Usuario u WHERE u.numeroCelular = :numeroCelular")
    , @NamedQuery(name = "Usuario.findByDireccionUsuario", query = "SELECT u FROM Usuario u WHERE u.direccionUsuario = :direccionUsuario")
    , @NamedQuery(name = "Usuario.findByCorreo", query = "SELECT u FROM Usuario u WHERE u.correo = :correo")
    , @NamedQuery(name = "Usuario.findByContrase\u00f1a", query = "SELECT u FROM Usuario u WHERE u.contrase\u00f1a = :contrase\u00f1a")
    , @NamedQuery(name = "Usuario.findByTallaCamisa", query = "SELECT u FROM Usuario u WHERE u.tallaCamisa = :tallaCamisa")
    , @NamedQuery(name = "Usuario.findByTallaPantalon", query = "SELECT u FROM Usuario u WHERE u.tallaPantalon = :tallaPantalon")
    , @NamedQuery(name = "Usuario.findByPos", query = "SELECT u FROM Usuario u WHERE u.pos = :pos")
    , @NamedQuery(name = "Usuario.findByNumeroCuenta", query = "SELECT u FROM Usuario u WHERE u.numeroCuenta = :numeroCuenta")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "tipo_documento_usuario")
    private String tipoDocumentoUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "tipo_usuario")
    private String tipoUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_celular")
    private int numeroCelular;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "direccion_usuario")
    private String direccionUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "contrase\u00f1a")
    private String contraseña;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "talla_camisa")
    private String tallaCamisa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "talla_pantalon")
    private short tallaPantalon;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pos")
    private short pos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_cuenta")
    private int numeroCuenta;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private UsuarioImplementos usuarioImplementos;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private UsuarioTurno usuarioTurno;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Salario salario;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Ventas ventas;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private UsuarioDotacion usuarioDotacion;

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(Integer idUsuario, String tipoDocumentoUsuario, String nombreUsuario, String tipoUsuario, Date fechaNacimiento, int numeroCelular, String direccionUsuario, String correo, String contraseña, String tallaCamisa, short tallaPantalon, short pos, int numeroCuenta) {
        this.idUsuario = idUsuario;
        this.tipoDocumentoUsuario = tipoDocumentoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.tipoUsuario = tipoUsuario;
        this.fechaNacimiento = fechaNacimiento;
        this.numeroCelular = numeroCelular;
        this.direccionUsuario = direccionUsuario;
        this.correo = correo;
        this.contraseña = contraseña;
        this.tallaCamisa = tallaCamisa;
        this.tallaPantalon = tallaPantalon;
        this.pos = pos;
        this.numeroCuenta = numeroCuenta;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipoDocumentoUsuario() {
        return tipoDocumentoUsuario;
    }

    public void setTipoDocumentoUsuario(String tipoDocumentoUsuario) {
        this.tipoDocumentoUsuario = tipoDocumentoUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(int numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getDireccionUsuario() {
        return direccionUsuario;
    }

    public void setDireccionUsuario(String direccionUsuario) {
        this.direccionUsuario = direccionUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTallaCamisa() {
        return tallaCamisa;
    }

    public void setTallaCamisa(String tallaCamisa) {
        this.tallaCamisa = tallaCamisa;
    }

    public short getTallaPantalon() {
        return tallaPantalon;
    }

    public void setTallaPantalon(short tallaPantalon) {
        this.tallaPantalon = tallaPantalon;
    }

    public short getPos() {
        return pos;
    }

    public void setPos(short pos) {
        this.pos = pos;
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public UsuarioImplementos getUsuarioImplementos() {
        return usuarioImplementos;
    }

    public void setUsuarioImplementos(UsuarioImplementos usuarioImplementos) {
        this.usuarioImplementos = usuarioImplementos;
    }

    public UsuarioTurno getUsuarioTurno() {
        return usuarioTurno;
    }

    public void setUsuarioTurno(UsuarioTurno usuarioTurno) {
        this.usuarioTurno = usuarioTurno;
    }

    public Salario getSalario() {
        return salario;
    }

    public void setSalario(Salario salario) {
        this.salario = salario;
    }

    public Ventas getVentas() {
        return ventas;
    }

    public void setVentas(Ventas ventas) {
        this.ventas = ventas;
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
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Usuario[ idUsuario=" + idUsuario + " ]";
    }
    
}
