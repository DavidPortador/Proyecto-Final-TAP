package modelosReportes;
public class listTotalEncuentasDepartamento {
    String departamento;
    int totalEncuentas;
    public listTotalEncuentasDepartamento(String departamento, int totalEncuentas){
        this.departamento= departamento;
        this.totalEncuentas= totalEncuentas;
    }
    public String getDepartamento() {
        return departamento;
    }
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    public int getTotalEncuentas() {
        return totalEncuentas;
    }
    public void setTotalEncuentas(int totalEncuentas) {
        this.totalEncuentas = totalEncuentas;
    }
}
