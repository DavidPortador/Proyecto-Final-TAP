package modelosReportes;
public class listTotalEncuentasCarrera {
    String carrera;
    int totalEncuentas;
    public listTotalEncuentasCarrera(String carrera, int totalEncuentas){
        this.carrera= carrera;
        this.totalEncuentas= totalEncuentas;
    }
    public String getCarrera() {
        return carrera;
    }
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    public int getTotalEncuentas() {
        return totalEncuentas;
    }
    public void setTotalEncuentas(int totalEncuentas) {
        this.totalEncuentas = totalEncuentas;
    }
}
