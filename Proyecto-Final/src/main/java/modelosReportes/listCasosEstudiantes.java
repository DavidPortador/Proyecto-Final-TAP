package modelosReportes;
import java.sql.Date;
public class listCasosEstudiantes {
    String nombre,apellido, resultado, carrera;
    Date fechaDetencion;
    public listCasosEstudiantes(String nombre, String apellido,Date fechaDetencion, String resultado, String carrera){
        this.nombre= nombre;
        this.apellido=apellido;
        this.fechaDetencion= fechaDetencion;
        this.resultado= resultado;
        this.carrera= carrera;
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
