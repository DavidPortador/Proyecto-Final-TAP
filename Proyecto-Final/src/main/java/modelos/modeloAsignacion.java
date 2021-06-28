package modelos;

public class modeloAsignacion {
    String no;
    String cveAsignacion;

    public modeloAsignacion(String no, String cveAsignacion) {
        this.no = no;
        this.cveAsignacion = cveAsignacion;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getCveAsignacion() {
        return cveAsignacion;
    }

    public void setCveAsignacion(String cveAsignacion) {
        this.cveAsignacion = cveAsignacion;
    }
}
