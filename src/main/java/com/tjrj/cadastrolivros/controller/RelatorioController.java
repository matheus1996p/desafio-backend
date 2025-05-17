package com.tjrj.cadastrolivros.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tjrj.cadastrolivros.entity.LivroAutorView;
import com.tjrj.cadastrolivros.repository.LivroAutorViewRepository;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private LivroAutorViewRepository repository;

    @GetMapping
    public List<LivroAutorView> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/exportar")
    public ResponseEntity<byte[]> exportarPdf() throws Exception {
        try {
            List<LivroAutorView> dados = repository.findAll();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dados);

            InputStream jasperStream = new ClassPathResource("relatorio_livros.jasper").getInputStream();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, new HashMap<>(), dataSource);
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_livros.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(("Erro ao exportar PDF: " + e.getMessage()).getBytes());
        }
    }
}
