package modelos;
public class modeloAlertaM {
    int noAlertaMonitoreo;
    String descripcion;
    int noOrden;
    String noMonitoreo;
    public modeloAlertaM(int noAlertaMonitoreo, String descripcion, int noOrden, String noMonitoreo) {
        this.noAlertaMonitoreo = noAlertaMonitoreo;
        this.descripcion = descripcion;
        this.noOrden = noOrden;
        this.noMonitoreo = noMonitoreo;
    }
    public int getNoAlertaMonitoreo() {
        return noAlertaMonitoreo;
    }
    public void setNoAlertaMonitoreo(int noAlertaMonitoreo) {
        this.noAlertaMonitoreo = noAlertaMonitoreo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public int getNoOrden() {
        return noOrden;
    }
    public void setNoOrden(int noOrden) {
        this.noOrden = noOrden;
    }
    public String getNoMonitoreo() {
        return noMonitoreo;
    }
    public void setNoMonitoreo(String noMonitoreo) {
        this.noMonitoreo = noMonitoreo;
    }
}
