package pl.endproject.offerscomparator.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.endproject.offerscomparator.domain.Product;
import pl.endproject.offerscomparator.infrastructure.filesDownloader.ExcelService;
import pl.endproject.offerscomparator.infrastructure.filesDownloader.PdfService;
import pl.endproject.offerscomparator.infrastructure.memoryCash.MemoryCash;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;


@Controller
public class DownloadFilesController {
    private ExcelService excelService;
    private PdfService pdfService;
    private ServletContext servletContext;

    @Autowired
    private  MemoryCash memoryCash;

    public DownloadFilesController(ExcelService excelService, PdfService pdfService, ServletContext servletContext) {
        this.excelService = excelService;
        this.pdfService = pdfService;
        this.servletContext = servletContext;

    }

    @RequestMapping("/printPdf/{id}") // id from list
    @ResponseBody
    public void getPdfFile(HttpServletRequest request, HttpServletResponse response, HttpSession session, @PathVariable("id") int id) {
//        List<Product> products = (List<Product>) session.getAttribute("products");

       id = memoryCash.getId();


        List<Product> byId = memoryCash.findById(id);//szuka po Id

        pdfService.createPdf(byId, servletContext, request, response);
    }

    @RequestMapping("/printExcel")
    @ResponseBody
    public void getExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        List<Product> products = (List<Product>) session.getAttribute("products");
        excelService.generateExcel(products, servletContext, request, response);
    }

}
