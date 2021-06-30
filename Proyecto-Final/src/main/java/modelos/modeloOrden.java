package modelos;
public class modeloOrden {
    int noOrden;
    String resultado;
    int noConsulta;
    String noCedula;
    String cvePrueba;
    public modeloOrden(int noOrden, String resultado, int noConsulta, String noCedula, String cvePrueba) {
        this.noOrden = noOrden;
        this.resultado = resultado;
        this.noConsulta = noConsulta;
        this.noCedula = noCedula;
        this.cvePrueba = cvePrueba;
    }
    public int getNoOrden() {
        return noOrden;
    }
    public void setNoOrden(int noOrden) {
        this.noOrden = noOrden;
    }
    public String getResultado() {
        return resultado;
    }
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    public int getNoConsulta() {
        return noConsulta;
    }
    public void setNoConsulta(int noConsulta) {
        this.noConsulta = noConsulta;
    }
    public String getNoCedula() {
        return noCedula;
    }
    public void setNoCedula(String noCedula) {
        this.noCedula = noCedula;
    }
    public String getCvePrueba() {
        return cvePrueba;
    }
    public void setCvePrueba(String cvePrueba) {
        this.cvePrueba = cvePrueba;
    }
}
