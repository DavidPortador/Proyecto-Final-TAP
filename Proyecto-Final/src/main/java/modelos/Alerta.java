package modelos;
public class Alerta {
    int noAlerta;
    int noOrden;
    String tipoAlerta;
    String descripcion;
    public Alerta(int noAlerta, int noOrden, String tipoAlerta, String descripcion) {
        this.noAlerta = noAlerta;
        this.noOrden = noOrden;
        this.tipoAlerta = tipoAlerta;
        this.descripcion = descripcion;
    }
    public int getNoAlerta() {
        return noAlerta;
    }
    public void setNoAlerta(int noAlerta) {
        this.noAlerta = noAlerta;
    }
    public int getNoOrden() {
        return noOrden;
    }
    public void setNoOrden(int noOrden) {
        this.noOrden = noOrden;
    }
    public String getTipoAlerta() {
        return tipoAlerta;
    }
    public void setTipoAlerta(String tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
