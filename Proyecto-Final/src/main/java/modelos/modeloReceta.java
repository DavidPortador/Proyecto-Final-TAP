package modelos;

public class modeloReceta {
    int noReceta;
    String indicaciones,noConsulta;

    public modeloReceta(int noReceta, String indicaciones, String noConsulta) {
        this.noReceta = noReceta;
        this.indicaciones = indicaciones;
        this.noConsulta = noConsulta;
    }

    public int getNoReceta() {
        return noReceta;
    }

    public void setNoReceta(int noReceta) {
        this.noReceta = noReceta;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public String getNoConsulta() {
        return noConsulta;
    }

    public void setNoConsulta(String noConsulta) {
        this.noConsulta = noConsulta;
    }
}
