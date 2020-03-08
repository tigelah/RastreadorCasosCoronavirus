package br.com.rodrigo.rastreadorcoronavirus.services;

import br.com.rodrigo.rastreadorcoronavirus.models.LocalEstado;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CoronaVirusDataService {

    private static String DADOS_VIRUS_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

    private List<LocalEstado> todosEstados = new ArrayList<>();

    public List<LocalEstado> getTodosEstados() {
        return todosEstados;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocalEstado> novosEstados = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(DADOS_VIRUS_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        log.info("Escrevendo na pagina ...");
        for (CSVRecord record : records) {
            LocalEstado localEstado = new LocalEstado();
            localEstado.setEstado(record.get("Province/State"));
            localEstado.setPais(record.get("Country/Region"));
            int ultimosCasos = Integer.parseInt(record.get(record.size() - 1));
            int casosDiaAnterior = Integer.parseInt(record.get(record.size() - 2));
            localEstado.setTotalCasosRecentes(ultimosCasos);
            localEstado.setDiferencaDiaAnterior(ultimosCasos - casosDiaAnterior);
            novosEstados.add(localEstado);
        }
        this.todosEstados = novosEstados;
        log.info("Finalizando escrita ...");
    }


}
