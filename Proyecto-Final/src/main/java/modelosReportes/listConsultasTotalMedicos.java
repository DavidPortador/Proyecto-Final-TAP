package modelosReportes;
public class listConsultasTotalMedicos {
    String medico;
    int total;
    public listConsultasTotalMedicos(String medico, int total){
        this.medico= medico;
        this.total=total;
    }
    public String getMedico() {
        return medico;
    }
    public void setMedico(String medico) {
        this.medico = medico;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
}
