package com.example.server.Service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DocxToPdfConverter {

    public void convertDocxToPdf(String docxFilePath, String pdfFilePath) {
        try {
            FileInputStream fis = new FileInputStream(new File(docxFilePath));
            XWPFDocument document = new XWPFDocument(fis);

            PDDocument pdfDocument = new PDDocument();
            PDPage page = new PDPage();
            pdfDocument.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.write(baos);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700); // Устанавливаем начальные координаты для текста в PDF
            contentStream.showText(new String(baos.toByteArray()));
            contentStream.endText();
            contentStream.close();

            pdfDocument.save(new File("D:\\University\\CourseProject\\Server\\src\\main\\resources\\reports_pdf\\" + pdfFilePath));
            pdfDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
