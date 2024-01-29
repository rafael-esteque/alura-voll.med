package med.voll.api.controller;

import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RelatorioRestController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/excel")
    public void generateExcelReport(HttpServletResponse response) throws Exception{

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=pacientes.xls";

        response.setHeader(headerKey, headerValue);

        relatorioService.generateExcel(response);
    }
}
