package reports;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import database.MySQLConnection;
import database.UserDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import modelosReportes.listCasosCarrera;
import modelosReportes.listCasosDepartamento;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class PDFreports implements Initializable {
    UserDAO userDAO = new UserDAO(MySQLConnection.getConnection());
    Stage anterior;
    Color v_backG = new DeviceRgb(0, 142, 0);
    Color v_font = new DeviceRgb(0, 0, 0);
    Color v_backG2= new DeviceRgb(251,113,112);
    public static final String DEST1 = "contagios/carrera/carrera_report.pdf";
    public static final String DEST2 = "contagios/departamentos/departamentos_report.pdf";
    @FXML Button btnReporte,btnSalir;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initButtons();
    }
    private void initButtons() {
        btnReporte.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = new File(DEST1);
                file.getParentFile().mkdirs();
                try {
                    new PDFreports().createPdfCasosCarrera(DEST1);
                    sendMessage("Reported succesfull", "File: " + DEST1 + "generated...");
                    openPdfFile(DEST1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                File file2 = new File(DEST2);
                file2.getParentFile().mkdirs();
                try {
                    new PDFreports().createPdfCasosDepartamento(DEST2);
                    sendMessage("Reported succesfull", "File: " + DEST1 + "generated...");
                    openPdfFile(DEST2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                stage.close();
                anterior.show();
            }
        });
    }
    public void createPdfCasosCarrera(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        // Initialize document
        Document document = new Document(pdf, PageSize.A4.rotate());
        document.setMargins(20, 20, 20, 20);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        Table table = new Table(UnitValue.createPercentArray(new float[]{2, 4}))
                .useAllAvailableWidth();
        processPdfCarrera(table, null, bold, true);
        for(listCasosCarrera e : userDAO.getListContagiadosCarrera()) {
            processPdfCarrera(table, e, bold, false);
        }
        document.add(table);
        //Close document
        document.close();
    }
    public void processPdfCarrera(Table table, listCasosCarrera user, PdfFont font, boolean isHeader) {
        if (isHeader) {
            table.addHeaderCell(new Cell().add(new Paragraph("CARRERA").setFont(font).setBackgroundColor(v_backG).setFontColor(v_font)));
            table.addHeaderCell(new Cell().add(new Paragraph("TOTAL CASOS").setFont(font).setBackgroundColor(v_backG).setFontColor(v_font)));
        } else {
            table.addCell(new Cell().add(new Paragraph(user.getCarrera() + "").setFont(font).setBackgroundColor(v_backG2).setFontColor(v_font)));
            table.addCell(new Cell().add(new Paragraph(user.getContagiados()+ "").setFont(font).setBackgroundColor(v_backG2).setFontColor(v_font)));
        }
    }
    public void createPdfCasosDepartamento(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        // Initialize document
        Document document = new Document(pdf, PageSize.A4.rotate());
        document.setMargins(20, 20, 20, 20);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        Table table = new Table(UnitValue.createPercentArray(new float[]{2, 4}))
                .useAllAvailableWidth();
        processPdfDepartamento(table, null, bold, true);
        for(listCasosDepartamento e : userDAO.getListContagiadosDepartamento()) {
            processPdfDepartamento(table, e, bold, false);
        }
        document.add(table);
        //Close document
        document.close();
    }
    public void processPdfDepartamento(Table table, listCasosDepartamento  user, PdfFont font, boolean isHeader) {
        if (isHeader) {
            table.addHeaderCell(new Cell().add(new Paragraph("DEPARTAMENTO").setFont(font).setBackgroundColor(v_backG).setFontColor(v_font)));
            table.addHeaderCell(new Cell().add(new Paragraph("TOTAL CASOS").setFont(font).setBackgroundColor(v_backG).setFontColor(v_font)));
        } else {
            table.addCell(new Cell().add(new Paragraph(user.getDepartamentos() + "").setFont(font).setBackgroundColor(v_backG2).setFontColor(v_font)));
            table.addCell(new Cell().add(new Paragraph(user.getContagiados()+ "").setFont(font).setBackgroundColor(v_backG2).setFontColor(v_font)));
        }
    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
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

