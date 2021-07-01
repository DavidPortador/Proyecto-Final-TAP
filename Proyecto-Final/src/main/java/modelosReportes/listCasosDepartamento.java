package modelosReportes;
public class listCasosDepartamento {
    String departamentos;
    int contagiados;
    public listCasosDepartamento(String departamentos, int contagiados){
        this.departamentos= departamentos;
        this.contagiados= contagiados;
    }
    public String getDepartamentos() {
        return departamentos;
    }
    public void setDepartamentos(String departamentos) {
        this.departamentos = departamentos;
    }
    public int getContagiados() {
        return contagiados;
    }
    public void setContagiados(int contagiados) {
        this.contagiados = contagiados;
    }
}
