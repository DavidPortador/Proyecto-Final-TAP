package modelosReportes;
public class listCasosDepartamento {
    String departamento;
    int totalCasos;
    public listCasosDepartamento(String departamento, int totalCasos){
        this.departamento= departamento;
        this.totalCasos= totalCasos;
    }
    public String getDepartamento() {
        return departamento;
    }
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    public int getTotalCasos() {
        return totalCasos;
    }
    public void setTotalCasos(int totalCasos) {
        this.totalCasos = totalCasos;
    }
}
