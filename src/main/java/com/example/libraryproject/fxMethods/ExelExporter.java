package com.example.libraryproject.fxMethods;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;


public class ExelExporter {
    public void exportToExcel(TableView table, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        ObservableList<TableColumn<?, ?>> columns = table.getColumns();
        ObservableList<?> items = table.getItems();

        // Создаем заголовки столбцов
        Row header = sheet.createRow(0);
        for (int i = 0; i < columns.size(); i++) {
            header.createCell(i).setCellValue(columns.get(i).getText());
        }

        // Заполняем данные
        for (int i = 0; i < items.size(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < columns.size(); j++) {
                TableColumn<?, ?> column = columns.get(j);
                if (column.getCellData(i) != null) {
                    row.createCell(j).setCellValue(column.getCellData(i).toString());
                } else {
                    row.createCell(j).setCellValue("");
                }
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            Toast.makeToast((Stage) table.getScene().getWindow(), "Таблица успешно выгружена!", Duration.seconds(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
