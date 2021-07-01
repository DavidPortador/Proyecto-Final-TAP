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
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import controllers.Solicitud;
import database.ConsultaDAO;
import database.MySQLConnection;
import encuesta.Encuesta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelos.*;
import modelosReportes.listCasosCarrera;
import org.kordamp.bootstrapfx.BootstrapFX;
import reports.PDFreports;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static reports.PDFreports.DEST1;

public class Estudiantes implements Initializable {
    /*
    Estudiante y Personal son lo mismo casi casi xd
        Los botones son (En ese orden)
            Encuestas
            Alertas
            Ordenes
            Consultas   (opciones) -> solicitar consulta o imprimir recetas
    */
    public static final String DEST5 = "contagios/personal/estudiante.pdf";
    public static final String DEST6 = "contagios/personal/receta.pdf";
    ConsultaDAO consultaDAO = new ConsultaDAO(MySQLConnection.getConnection());
    Usuario estudiante;
    Stage anterior;
    Color v_font = new DeviceRgb(0, 0, 0);
    Color v_header = new DeviceRgb(196, 49, 0);
    Color v_background= new DeviceRgb(255, 125, 82);
    Color v_header2=new DeviceRgb(95, 179, 84);
    Color v_background2=new DeviceRgb(133, 255, 117);
    @FXML Button btnEncuestas, btnSalir, btnAlerta, btnConsulta, btnOrdenes, btnSolicitud,btnPDF;
    @FXML TableView tblEstudiante;
    @FXML Label lblUsuario;
    @Override public void initialize(URL location, ResourceBundle resources) {
        lblUsuario.setText(estudiante.getNombres()+" "+estudiante.getApellidos());
        createTableAlertas();
        initButtons();
    }
    private void initButtons() {
        btnEncuestas.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                try {
                    showEncuesta(event);
                } catch (IOException e) {
                    alertMessage("Error","btnEncuestas", e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
        btnAlerta.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                createTableAlertas();
            }
        });
        btnConsulta.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                createTableConsultas();
            }
        });
        btnOrdenes.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                createTableOrdenes();
            }
        });
        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                Stage stage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                stage.close();
                anterior.show();
            }
        });
        btnSolicitud.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showSolicitud(event);
                } catch (IOException e) {
                    alertMessage("Error","btnSolicitud", e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
        btnPDF.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    madePDF();
                } catch (Exception e) {
                    alertMessage("Error","btnPDF", e.getMessage(), Alert.AlertType.ERROR);
                }

            }
        });
    }
    private void createTableAlertas() {
        ObservableList <Alerta> alertas = FXCollections.observableArrayList();
        ObservableList<Alerta> generales, monitoreadas;
        tblEstudiante.getItems().clear();
        tblEstudiante.getColumns().clear();
        TableColumn noAlerta = new TableColumn("noAlerta");
        noAlerta.setMinWidth(130);
        TableColumn noOrden = new TableColumn("noOrden");
        noOrden.setMinWidth(130);
        TableColumn TipoAlerta = new TableColumn("Tipo Alerta");
        TipoAlerta.setMinWidth(250);
        TableColumn descripcion = new TableColumn("Descripcion");
        descripcion.setMinWidth(350);
        noAlerta.setCellValueFactory(new PropertyValueFactory<>("noAlerta"));
        noOrden.setCellValueFactory(new PropertyValueFactory<>("noOrden"));
        TipoAlerta.setCellValueFactory(new PropertyValueFactory<>("tipoAlerta"));
        descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tblEstudiante.getColumns()
                .addAll(noAlerta, noOrden, TipoAlerta, descripcion);
        try {
            generales = consultaDAO.getAlertasGenerales(estudiante.getNoUsuario());
            monitoreadas = consultaDAO.getAlertasMonitoreadas(estudiante.getNoUsuario());
            for (int i = 0; i < generales.size(); i++)
                alertas.add(generales.get(i));
            for (int i = 0; i < monitoreadas.size(); i++)
                alertas.add(monitoreadas.get(i));
            tblEstudiante.setItems(alertas);
        } catch (SQLException e) {
            alertMessage("Error","createTable", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void createTableConsultas() {
        ObservableList <modeloConsulta> consultas;
        tblEstudiante.getItems().clear();
        tblEstudiante.getColumns().clear();
        TableColumn noConsulta = new TableColumn("noCon");
        noConsulta.setMinWidth(80);
        TableColumn sintomas = new TableColumn("sintomas");
        sintomas.setMinWidth(150);
        TableColumn fecha = new TableColumn("Fecha");
        fecha.setMinWidth(120);
        TableColumn hora = new TableColumn("Hora");
        hora.setMinWidth(120);
        TableColumn tipo = new TableColumn("Tipo");
        tipo.setMinWidth(120);
        TableColumn cveAsignacion = new TableColumn("cveAsig");
        cveAsignacion.setMinWidth(120);
        TableColumn noUsuario = new TableColumn("noUsua");
        noUsuario.setMinWidth(120);
        TableColumn noCedula = new TableColumn("noCedu");
        noCedula.setMinWidth(120);
        noConsulta.setCellValueFactory(new PropertyValueFactory<>("noConsulta"));
        sintomas.setCellValueFactory(new PropertyValueFactory<>("sintomas"));
        fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        hora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        cveAsignacion.setCellValueFactory(new PropertyValueFactory<>("cveAsignacion"));
        noUsuario.setCellValueFactory(new PropertyValueFactory<>("noUsuario"));
        noCedula.setCellValueFactory(new PropertyValueFactory<>("noCedula"));
        tblEstudiante.getColumns()
                .addAll(noConsulta, sintomas, fecha, hora, tipo, cveAsignacion, noUsuario, noCedula);
        try {
            consultas = consultaDAO.getConsultaUsuario(estudiante.getNoUsuario());
            tblEstudiante.setItems(consultas);
        } catch (SQLException e) {
            alertMessage("Error", "createTableConsultas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void createTableOrdenes() {
        ObservableList <modeloOrden> ordenes;
        tblEstudiante.getItems().clear();
        tblEstudiante.getColumns().clear();

        TableColumn noOrden = new TableColumn("noOrd");
        noOrden.setMinWidth(80);
        TableColumn resultado = new TableColumn("resultado");
        resultado.setMinWidth(150);
        TableColumn noConsulta = new TableColumn("noConsulta");
        noConsulta.setMinWidth(120);
        TableColumn noCedula = new TableColumn("noCedula");
        noCedula.setMinWidth(120);
        TableColumn cvePrueba = new TableColumn("cvePrueba");
        cvePrueba.setMinWidth(120);

        noOrden.setCellValueFactory(new PropertyValueFactory<>("noOrden"));
        resultado.setCellValueFactory(new PropertyValueFactory<>("resultado"));
        noConsulta.setCellValueFactory(new PropertyValueFactory<>("noConsulta"));
        noCedula.setCellValueFactory(new PropertyValueFactory<>("noCedula"));
        cvePrueba.setCellValueFactory(new PropertyValueFactory<>("cvePrueba"));
        tblEstudiante.getColumns()
                .addAll(noOrden, resultado, noConsulta, noCedula, cvePrueba);
        try {
            ordenes = consultaDAO.getOrdenUsuario(estudiante.getNoUsuario());
            tblEstudiante.setItems(ordenes);
        } catch (SQLException e) {
            alertMessage("Error", "createTableConsultas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void showEncuesta(ActionEvent event) throws IOException {
        Stage encuestas = new Stage();
        encuestas.setTitle("Encuesta");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/encuesta.fxml"));
        Encuesta encuesta = new Encuesta();
        encuesta.setUsuario(estudiante);
        loader.setController(encuesta);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        encuestas.setResizable(false);
        // Le pasa como parametro el stage actual y nueva
        Stage actual = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        //encuesta.setStageAnterior(actual);
        //actual.close();
        // Muestra el nuevo stage
        encuestas.setScene(scene);
        //primaryStage.show();
        encuestas.initOwner(actual);
        encuestas.initModality(Modality.WINDOW_MODAL);
        encuestas.show();
    }
    private void madePDF(){

        File file = new File(DEST5);
        file.getParentFile().mkdirs();
        try {
           createPdfOrdenes(DEST5);
            sendMessage("Reported succesfull", "File: " + DEST1 + "generated...");
            openPdfFile(DEST5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file2 = new File(DEST6);
        file2.getParentFile().mkdirs();
        try {
            createPdfReceta(DEST6);
            sendMessage("Reported succesfull", "File: " + DEST1 + "generated...");
            openPdfFile(DEST6);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void createPdfOrdenes(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        // Initialize document
        Document document = new Document(pdf, PageSize.A4.rotate());
        ImageData imageData = ImageDataFactory.create("src/main/resources/img/header.png");
        com.itextpdf.layout.element.Image pdfImg= new com.itextpdf.layout.element.Image(imageData);
        Paragraph paragraph=new Paragraph("Orden");
        paragraph.setFontSize(25);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        document.setMargins(20, 20, 20, 20);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        Table table1 = new Table(UnitValue.createPercentArray(new float[]{5, 4,4,4,4}))
                .useAllAvailableWidth();
        processPdfOrdenes(table1, null, bold, true);
        for(modeloOrden e : consultaDAO.getListOrdenes(estudiante.getNoUsuario())) {
            processPdfOrdenes(table1, e, bold, false);
        }
        document.add(pdfImg);
        document.add(paragraph);
        document.add(table1);

        //Close document
        document.close();
    }
    public void processPdfOrdenes(Table table, modeloOrden user, PdfFont font, boolean isHeader) {
        if (isHeader) {
            table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("No.Orden").setFont(font).setBackgroundColor(v_header).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Resultado").setFont(font).setBackgroundColor(v_header).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("No.Consulta").setFont(font).setBackgroundColor(v_header).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("No.Cedula").setFont(font).setBackgroundColor(v_header).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Clave de Prueba").setFont(font).setBackgroundColor(v_header).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
        } else {
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(user.getNoOrden() + "").setFont(font).setBackgroundColor(v_background).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(user.getResultado()+ "").setFont(font).setBackgroundColor(v_background).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(user.getNoConsulta()+ "").setFont(font).setBackgroundColor(v_background).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(user.getNoCedula()+ "").setFont(font).setBackgroundColor(v_background).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(user.getCvePrueba()+ "").setFont(font).setBackgroundColor(v_background).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
        }
    }
    public void createPdfReceta(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        // Initialize document
        Document document = new Document(pdf, PageSize.A4.rotate());
        ImageData imageData = ImageDataFactory.create("src/main/resources/img/header.png");
        com.itextpdf.layout.element.Image pdfImg= new com.itextpdf.layout.element.Image(imageData);
        Paragraph paragraph=new Paragraph("Receta");
        paragraph.setFontSize(25);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        document.setMargins(20, 20, 20, 20);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        Table table1 = new Table(UnitValue.createPercentArray(new float[]{5, 4,4}))
                .useAllAvailableWidth();
        processPdfReceta(table1, null, bold, true);
        for(modeloReceta e : consultaDAO.getmodeloReceta(estudiante.getNoUsuario())) {
            processPdfReceta(table1, e, bold, false);
        }
        document.add(pdfImg);
        document.add(paragraph);
        document.add(table1);
        //Close document
        document.close();
    }
    public void processPdfReceta(Table table, modeloReceta user, PdfFont font, boolean isHeader) {
        if (isHeader) {
            table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("No.Receta").setFont(font).setBackgroundColor(v_header2).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Indicaciones").setFont(font).setBackgroundColor(v_header2).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("No.Consulta").setFont(font).setBackgroundColor(v_header2).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
        } else {
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(user.getNoReceta() + "").setFont(font).setBackgroundColor(v_background2).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(user.getIndicaciones()+ "").setFont(font).setBackgroundColor(v_background2).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(user.getNoConsulta()+ "").setFont(font).setBackgroundColor(v_background2).setFontColor(v_font)).setTextAlignment(TextAlignment.CENTER));

        }
    }
    private void showSolicitud(ActionEvent event) throws IOException {
        Stage solicitud = new Stage();
        solicitud.setTitle("Solicitud");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/solicitudes.fxml"));
        Solicitud soli = new Solicitud();
        soli.setUsuario(estudiante);
        loader.setController(soli);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        solicitud.setResizable(false);
        // Le pasa como parametro el stage actual y nueva
        Stage actual = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        //encuesta.setStageAnterior(actual);
        //actual.close();
        // Muestra el nuevo stage
        solicitud.setScene(scene);
        //primaryStage.show();
        solicitud.initOwner(actual);
        solicitud.initModality(Modality.WINDOW_MODAL);
        solicitud.show();
    }
    private void alertMessage(String title, String Header, String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(Header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
    public void setUsuario(Usuario usuario){
        estudiante = usuario;
    }
    private void sendMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
    private void openPdfFile(String filename) {
        if (Desktop.isDesktopSupported()) {
            try { File myFile = new File(filename);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) { } } }
}
