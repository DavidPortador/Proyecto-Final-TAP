package modelosReportes;
public class listConsultasTotalMedicos {
    String cedula;
    int total;
    public listConsultasTotalMedicos(String cedula, int total){
        this.cedula= cedula;
        this.total=total;
    }
    public String getCedula() {
        return cedula;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
}
