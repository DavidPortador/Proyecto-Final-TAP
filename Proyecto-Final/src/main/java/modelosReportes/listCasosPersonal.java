package modelosReportes;
public class listCasosPersonal {
    String departamento;
    int totalCasos;
    public listCasosPersonal(String departamento, int totalCasos){
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
