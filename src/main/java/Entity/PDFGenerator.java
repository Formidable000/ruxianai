package Entity;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFGenerator {
    public static String generateDiagnosisReport(List<DiagnosisResult> diagnosisResults, String username) throws DocumentException, IOException {
        Document document = new Document();
        String pdfPath = "G:\\Scode\\getpdf\\" + username + ".pdf"; // Modify this path as necessary
        PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
        document.open();

        document.add(new Paragraph("Diagnosis Report"));
        document.add(new Paragraph("Username: " + username));

        for (DiagnosisResult result : diagnosisResults) {
            document.add(new Paragraph("Image: " + result.getImageName()));
            document.add(new Paragraph("Diagnosis: " + result.getDiagnosis()));
            document.add(new Paragraph(" "));  // Adding empty line
        }

        document.close();
        return pdfPath;
    }
}