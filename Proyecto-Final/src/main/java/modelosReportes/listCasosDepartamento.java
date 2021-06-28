package modelosReportes;
import java.sql.Date;
public class listCasosDepartamento {
    String nombre, resultadoPrueba, departamento;
    Date fechaDetencion;
    public listCasosDepartamento(String nombre, String resultadoPrueba, String departamento, Date fechaDetencion){
        this.nombre= nombre;
        this.resultadoPrueba= resultadoPrueba;
        this.departamento= departamento;
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
    public String getDepartamento() {
        return departamento;
    }
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    public Date getFechaDetencion() {
        return fechaDetencion;
    }
    public void setFechaDetencion(Date fechaDetencion) {
        this.fechaDetencion = fechaDetencion;
    }
}
