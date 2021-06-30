package modelos;
public class modeloSolicitud {
    int noSolicitud;
    String estado;
    String tipo;
    String cveAsignacion;
    int noUsuario;
    String noCedula;
    public modeloSolicitud(int noSolicitud, String estado, String tipo, String cveAsignacion, int noUsuario, String noCedula) {
        this.noSolicitud = noSolicitud;
        this.estado = estado;
        this.tipo = tipo;
        this.cveAsignacion = cveAsignacion;
        this.noUsuario = noUsuario;
        this.noCedula = noCedula;
    }
    public int getNoSolicitud() {
        return noSolicitud;
    }
    public void setNoSolicitud(int noSolicitud) {
        this.noSolicitud = noSolicitud;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getCveAsignacion() {
        return cveAsignacion;
    }
    public void setCveAsignacion(String cveAsignacion) {
        this.cveAsignacion = cveAsignacion;
    }
    public int getNoUsuario() {
        return noUsuario;
    }
    public void setNoUsuario(int noUsuario) {
        this.noUsuario = noUsuario;
    }
    public String getNoCedula() {
        return noCedula;
    }
    public void setNoCedula(String noCedula) {
        this.noCedula = noCedula;
    }
}
