package modelos;
public class modeloPersonal {
    String noCont;
    String cveAsignacion;
    int noUsuario;
    String cveCarrera;

    public modeloPersonal(String noCont, String cveAsignacion, int noUsuario, String cveCarrera) {
        this.noCont = noCont;
        this.cveAsignacion = cveAsignacion;
        this.noUsuario = noUsuario;
        this.cveCarrera = cveCarrera;
    }

    public String getNoCont() {
        return noCont;
    }

    public void setNoCont(String noCont) {
        this.noCont = noCont;
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

    public String getCveCarrera() {
        return cveCarrera;
    }

    public void setCveCarrera(String cveCarrera) {
        this.cveCarrera = cveCarrera;
    }
}
