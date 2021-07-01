package modelosReportes;
import java.sql.Date;
public class listCasosDelPersonal {
    String nombre,apellido, resultado, departamento;
    Date fechaDetencion;
    public listCasosDelPersonal(String nombre, String apellido,Date fechaDetencion, String resultado, String departamento){
        this.nombre= nombre;
        this.apellido=apellido;
        this.fechaDetencion= fechaDetencion;
        this.resultado= resultado;
        this.departamento= departamento;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getResultado() {
        return resultado;
    }
    public void setResultado(String resultado) {
        this.resultado = resultado;
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
