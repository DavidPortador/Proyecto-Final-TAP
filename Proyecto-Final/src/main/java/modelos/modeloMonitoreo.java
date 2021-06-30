package modelos;
public class modeloMonitoreo {
    int noOrden;
    String resultado;
    String nombres;
    String tipo;
    public modeloMonitoreo(int noOrden, String resultado, String nombres, String tipo) {
        this.noOrden = noOrden;
        this.resultado = resultado;
        this.nombres = nombres;
        this.tipo = tipo;
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
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
