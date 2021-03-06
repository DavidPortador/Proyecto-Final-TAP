package usuarios;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import database.ConsultaDAO;
import database.MySQLConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import modelos.Usuario;
import modelosReportes.*;
import reports.PDFreports;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class Directivos implements Initializable {
    PDFreports pdf=new PDFreports();
    public static final String DEST1 = "contagios/carrera/carrera_report.pdf";
    public static final String DEST2 = "contagios/departamentos/departamentos_report.pdf";
    public static final String DEST3 = "contagios/estudiantes/estudiantes_report.pdf";
    public static final String DEST4 = "contagios/personal/personal_report.pdf";
    com.itextpdf.kernel.colors.Color v_font = new DeviceRgb(0, 0, 0);
    com.itextpdf.kernel.colors.Color v_header = new DeviceRgb(34, 219, 240);
    com.itextpdf.kernel.colors.Color v_background= new DeviceRgb(175, 242, 250);
    //
    com.itextpdf.kernel.colors.Color v_font2=new DeviceRgb(0,0,0);
    com.itextpdf.kernel.colors.Color v_fontwhite=new DeviceRgb(255, 255, 255);
    com.itextpdf.kernel.colors.Color v_header2=new DeviceRgb(163, 162, 162);
    com.itextpdf.kernel.colors.Color v_background2=new DeviceRgb(247, 247, 247);
    //
    com.itextpdf.kernel.colors.Color font3=new DeviceRgb();
    com.itextpdf.kernel.colors.Color header3=new DeviceRgb(77, 0, 110);
    com.itextpdf.kernel.colors.Color Background3= new DeviceRgb(152, 0, 217);
    //
    com.itextpdf.kernel.colors.Color header4= new DeviceRgb(255, 255, 255);
    Color background4=new DeviceRgb(0,0,0);
    Usuario directivo;
    Stage anterior;
    @FXML ComboBox cboReportes;
    @FXML Label lblUsuario;
    @FXML GridPane gpBarras;
    @FXML Button btnSalir,btnPDF    ;
    @FXML ConsultaDAO consultaDAO = new ConsultaDAO(MySQLConnection.getConnection());
    @Override public void initialize(URL location, ResourceBundle resources) {
        lblUsuario.setText(directivo.getNombres()+" "+directivo.getApellidos());
        initCharts();
        initButtons();
        llenarCombo();
    }
    private void initButtons() {
        btnPDF.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cboReportes.getSelectionModel().getSelectedItem();
                System.out.println(cboReportes.getSelectionModel().getSelectedItem());
                if(cboReportes.getSelectionModel().getSelectedItem().equals("Reporte 1")){
                    File file = new File(DEST1);
                    file.getParentFile().mkdirs();
                    try {
                        createPdfCasosCarrera(DEST1);
                        sendMessage("Reported succesfull", "File: " + DEST1 + "generated...");
                        openPdfFile(DEST1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(cboReportes.getSelectionModel().getSelectedItem().equals("Reporte 2")){
                    File file2 = new File(DEST2);
                    file2.getParentFile().mkdirs();
                    try {
                        createPdfCasosDepartamento(DEST2);
                        sendMessage("Reported succesfull", "File: " + DEST2 + "generated...");
                        openPdfFile(DEST2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(cboReportes.getSelectionModel().getSelectedItem().equals("Reporte 3")){
                    File file3 = new File(DEST3);
                    file3.getParentFile().mkdirs();
                    try {
                        createPdfCasosEstudiantes(DEST3);
                        sendMessage("Reported succesfull", "File: " + DEST3 + "generated...");
                        openPdfFile(DEST3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(cboReportes.getSelectionModel().getSelectedItem().equals("Reporte 4")){
                    File file4 = new File(DEST4);
                    file4.getParentFile().mkdirs();
                    try {
                        createPdfCasosPersonal(DEST4);
                        sendMessage("Reported succesfull", "File: " + DEST4 + "generated...");
                        openPdfFile(DEST4);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                Stage stage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                stage.close();
                anterior.show();
            }
        });
    }
    private void initCharts() {
        gpBarras.add(generateTotalCasosChart(),0,0);
        gpBarras.add(generateMedicosPieChart(),1,0);
    }
    private BarChart generateTotalCasosChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc =
                new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("Total Casos");
        xAxis.setLabel("Casos");
        yAxis.setLabel("Total");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Carrera");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Departamento");
        for (listCasosCarrera row : consultaDAO.getListContagiadosCarrera()) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(row.getCarrera(), row.getContagiados());
            series1.getData().add(data);
        }
        for (listCasosDepartamento row2 : consultaDAO.getListContagiadosDepartamento()) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(row2.getDepartamentos(), row2.getContagiados());
            series2.getData().add(data);
        }
        bc.getData().addAll(series1,series2);
        return bc;
    }
    private PieChart generateMedicosPieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (listConsultasTotalMedicos listTotalMedicos : consultaDAO.getListConsultasTotalMedicos())
            pieChartData.add(new PieChart.Data(listTotalMedicos.getCedula(), listTotalMedicos.getTotal()));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("CONSULTAS DE MEDICOS");
        return chart;
    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
    public void setUsuario(Usuario usuario){
        directivo = usuario;
    }
    private void llenarCombo(){
        ObservableList<String> reportes = FXCollections.observableArrayList();
        reportes.add("Reporte 1");
        reportes.add("Reporte 2");
        reportes.add("Reporte 3");
        reportes.add("Reporte 4");
        cboReportes.setItems(reportes);
    }
    public void createPdfCasosCarrera(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        // Initialize document
        Document document = new Document(pdf, PageSize.A4.rotate());
        ImageData imageData = ImageDataFactory.create("src/main/resources/img/header.png");
        Image pdfImg= new Image(imageData);
        Paragraph paragraph=new Paragraph("CASOS DE CARRERA");
        paragraph.setFontSize(25);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        document.setMargins(20, 20, 20, 20);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        Table table1 = new Table(UnitValue.createPercentArray(new float[]{2, 4}))
                .useAllAvailableWidth();
        processPdfCarrera(table1, null, bold, true);
        for(listCasosCarrera e : consultaDAO.getListContagiadosCarrera()) {
            processPdfCarrera(table1, e, bold, false);
        }
        document.add(pdfImg);
        document.add(paragraph);
        document.add(table1);

        //Close document
        document.close();
    }
    public void processPdfCarrera(Table table, listCasosCarrera user, PdfFont font, boolean isHeader) {
        if (isHeader) {
            table.addHeaderCell(new Cell().add(new Paragraph("CARRERA").setFont(font).setBackgroundColor(v_header).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new Cell().add(new Paragraph("TOTAL CASOS").setFont(font).setBackgroundColor(v_header).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
        } else {
            table.addCell(new Cell().add(new Paragraph(user.getCarrera() + "").setFont(font).setBackgroundColor(v_background).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(user.getContagiados()+ "").setFont(font).setBackgroundColor(v_background).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
        }
    }
    public void createPdfCasosDepartamento(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        // Initialize document
        Document document = new Document(pdf, PageSize.A4.rotate());
        ImageData imageData = ImageDataFactory.create("src/main/resources/img/header.png");
        Image pdfImg= new Image(imageData);
        Paragraph paragraph=new Paragraph("CASOS DE DEPARTAMENTO");
        paragraph.setFontSize(25);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        document.setMargins(20, 20, 20, 20);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        Table table2 = new Table(UnitValue.createPercentArray(new float[]{2, 4}))
                .useAllAvailableWidth();
        processPdfDepartamento(table2, null, bold, true);
        for(listCasosDepartamento e : consultaDAO.getListContagiadosDepartamento()) {
            processPdfDepartamento(table2, e, bold, false);
        }
        document.add(pdfImg);
        document.add(paragraph);
        document.add(table2);
        document.close();
    }
    public void processPdfDepartamento(Table table, listCasosDepartamento  user, PdfFont font, boolean isHeader) {
        if (isHeader) {
            table.addHeaderCell(new Cell().add(new Paragraph("DEPARTAMENTO").setFont(font).setBackgroundColor(v_header2).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new Cell().add(new Paragraph("TOTAL CASOS").setFont(font).setBackgroundColor(v_header2).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
        } else {
            table.addCell(new Cell().add(new Paragraph(user.getDepartamentos() + "").setFont(font).setBackgroundColor(v_background2).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(user.getContagiados()+ "").setFont(font).setBackgroundColor(v_background2).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
        }
    }
    public void createPdfCasosEstudiantes(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        // Initialize document
        Document document = new Document(pdf, PageSize.A4.rotate());
        ImageData imageData = ImageDataFactory.create("src/main/resources/img/header.png");
        Image pdfImg= new Image(imageData);
        Paragraph paragraph=new Paragraph("CASOS DE ESTUDIANTES");
        paragraph.setFontSize(25);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        document.setMargins(20, 20, 20, 20);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        Table table3 = new Table(UnitValue.createPercentArray(new float[]{5, 4,4,4}))
                .useAllAvailableWidth();
        processPdfCasosEstudiantes(table3, null, bold, true);
        for(listCasosEstudiantes e : consultaDAO.getListContagiadosEstudiantes()) {
            processPdfCasosEstudiantes(table3, e, bold, false);
        }
        document.add(pdfImg);
        document.add(paragraph);
        document.add(table3);
        //Close document
        document.close();
    }
    public void processPdfCasosEstudiantes(Table table, listCasosEstudiantes  user, PdfFont font, boolean isHeader) {
        if (isHeader) {
            table.addHeaderCell(new Cell().add(new Paragraph("NOMBRE COMPLETO").setFont(font).setBackgroundColor(header3).setFontColor(v_fontwhite)).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new Cell().add(new Paragraph("FECHA DETENCION").setFont(font).setBackgroundColor(header3).setFontColor(v_fontwhite)));
            table.addHeaderCell(new Cell().add(new Paragraph("RESULTADO").setFont(font).setBackgroundColor(header3).setFontColor(v_fontwhite)));
            table.addHeaderCell(new Cell().add(new Paragraph("CARRERA").setFont(font).setBackgroundColor(header3).setFontColor(v_fontwhite)));
        } else {
            table.addCell(new Cell().add(new Paragraph(user.getNombre() + " " + user.getApellido()+"").setFont(font).setBackgroundColor(background4).setFontColor(v_fontwhite)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(user.getFechaDetencion()+ "").setFont(font).setBackgroundColor(background4).setFontColor(v_fontwhite)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(user.getResultado()+ "").setFont(font).setBackgroundColor(background4).setFontColor(v_fontwhite)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(user.getCarrera()+ "").setFont(font).setBackgroundColor(background4).setFontColor(v_fontwhite)).setTextAlignment(TextAlignment.CENTER));
        }
    }
    public void createPdfCasosPersonal(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        // Initialize document
        Document document = new Document(pdf, PageSize.A4.rotate());
        ImageData imageData = ImageDataFactory.create("src/main/resources/img/header.png");
        Image pdfImg= new Image(imageData);
        Paragraph paragraph=new Paragraph("CASOS DE PERSONAL");
        paragraph.setFontSize(25);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        document.setMargins(20, 20, 20, 20);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        Table table4 = new Table(UnitValue.createPercentArray(new float[]{6, 4,4,4}))
                .useAllAvailableWidth();
        processPdfCasosPersonal(table4, null, bold, true);
        for(listCasosDelPersonal e : consultaDAO.getListContagiadosPersonal()) {
            processPdfCasosPersonal(table4, e, bold, false);
        }
        document.add(pdfImg);
        document.add(paragraph);
        document.add(table4);
        //Close document
        document.close();
    }
    public void processPdfCasosPersonal(Table table, listCasosDelPersonal  user, PdfFont font, boolean isHeader) {
        if (isHeader) {
            table.addHeaderCell(new Cell().add(new Paragraph("NOMBRE COMPLETO").setFont(font).setBackgroundColor(header3).setFontColor(v_fontwhite)).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new Cell().add(new Paragraph("FECHA DETENCION").setFont(font).setBackgroundColor(header3).setFontColor(v_fontwhite)).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new Cell().add(new Paragraph("RESULTADO").setFont(font).setBackgroundColor(header3).setFontColor(v_fontwhite)).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new Cell().add(new Paragraph("CARRERA").setFont(font).setBackgroundColor(header3).setFontColor(v_fontwhite)).setTextAlignment(TextAlignment.CENTER));

        } else {
            table.addCell(new Cell().add(new Paragraph(user.getNombre() + " " + user.getApellido()+"").setFont(font).setBackgroundColor(Background3).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(user.getFechaDetencion()+ "").setFont(font).setBackgroundColor(Background3).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(user.getResultado()+ "").setFont(font).setBackgroundColor(Background3).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(user.getDepartamento()+ "").setFont(font).setBackgroundColor(Background3).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));

        }
    }
    private void openPdfFile(String filename) {
        if (Desktop.isDesktopSupported()) {
            try { File myFile = new File(filename);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) { } } }
    private void sendMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
