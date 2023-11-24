package com.example.server.Service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType3Font;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ReportGenerator {

    public void generateReport(Map<String, String> data, String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream cS = new PDPageContentStream(document, page);
            PDType0Font font = PDType0Font.load(document, new File("D:\\University\\CourseProject\\Server\\src\\main\\resources\\css\\ofont.ru_Times New Roman.ttf"));

            String headerText = "Заявление № " + data.get("Num");
            String receivedDateTime = data.get("recieved_date_time");
            String type = data.get("Type");
            String sectionHeader1 = "1. Определение характеристик чрезвычайной ситуации и незамедлительный вызов необходимых служб.";
            String kind = data.get("cmbBoxKinds");
            String nature = data.get("ChoiceBoxChar");
            String place = data.get("place");
            String dateTime = data.get("received_date_time");
            String services = data.get("services");
            String paragraphText = "Вид и характер определены диспетчером как " + kind + " и " + nature + " соответственно. Указанный адрес, где произошла ЧС: " + place + ". "
                    + "Указанные дата и время происшествия: " + dateTime + ". Диспетчером были вызваны следующие службы: " + services + ".";

            String sectionHeader2 = "2. Получение персональных данных заявителя и предоставление соответствующих рекомендаций.";
            String fullName = data.get("fio");
            String homeAddress = data.get("address");
            String email = data.get("email");
            String recommendation = data.get("recommendations");

            String casualties = data.get("areThereCasualities");
            String amount = data.get("amountOfCasualities");
            String danger = data.get("isUserInDanger");
            String additionalData = data.get("additionalData");

            cS.beginText();
            cS.setFont(font, 16);
            cS.setLeading(16 * 1.15f);

            float headerTextWidth = font.getStringWidth(headerText) / 1000 * 16;
            float startX = (page.getMediaBox().getWidth() - headerTextWidth) / 2;
            float startY = page.getMediaBox().getHeight() - 40;

            cS.newLineAtOffset(startX, startY);
            cS.showText(headerText);

            cS.newLine();
            cS.newLine();

            cS.setFont(font, 14);


            cS.newLineAtOffset(-60, 0);
            cS.showText("Текст нового абзаца");

            cS.endText();
            cS.close();

            cS.close();
            document.save(filePath);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
