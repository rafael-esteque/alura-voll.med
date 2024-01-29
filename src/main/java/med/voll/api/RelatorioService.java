package med.voll.api;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.paciente.Paciente;
import med.voll.api.paciente.PacienteRepository;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public void generateExcel(HttpServletResponse response) throws IOException {

        List<Paciente> pacientes = pacienteRepository.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Pacientes Info");
        HSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Nome");
        row.createCell(2).setCellValue("Email");
        row.createCell(3).setCellValue("Telefone");

        int dataRowIndex = 1;
        for (Paciente paciente:pacientes) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(paciente.getId());
            dataRow.createCell(1).setCellValue(paciente.getNome());
            dataRow.createCell(2).setCellValue(paciente.getEmail());
            dataRow.createCell(3).setCellValue(paciente.getTelefone());
            dataRowIndex ++;
        }

        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();

    }
}
