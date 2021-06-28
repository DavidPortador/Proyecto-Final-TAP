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
import javafx.stage.Stage;

import java.io.IOException;
public class PDFreports {
    UserDAO userDAO = new UserDAO(MySQLConnection.getConnection());
    Stage anterior;
    Color v_backG = new DeviceRgb(0, 142, 0);
    Color v_font = new DeviceRgb(0, 0, 0);
    Color v_backG2= new DeviceRgb(251,113,112);
    public void createPdfCasosCarrera(String dest, String p_indicador) throws IOException {
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
        /*for(UserDAO e : userDAO.getList(p_indicador)) {
            process(table, e, bold, false);
        }*/
        document.add(table);
        //Close document
        document.close();
    }
    public void processPdfCarrera(Table table, UserDAO emp, PdfFont font, boolean isHeader) {
        if (isHeader) {
            table.addHeaderCell(new Cell().add(new Paragraph("EMP. NO.").setFont(font).setBackgroundColor(v_backG).setFontColor(v_font)));
            table.addHeaderCell(new Cell().add(new Paragraph("NAME").setFont(font).setBackgroundColor(v_backG).setFontColor(v_font)));
        } else {
            // table.addCell(new Cell().add(new Paragraph(emp.getEmp_no() + "").setFont(font).setBackgroundColor(v_backG2).setFontColor(v_font)));
            // table.addCell(new Cell().add(new Paragraph(emp.getFirst_name() + " " + emp.getLast_name()).setFont(font).setBackgroundColor(v_backG2).setFontColor(v_font)));
        }
    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
}

