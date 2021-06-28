package modelosReportes;
import java.sql.Date;
public class listCasosCarrera {
    String nombre, resultadoPrueba, carrera;
    Date fechaDetencion;
    public listCasosCarrera(String nombre, String resultadoPrueba, String carrera, Date fechaDetencion){
        this.nombre= nombre;
        this.resultadoPrueba= resultadoPrueba;
        this.carrera= carrera;
        this.fechaDetencion= fechaDetencion;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getResultadoPrueba() {
        return resultadoPrueba;
    }
    public void setResultadoPrueba(String resultadoPrueba) {
        this.resultadoPrueba = resultadoPrueba;
    }
    public String getCarrera() {
        return carrera;
    }
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    public Date getFechaDetencion() {
        return fechaDetencion;
    }
    public void setFechaDetencion(Date fechaDetencion) {
        this.fechaDetencion = fechaDetencion;
    }
}
