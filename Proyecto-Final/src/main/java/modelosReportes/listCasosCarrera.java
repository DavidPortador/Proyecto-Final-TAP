package modelosReportes;
public class listCasosCarrera {
    String carrera;
    int contagiados;
    public listCasosCarrera(String carrera, int contagiados){
        this.carrera= carrera;
        this.contagiados= contagiados;

    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public int getContagiados() {
        return contagiados;
    }

    public void setContagiados(int contagiados) {
        this.contagiados = contagiados;
    }
}
