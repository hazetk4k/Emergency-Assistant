package com.example.server.Service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

public class DocxToPdfConverter {

    public void convertDocxToPdf(String docxFilePath, String pdfFilePath) {
        try {

            FileInputStream fis = new FileInputStream(docxFilePath);
            XWPFDocument document = new XWPFDocument(fis);

            PDDocument pdfDocument = new PDDocument();
            PDPage page = new PDPage();
            pdfDocument.addPage(page);

            InputStream fontStream = new FileInputStream("D:\\University\\CourseProject\\Server\\src\\main\\resources\\css\\ofont.ru_Times New Roman.ttf");
            PDType0Font font = PDType0Font.load(pdfDocument, fontStream);
            fontStream.close();

            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.write(baos);
            String cleanText = baos.toString().replaceAll("[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F]", "");
            contentStream.setFont(font, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700); // Устанавливаем начальные координаты для текста в PDF
            contentStream.showText(cleanText);
            contentStream.endText();
            contentStream.close();

            pdfDocument.save(new File("D:\\University\\CourseProject\\Server\\src\\main\\resources\\reports_pdf\\" + pdfFilePath));
            pdfDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
