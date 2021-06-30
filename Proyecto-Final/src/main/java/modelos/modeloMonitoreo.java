package modelos;
public class modeloMonitoreo {
    int noOrden;
    String resultado;
    String nombres;
    String cvePrueba;
    public modeloMonitoreo(int noOrden, String resultado, String nombres, String cvePrueba) {
        this.noOrden = noOrden;
        this.resultado = resultado;
        this.nombres = nombres;
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
    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getCvePrueba() {
        return cvePrueba;
    }
    public void setCvePrueba(String cvePrueba) {
        this.cvePrueba = cvePrueba;
    }
}
