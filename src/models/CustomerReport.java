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
import sun.misc.IOUtils;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CustomerReport {



    public static void createPdf(Customer customer) {
        ArrayList<Date> customerDates = BBDD.datesbyCustomer(customer.getDni());
        Collections.sort(customerDates);
        PdfWriter writer = null;

        try {
            writer = new PdfWriter("Informe"+customer.getDni()+".pdf");
  //          writer = new PdfWriter("Informe.pdf");

        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);




        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        doc.add(new Paragraph("Informe de citas de: "+customer.getName()).setFont(bold));

            doc.add(new Paragraph("DNI: "+customer.getDni()).setFont(font));
            doc.add(new Paragraph("Fecha de nacimiento: "+customer.getBirthDate()).setFont(font));
            doc.add(new Paragraph("Genero: "+customer.getGender()).setFont(font));
            doc.add(new Paragraph("Tutor Legal: "+customer.getGuardian()).setFont(font));
            doc.add(new Paragraph("Email: "+customer.getEmail()).setFont(font));
            doc.add(new Paragraph("Teléfono: "+customer.getPhone()).setFont(font));
            doc.add(new Paragraph("Contacto alternativo: "+customer.getAltContact()).setFont(font));
            doc.add(new Paragraph("Teléfono alternativo: "+customer.getAltPhone()).setFont(font));
            doc.add(new Paragraph("RGPD: "+customer.isRgpd()).setFont(font));
            doc.add(new Paragraph("Colegio:"+customer.getSchool()).setFont(font));
            doc.add(new Paragraph("Curso:"+customer.getCourse()).setFont(font));
            doc.add(new Paragraph("Derivado de:\n"+customer.getDerivedFrom()).setFont(font));
            doc.add(new Paragraph("Nos conoce por:\n"+customer.getKnowUsFor()).setFont(font));
            doc.add(new Paragraph(""));

            Table table = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();

            for (Date date: customerDates){
                String formattedDate = date.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String dateDay;
                String text4;
                String color;
                if(date.getWeekly()){
                    dateDay=date.getWeekDay();
                    text4="Inicio:"+date.getDate()+"\n"+"Final:"+date.getDateEnd();
                }else{
                    dateDay=formattedDate;
                    text4=date.getWeekDay();
                }
                table.addCell(dateDay);
                table.addCell(text4);
                table.addCell(date.getStartTime()+" // "+date.getFinishTime());
                table.addCell(date.getDepartmentName());

            }


            doc.add(table);

        doc.close();
        } catch (Exception e) {
        e.printStackTrace();
        }


    }
}
