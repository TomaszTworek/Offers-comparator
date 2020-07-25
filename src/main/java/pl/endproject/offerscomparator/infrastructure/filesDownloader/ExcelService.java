package pl.endproject.offerscomparator.infrastructure.filesDownloader;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import pl.endproject.offerscomparator.domain.Product;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService extends FileDownloaderService {

    private Cell cell;
    private Row row;

    private HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }


    public void generateExcel(List<Product> products, ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Offers comparator");

        int rownum = 0;
        HSSFCellStyle style = createStyleForTitle(workbook);

        row = sheet.createRow(rownum);

        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Nazwa");
        cell.setCellStyle(style);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Cena");
        cell.setCellStyle(style);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Sklep");
        cell.setCellStyle(style);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Link");
        cell.setCellStyle(style);

        fillExcelFile(products, rownum, sheet);
        getExcelFile(servletContext, request, response, workbook);

    }

    private void fillExcelFile(List<Product> products, int rownum, HSSFSheet sheet) {
        for (Product p : products) {
            rownum++;
            row = sheet.createRow(rownum);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(p.getName());

            cell = row.createCell(1, CellType.STRING);
            if (p.getPrice() == null) {
                cell.setCellValue("brak");
            } else {
                cell.setCellValue(p.getPrice());
            }

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(p.getSource().toString());

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(p.getUrl());
        }
    }

    private void getExcelFile(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response, HSSFWorkbook workbook) {
        try {
            String filepath = servletContext.getRealPath("/resources/templates");
            File file = new File(filepath);
            boolean exists = new File(filepath).exists();
            if (!exists) {
                new File(filepath).mkdirs();
            }
            FileOutputStream outFile = new FileOutputStream(file + "/" + "getAll" + ".xls");
            String fullPath = request.getServletContext().getRealPath("/resources/templates/getAll" + ".xls");
            workbook.write(outFile);
            fileDownload(fullPath, response, servletContext, "wyniki.xls");
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
