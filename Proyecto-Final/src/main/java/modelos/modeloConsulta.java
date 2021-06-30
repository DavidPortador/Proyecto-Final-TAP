package modelos;
import java.sql.Date;
public class modeloConsulta {
    int noConsulta;
    String sintomas;
    Date fecha;
    String hora;
    String tipo;
    String cveAsignacion;
    int noUsuario;
    String noCedula;
    public modeloConsulta(int noConsulta, String sintomas, Date fecha, String hora, String tipo, String cveAsignacion, int noUsuario, String noCedula) {
        this.noConsulta = noConsulta;
        this.sintomas = sintomas;
        this.fecha = fecha;
        this.hora = hora;
        this.tipo = tipo;
        this.cveAsignacion = cveAsignacion;
        this.noUsuario = noUsuario;
        this.noCedula = noCedula;
    }
    public int getNoConsulta() {
        return noConsulta;
    }
    public void setNoConsulta(int noConsulta) {
        this.noConsulta = noConsulta;
    }
    public String getSintomas() {
        return sintomas;
    }
    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public String getHora() {
        return hora;
    }
    public void setHora(String hora) {
        this.hora = hora;
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
