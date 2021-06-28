package modelosReportes;
public class listCasosEstudiante {
    String carrera;
    int totalCasos;
    public listCasosEstudiante(String carrera, int totalCasos){
       this.carrera= carrera;
       this.totalCasos= totalCasos;

    }
    public String getCarrera() {
        return carrera;
    }
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    public int getTotalCasos() {
        return totalCasos;
    }
    public void setTotalCasos(int totalCasos) {
        this.totalCasos = totalCasos;
    }
}
