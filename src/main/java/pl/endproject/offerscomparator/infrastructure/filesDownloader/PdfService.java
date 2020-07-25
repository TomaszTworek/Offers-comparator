package pl.endproject.offerscomparator.infrastructure.filesDownloader;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import pl.endproject.offerscomparator.domain.Product;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.util.List;

@Service
public class PdfService extends FileDownloaderService {

    private File getFile(ServletContext servletContext) {
        String filepath = servletContext.getRealPath("/resources/templates");
        File file = new File(filepath);
        boolean exists = new File(filepath).exists();
        if (!exists) {
            new File(filepath).mkdirs();
        }
        return file;
    }

    public void createPdf(List<Product> products, ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) {
        Document document = new Document(PageSize.A4, 15, 15, 45, 30);
        try {
            File file = getFile(servletContext);
            PdfWriter.getInstance(document, new FileOutputStream(file + "/" + "getAll" + ".pdf"));
            document.open();

            Font font = FontFactory.getFont(FontFactory.TIMES_BOLD, 16, BaseColor.BLACK);

            Paragraph paragraph = new Paragraph("Wyniki wyszukiwania", font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);

            PdfPTable pdfPTable = new PdfPTable(5);
            pdfPTable.setWidthPercentage(100);
            pdfPTable.setSpacingBefore(5);
            pdfPTable.setSpacingAfter(5);
            float[] columnWidths = {2f, 2f, 2f, 2f, 2f};
            pdfPTable.setWidths(columnWidths);

            decorateFile(pdfPTable);
            fillPdfFile(pdfPTable, products);

            document.add(pdfPTable);
            document.close();

            String fullPath = request.getServletContext().getRealPath("/resources/templates/getAll" + ".pdf");
            fileDownload(fullPath, response, servletContext, "oferty.pdf");

        } catch (IOException | DocumentException ex) {
            ex.printStackTrace();
        }
    }

    private void decorateFile(PdfPTable pdfPTable) {
        PdfPCell image = new PdfPCell(new Phrase("Foto"));
        image.setBorderColor(BaseColor.BLACK);
        image.setPaddingLeft(10);
        image.setHorizontalAlignment(Element.ALIGN_CENTER);
        image.setVerticalAlignment(Element.ALIGN_CENTER);
        image.setBackgroundColor(BaseColor.LIGHT_GRAY);
        pdfPTable.addCell(image);

        PdfPCell title = new PdfPCell(new Phrase("Nazwa"));
        title.setBorderColor(BaseColor.BLACK);
        title.setPaddingLeft(10);
        title.setHorizontalAlignment(Element.ALIGN_CENTER);
        title.setVerticalAlignment(Element.ALIGN_CENTER);
        title.setBackgroundColor(BaseColor.LIGHT_GRAY);
        pdfPTable.addCell(title);


        PdfPCell price = new PdfPCell(new Phrase("Cena"));
        price.setBorderColor(BaseColor.BLACK);
        price.setPaddingLeft(10);
        price.setHorizontalAlignment(Element.ALIGN_CENTER);
        price.setVerticalAlignment(Element.ALIGN_CENTER);
        price.setBackgroundColor(BaseColor.LIGHT_GRAY);
        pdfPTable.addCell(price);


        PdfPCell source = new PdfPCell(new Phrase("Sklep"));
        source.setBorderColor(BaseColor.BLACK);
        source.setPaddingLeft(10);
        source.setHorizontalAlignment(Element.ALIGN_CENTER);
        source.setVerticalAlignment(Element.ALIGN_CENTER);
        source.setBackgroundColor(BaseColor.LIGHT_GRAY);
        pdfPTable.addCell(source);


        PdfPCell action = new PdfPCell(new Phrase("Link"));
        action.setBorderColor(BaseColor.BLACK);
        action.setPaddingLeft(10);
        action.setHorizontalAlignment(Element.ALIGN_CENTER);
        action.setVerticalAlignment(Element.ALIGN_CENTER);
        action.setBackgroundColor(BaseColor.LIGHT_GRAY);
        pdfPTable.addCell(action);
    }

    private void fillPdfFile(PdfPTable pdfPTable, List<Product> products) throws IOException, BadElementException {
        for (Product product : products) {
            PdfPCell imageValue;
            try {
               imageValue = new PdfPCell(Image.getInstance(product.getImageUrl()), true);
            }catch (IOException e){
                imageValue =new PdfPCell(Image.getInstance("src/main/resources/static/images/no-image.jpg"), true);
            }
            imageValue.setPaddingLeft(10);
            imageValue.setHorizontalAlignment(Element.ALIGN_CENTER);
            imageValue.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(imageValue);

            PdfPCell titleValue = new PdfPCell(new Phrase(product.getName()));
            titleValue.setBorderColor(BaseColor.BLACK);
            titleValue.setPaddingLeft(10);
            titleValue.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleValue.setVerticalAlignment(Element.ALIGN_CENTER);
            titleValue.setBackgroundColor(BaseColor.WHITE);
            pdfPTable.addCell(titleValue);

            PdfPCell priceValue = new PdfPCell(new Phrase(String.valueOf(product.getPrice())));
            priceValue.setBorderColor(BaseColor.BLACK);
            priceValue.setPaddingLeft(10);
            priceValue.setHorizontalAlignment(Element.ALIGN_CENTER);
            priceValue.setVerticalAlignment(Element.ALIGN_CENTER);
            priceValue.setBackgroundColor(BaseColor.WHITE);
            pdfPTable.addCell(priceValue);

            PdfPCell sourceValue = new PdfPCell(new Phrase(product.getSource().toString()));
            sourceValue.setBorderColor(BaseColor.BLACK);
            sourceValue.setPaddingLeft(10);
            sourceValue.setHorizontalAlignment(Element.ALIGN_CENTER);
            sourceValue.setVerticalAlignment(Element.ALIGN_CENTER);
            sourceValue.setBackgroundColor(BaseColor.WHITE);
            pdfPTable.addCell(sourceValue);

            PdfPCell actionValue = new PdfPCell(new Phrase(product.getUrl()));
            actionValue.setBorderColor(BaseColor.BLACK);
            actionValue.setPaddingLeft(10);
            actionValue.setHorizontalAlignment(Element.ALIGN_CENTER);
            actionValue.setVerticalAlignment(Element.ALIGN_CENTER);
            actionValue.setBackgroundColor(BaseColor.WHITE);
            pdfPTable.addCell(actionValue);
        }
    }
}
