package modelos;
public class modeloPersonal {
    String noPersonal;
    String cveAsignacion;
    int noUsuario;
    String cveDepa;
    public modeloPersonal(String noPersonal, String cveAsignacion, int noUsuario, String cveDepa) {
        this.noPersonal = noPersonal;
        this.cveAsignacion = cveAsignacion;
        this.noUsuario = noUsuario;
        this.cveDepa = cveDepa;
    }
    public String getNoPersonal() {
        return noPersonal;
    }
    public void setNoPersonal(String noPersonal) {
        this.noPersonal = noPersonal;
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
    public String getCveDepa() {
        return cveDepa;
    }
    public void setCveDepa(String cveDepa) {
        this.cveDepa = cveDepa;
    }
}
