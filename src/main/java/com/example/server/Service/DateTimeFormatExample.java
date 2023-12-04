package com.example.server.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatExample {

    public String dateTimeChange(String inputDateTime){

        // Создаем DateTimeFormatter для существующего формата
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

        // Парсим строку в LocalDateTime
        LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, inputFormatter);

        // Создаем новый формат даты и времени для вывода
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd:MM:yyyy, HH:mm");

        // Преобразуем LocalDateTime в строку с новым форматом

        return (dateTime.format(outputFormatter));
    }
}
