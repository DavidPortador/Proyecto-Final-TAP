package modelos;
public class Alerta {
    int noAlerta;
    String tipoAlerta;
    String descripcion;
    String noOrden;
    public Alerta(int noAlerta, String tipoAlerta, String descripcion, String noOrden) {
        this.noAlerta = noAlerta;
        this.tipoAlerta = tipoAlerta;
        this.descripcion = descripcion;
        this.noOrden = noOrden;
    }
    public int getNoAlerta() {
        return noAlerta;
    }
    public void setNoAlerta(int noAlerta) {
        this.noAlerta = noAlerta;
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
    public String getNoOrden() {
        return noOrden;
    }
    public void setNoOrden(String noOrden) {
        this.noOrden = noOrden;
    }
}
