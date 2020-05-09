package models;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class DepartmentReport {



    public static void createPdf(Department department) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        ArrayList<Date> departmentDates = BBDD.datesbyDepartment(department.getId());
        Collections.sort(departmentDates);
        PdfWriter writer = null;

        try {
            writer = new PdfWriter("Informe"+department.getName()+".pdf");

        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);




        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        doc.add(new Paragraph("Informe de citas de: "+department.getName()).setFont(bold));

            doc.add(new Paragraph("Asigando a: "+department.getAssignedName()).setFont(font));
            doc.add(new Paragraph("Suplente: "+department.getAssignedName2()).setFont(font));
            doc.add(new Paragraph("\n"));

            Table table = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();

            for (Date date: departmentDates){
                String dateDay;
                String text4;
                if(date.getWeekly()){
                    dateDay=date.getWeekDay();
                    text4="Inicio: "+date.getDate().format(formatter)+"\n"+"Final: "+date.getDateEnd().format(formatter);
                }else{
                    dateDay=date.getDate().format(formatter);
                    text4=date.getWeekDay();
                }
                table.addCell(dateDay);
                table.addCell(text4);
                table.addCell(date.getStartTime()+" // "+date.getFinishTime());
                table.addCell(date.getCustomerName());

            }


            doc.add(table);

        doc.close();
        } catch (Exception e) {
        e.printStackTrace();
        }


    }
}
