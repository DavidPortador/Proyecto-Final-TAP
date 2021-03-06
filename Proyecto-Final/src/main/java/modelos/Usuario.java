package modelos;
import java.sql.Date;
public class Usuario {
    int noUsuario;
    String usuario;
    String contra;
    String nombres;
    String apellidos;
    String genero;
    String correo;
    Date fechaNac;
    public Usuario(int noUsuario, String usuario, String contra, String nombres, String apellidos, String genero, String correo, Date fechaNac) {
        this.noUsuario = noUsuario;
        this.usuario = usuario;
        this.contra = contra;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.genero = genero;
        this.correo = correo;
        this.fechaNac = fechaNac;
    }
    public int getNoUsuario() {
        return noUsuario;
    }
    public void setNoUsuario(int noUsuario) {
        this.noUsuario = noUsuario;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getContra() {
        return contra;
    }
    public void setContra(String contra) {
        this.contra = contra;
    }
    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public Date getFechaNac() {
        return fechaNac;
    }
    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }
}
