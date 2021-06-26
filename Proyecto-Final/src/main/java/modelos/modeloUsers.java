package modelos;
public class modeloUsers {
    int noUsuario;
    String asignacion;
    String usuario;
    String contra;
    String nombres;
    String apellidos;
    public modeloUsers(int noUsuario, String asignacion, String usuario, String contra, String nombres, String apellidos) {
        this.noUsuario = noUsuario;
        this.asignacion = asignacion;
        this.usuario = usuario;
        this.contra = contra;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }
    public int getNoUsuario() {
        return noUsuario;
    }
    public void setNoUsuario(int noUsuario) {
        this.noUsuario = noUsuario;
    }
    public String getAsignacion() {
        return asignacion;
    }
    public void setAsignacion(String asignacion) {
        this.asignacion = asignacion;
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
}
