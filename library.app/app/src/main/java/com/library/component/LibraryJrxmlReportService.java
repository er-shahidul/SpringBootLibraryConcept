package com.library.component;


import java.util.List;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import java.io.FileOutputStream;
import java.awt.image.BufferedImage;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * @author Shahidul Hasan
 * @developer Shahidul Hasan
 * class LibraryJrxmlReportService
 * Service
 */
@Service
public class LibraryJrxmlReportService {

	public LibraryJrxmlReportService() {
	}

	public void generatePdf(List<JasperPrint> jasperPrintArray, String fileName) throws JRException {

		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintArray));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fileName));
		try {
			exporter.exportReport();
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}

	public void generateImage(JasperPrint jasperPrint, String fileName, String extension) throws JRException {
		final float zoom = 1f;
		int pages = jasperPrint.getPages().size();

		for (int i = 0; i < pages; i++) {
			try(OutputStream out = new FileOutputStream(fileName)){
				BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, i,zoom);
				ImageIO.write(image, extension, out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void generateXlsx(JasperPrint jasperPrint, String fileName,String[] sheetName) throws JRException, IOException {
		JRXlsxExporter exporter = new JRXlsxExporter();
		SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
		reportConfigXLS.setSheetNames(sheetName);
		reportConfigXLS.setDetectCellType(true);
		reportConfigXLS.setCollapseRowSpan(false);
		exporter.setConfiguration(reportConfigXLS);
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fileName));
		exporter.exportReport();
	}

	public void generateDoc(JasperPrint jasperPrint, String fileName) throws JRException, IOException {
		JRDocxExporter exporter = new JRDocxExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fileName));
		exporter.exportReport();
	}

}