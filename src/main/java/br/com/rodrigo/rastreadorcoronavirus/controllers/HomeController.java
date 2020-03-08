package br.com.rodrigo.rastreadorcoronavirus.controllers;

import br.com.rodrigo.rastreadorcoronavirus.models.LocalEstado;
import br.com.rodrigo.rastreadorcoronavirus.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        //metodo para pegar os casos atuais, anteriores e somar
        List<LocalEstado> localEstado = coronaVirusDataService.getTodosEstados();
        int totalCasosReportados = localEstado.stream().mapToInt(est -> est.getTotalCasosRecentes()).sum();
        int totalNovosCasos = localEstado.stream().mapToInt(est -> est.getDiferencaDiaAnterior()).sum();
        model.addAttribute("localEstado", localEstado);
        model.addAttribute("totalCasosReportados", totalCasosReportados);
        model.addAttribute("totalNovosCasos", totalNovosCasos);

        return "home";
    }
}
