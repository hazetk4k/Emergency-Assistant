package com.example.server.Service;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class WordReportGenerator {

    public void generateReport(Map<String, String> reportData) {
        XWPFDocument document = new XWPFDocument();

        // Создаем заголовок документа
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun titleRun = title.createRun();
        titleRun.setText("Отчет по заявлению №" + reportData.get("Num"));
        titleRun.setBold(true);
        titleRun.setFontFamily("Times New Roman");
        titleRun.setFontSize(16);

        // Генерируем текст абзацев
        generateBoldParagraph(document, "Общие сведения о заявлении:");
        generateParagraph(document, "Дата и время получения заявления: " + reportData.get("recieved_date_time") + ".");
        generateParagraph(document, "Дата и время завершения процесса приема заявления: " + reportData.get("end_up_datetime") + ".");
        generateParagraph(document, "Указанный заявителем тип происшествия: " + reportData.get("Type") + ".");
        generateParagraph(document, "");
        generateBoldParagraph(document, "1. Определение характеристик чрезвычайной ситуации и незамедлительный вызов необходимых служб.");

        generateParagraph(document, "Характер ситуации определен диспетчером как \"ЧС " + reportData.get("Char") + "\". ");
        generateParagraph(document, "Вид чрезвычайной ситуации определен как \"" + reportData.get("Kind") + "\". ");
        generateParagraph(document, "Указанный адрес, где произошла ЧС: " + reportData.get("place") + ". ");
        generateParagraph(document, "Указанные дата и время происшествия: " + reportData.get("timestamp") + ". ");
        generateParagraph(document, "");
        generateParagraph(document, "Диспетчером были вызваны следующие службы: " + reportData.get("services").replace("[", "").replace("]", "") + ".");
        generateParagraph(document, "");
        generateBoldParagraph(document, "2. Получение персональных данных заявителя и предоставление соответствующих рекомендаций.");

        generateParagraph(document, "Данные заявителя:");
        generateParagraph(document, "ФИО заявителя: " + reportData.get("fio"));
        generateParagraph(document, "Место жительства: " + reportData.get("address") + ". ");
        generateParagraph(document, "Електронная почта: " + reportData.get("email") + ". ");
        generateParagraph(document, "");
        generateParagraph(document, "Диспетчером выдана следующая рекомендация для заявителя: " + reportData.get("recommendations"));
        generateParagraph(document, "");
        generateBoldParagraph(document, "3. Предоставление информации службам, направленным на ликвидацию чрезвычайной ситуации, посредством радиосвязи.");

        generateParagraph(document, "Пострдавшие: " + reportData.get("areThereCasualities") + ". ");
        if (!Objects.equals(reportData.get("areThereCasualities"), "Отсутствуют")) {
            generateParagraph(document, "Указанное количество пострадавших: " + reportData.get("amountOfCasualities"));
        }
        generateParagraph(document, "Также переданы дополнительные сведения от заявителя: " + reportData.get("additionalData"));
        generateParagraph(document, "");
        generateParagraph(document, "Отчет составлен диспетчером, владеющим учетной записью системы под логином \"" + reportData.get("логин") + "\".");
        saveDocument(document, reportData.get("Num"));
    }

    private void generateParagraph(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        paragraph.setSpacingAfterLines(0);
        run.setFontFamily("Times New Roman");
        run.setFontSize(14);
        run.setText(text);
        setParagraphProperties(paragraph);
    }

    private void generateBoldParagraph(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        // Устанавливаем текст как жирный
        run.setBold(true);
        run.setFontFamily("Times New Roman");
        run.setFontSize(14);
        run.setText(text);
        setFirstLineIndent(paragraph);
    }

    private void setFirstLineIndent(XWPFParagraph paragraph) {
        paragraph.setIndentationFirstLine(440);
    }
    private void setParagraphProperties(XWPFParagraph paragraph) {
        paragraph.setAlignment(ParagraphAlignment.BOTH);
        paragraph.setSpacingAfterLines(-1);
    }

    private void saveDocument(XWPFDocument document, String num) {
        try (FileOutputStream out = new FileOutputStream("D:\\University\\CourseProject\\Server\\src\\main\\resources\\reports\\" + "Заявление №" + num + ".docx")) {
            document.write(out);
            System.out.println("Отчет успешно создан!");
        } catch (IOException e) {
            System.out.println("Ошибка при создании отчета: " + e.getMessage());
        }
    }

}
