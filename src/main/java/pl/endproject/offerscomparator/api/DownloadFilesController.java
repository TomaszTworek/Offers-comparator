package pl.endproject.offerscomparator.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.endproject.offerscomparator.domain.Product;
import pl.endproject.offerscomparator.infrastructure.filesDownloader.ExcelService;
import pl.endproject.offerscomparator.infrastructure.filesDownloader.PdfService;
import pl.endproject.offerscomparator.infrastructure.memoryCache.MemoryCache;

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
    private MemoryCache memoryCache;

    public DownloadFilesController(ExcelService excelService, PdfService pdfService, ServletContext servletContext) {
        this.excelService = excelService;
        this.pdfService = pdfService;
        this.servletContext = servletContext;

    }

    @RequestMapping("/printPdf/{id}")
    @ResponseBody
    public void getPdfFile(HttpServletRequest request, HttpServletResponse response, HttpSession session, @PathVariable("id") int id) {

        List<Product> byId = memoryCache.findById(id);

        pdfService.createPdf(byId, servletContext, request, response);
    }

    @RequestMapping("/printExcel/{id}")
    @ResponseBody
    public void getExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session,@PathVariable("id") int id) {

        List<Product> byId = memoryCache.findById(id);
        excelService.generateExcel(byId, servletContext, request, response);
    }

}
