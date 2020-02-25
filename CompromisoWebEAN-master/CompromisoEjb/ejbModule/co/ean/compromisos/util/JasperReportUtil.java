package co.ean.compromisos.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBean.CollectionDataSource;

import org.apache.log4j.Logger;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import net.sf.jasperreports.engine.design.JRDesignDataset;

public class JasperReportUtil {

	private static Logger logger = Logger.getLogger(JasperReportUtil.class);

	public static ByteArrayOutputStream getOutputStreamFromReport(Map map, String pathJasper) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		// JRBean.CollectionDataSource dataSource = new
		// JRBean.CollectionDataSource(list);

		try {

			JasperPrint jp = JasperFillManager.fillReport(pathJasper, map, new JREmptyDataSource());

			JasperExportManager.exportReportToPdfStream(jp, os);
			os.flush();
			os.close();

		} catch (Exception e) {
			logger.error("se presento un error mètodo getOutputStreamFromReport... " + e.getMessage(), e);
		}
		return os;
	}

	public static StreamedContent getStreamContentFromOutputStream(ByteArrayOutputStream os, String contentType,
			String nameFile) {
		StreamedContent file = null;
		try {

			InputStream is = new ByteArrayInputStream(os.toByteArray());
			file = new DefaultStreamedContent(is, contentType, nameFile);

		} catch (Exception e) {
			logger.error("se presento un error mètodo getStreamContentFromOutputStream... " + e.getMessage(), e);
		}
		return file;
	}

	public static StreamedContent getStreamContentReport(Map<String, Object> map, String pathJasper, String nameFilePdf)
			throws Exception {
		StreamedContent pdf = null;
		// JRBean.CollectionDataSource dataSource = new
		// JRBean.CollectionDataSource(list);

		JasperPrint jp = JasperFillManager.fillReport(pathJasper, map, new JREmptyDataSource());

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		JasperExportManager.exportReportToPdfStream(jp, os);
		os.flush();
		os.close();

		InputStream is = new ByteArrayInputStream(os.toByteArray());
		pdf = new DefaultStreamedContent(is, "application/pdf", nameFilePdf);
		return pdf;
	}
}
