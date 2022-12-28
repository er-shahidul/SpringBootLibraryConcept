package com.library.component;


import java.io.*;
import java.util.Random;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import reactor.core.publisher.Mono;
import com.library.qrClasses.Builder;
import reactor.core.scheduler.Schedulers;
import java.time.format.DateTimeFormatter;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.FileSystemResource;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.engine.JasperExportManager;
import org.apache.commons.io.output.ByteArrayOutputStream;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.springframework.transaction.annotation.Transactional;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

/**
 * @author Shahidul Hasan
 * @developer Shahidul Hasan
 * class LibraryBaseService
 * Service
 */
@Slf4j
@Service
@Transactional
public class LibraryBaseService {

    public String getLuhnChar(String terminalId)
    {
        int nDigits = terminalId.length();
        int nSum = 0;
        boolean isSecond = true;
        for (int i = nDigits - 1; i >= 0; i--)
        {
            int d = terminalId.charAt(i) - '0';
            if (isSecond == true) d = d * 2;

            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        return String.valueOf((10-(nSum % 10))%10);
    }

    public String padLeft(String inStr, int lngt) {
        while (inStr.length() < lngt) {
            inStr = "0" + inStr;
        }

        return inStr;
    }

    public String padLeftLong(String inStr, Long lngt) {
        while (inStr.length() < lngt) {
            inStr = "0" + inStr;
        }

        return inStr;
    }

    public DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LocalDateTime getLocalDateTimeFromString(String dateTimeString) throws JRException {
        return LocalDateTime.parse(dateTimeString, dateTimeFormatter);
    }

    public static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(9999999);
        return String.format("%06d", number);
    }

    public void qrPngCreator(Builder qr, Path qrPngPathValue) {
        File QRlpeg = new File(qrPngPathValue.toString());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(QRlpeg);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            fos.write(qr.getQRBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            fos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<byte[]> imageView(String imageLocation, MediaType mediaType) {
        File img = new File(imageLocation);
        try {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(Files.readAllBytes(img.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MediaType getMediaType(String name) {
        String extension = "";
        int index = name.lastIndexOf('.');
        if (index > 0) {
            extension = name.substring(index);
        }
        return extension.equals("png")? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG;
    }

    public Mono<ResponseEntity<Resource>> downloadFile(String location){
        return Mono.just(location).flatMap(t -> Mono.<Resource>fromCallable(() -> {
                    String path = Paths.get(location).toAbsolutePath().normalize().toString();
                    return new FileSystemResource(path);
                })
                .subscribeOn(Schedulers.boundedElastic()).<ResponseEntity<Resource>>flatMap(resource -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentDispositionFormData(location, location);
                    return Mono.just(ResponseEntity
                            .ok().cacheControl(CacheControl.noCache())
                            .headers(headers)
                            .body(resource));
                }));
    }

    public byte[] getJasperXlsxData(final JasperPrint jp) throws JRException, IOException {
        JRXlsxExporter xlsxExporter = new JRXlsxExporter();
        final byte[] rawBytes;

        try(org.apache.commons.io.output.ByteArrayOutputStream xlsReport = new ByteArrayOutputStream()){
            xlsxExporter.setExporterInput(new SimpleExporterInput(jp));
            xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
            xlsxExporter.exportReport();

            rawBytes = xlsReport.toByteArray();
        }
        return rawBytes;
    }

    public Mono<HttpEntity<byte[]>> downloadXlsx(String fileName, byte[] data) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+ fileName +".xlsx");
        header.setContentLength(data.length);

        return Mono.just(new HttpEntity<byte[]>(data, header));
    }

    public byte[] getJasperPdfData(final JasperPrint jp) throws JRException {
        return JasperExportManager.exportReportToPdf(jp);
    }

    public Mono<HttpEntity<byte[]>> downloadPdf(String fileName, byte[] data) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.parseMediaType(APPLICATION_PDF_VALUE));
        header.set(HttpHeaders.CONTENT_DISPOSITION,fileName);
        header.setContentLength(data.length);

        return Mono.just(new HttpEntity<byte[]>(data, header));
    }
}
