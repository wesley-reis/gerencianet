package br.com.project.report.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.util.JRLoader;

@SuppressWarnings("deprecation")
@Component
public class ReportUtil implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static final String UNDERLINE = "_";
	private static final String FOLDER_RELATORIOS = "/relatorios";
	private static final String SUBREPORT_DIR = "SUBREPORT_DIR";
	private static final String EXTENSION_ODS = "ods";
	private static final String EXTENSION_XLS = "xls";
	private static final String EXTENSION_HTML = "html";
	private static final String EXTENSION_PDF = "pdf";
	private static String SEPARATOR = File.separator;
	private static final int RELATORIO_PDF = 1;
	private static final int RELATORIO_EXCEL = 2;
	private static final int RELATORIO_HTML = 3;
	private static final int RELATORIO_PLANILHA_OPEN_OFFICE = 4;
	private static final String PONTO = ".";
	private StreamedContent arquivoRetorno = null;
	private String caminhoArquivoRelatorio = null;
	private JRExporter tipoArquivoExportado = null;
	private String extensaoArquivoExportado ="";
	private String caminhoSubreport_Dir = "";
	private File arquivoGerado = null;
	
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public StreamedContent geraRelatorio(List<?> listDataBeanColletionReport, HashMap parametrosRelatorio, String nomeRelatorioJasper, String nomeRelatorioSaida, int tipoRelatorio) throws JRException, FileNotFoundException {
		/*Cria a lista de collectionDataSource de beans que carregam os dados para o relat�rio*/
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listDataBeanColletionReport);

		/*
		 * Fornece o caminho fisico at� a pasta que contem os relat�rios
		 * compilador .jasper
		 */
		FacesContext context = FacesContext.getCurrentInstance();
		context.responseComplete();
		ServletContext scontext = (ServletContext) context.getExternalContext().getContext();

		String caminhoRelatorio = scontext.getRealPath(FOLDER_RELATORIOS);
		

		File file = new File(caminhoRelatorio + SEPARATOR + nomeRelatorioJasper	+ PONTO + "jasper");

		if (caminhoRelatorio == null || (caminhoRelatorio != null && caminhoRelatorio.isEmpty()) || !file.exists()) {
			caminhoRelatorio = this.getClass().getResource(FOLDER_RELATORIOS).getPath();
			SEPARATOR = "";
		}	
		
		/*caminho para imgens*/
		parametrosRelatorio.put("REPORT_PARAMETERS_IMG", caminhoRelatorio); 

		/* caminho completo at� o relat�rio compilado indicado */
		String caminhoArquivoJasper = caminhoRelatorio +  SEPARATOR + nomeRelatorioJasper + PONTO + "jasper";

		/* Faz o carregamento do relat�rio indicado. */
		JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(caminhoArquivoJasper);

		/* Seta param�tro SUBREPORT_DIR com o caminho fisico para sub-reports. */
		caminhoSubreport_Dir = caminhoRelatorio + SEPARATOR ;
		parametrosRelatorio.put(SUBREPORT_DIR, caminhoSubreport_Dir);

		/* Carrega o arquivo compilado para a m�moria. */
		JasperPrint impressoraJasper = JasperFillManager.fillReport(relatorioJasper, parametrosRelatorio, jrbcds);

		switch (tipoRelatorio) {/* Realiza a exporta��o para o tipo indicado. */
		case ReportUtil.RELATORIO_PDF:
			tipoArquivoExportado = new JRPdfExporter();
			extensaoArquivoExportado = EXTENSION_PDF;
			break;
		case ReportUtil.RELATORIO_HTML:
			tipoArquivoExportado = new JRHtmlExporter();
			extensaoArquivoExportado = EXTENSION_HTML;
			break;
		case ReportUtil.RELATORIO_EXCEL:
			tipoArquivoExportado = new JRXlsExporter();
			extensaoArquivoExportado = EXTENSION_XLS;
			break;
		case ReportUtil.RELATORIO_PLANILHA_OPEN_OFFICE:
			tipoArquivoExportado = new JROdtExporter();
			extensaoArquivoExportado = EXTENSION_ODS;
			break;
		default:
			tipoArquivoExportado = new JRPdfExporter();
			extensaoArquivoExportado = EXTENSION_PDF;
			break;
		}

		nomeRelatorioSaida += UNDERLINE + DateUtils.getDateAtualReportName();

		/* Caminho relat�rio exportado */
		caminhoArquivoRelatorio = caminhoRelatorio + SEPARATOR + nomeRelatorioSaida + PONTO + extensaoArquivoExportado;

		/* Cria novo File exportado */
		arquivoGerado = new File(caminhoArquivoRelatorio);

		/* Prepara a impress�o */
		tipoArquivoExportado.setParameter(JRExporterParameter.JASPER_PRINT,	impressoraJasper);

		/* Nome do arquivo fisico a ser impresso/exportado */
		tipoArquivoExportado.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);

		/* Executa a exporta��o */
		tipoArquivoExportado.exportReport();

		/* Remove o arquivo do servidor ap�s ser feito o download pelo usu�rio */
		arquivoGerado.deleteOnExit();
		
		/* Cria o InputStream para ser usado para pelo PrimeFaces */
		InputStream conteudoRelatorio = new FileInputStream(arquivoGerado);
		
		/* Faz o retorno para a aplica��o. */
		arquivoRetorno = new DefaultStreamedContent(conteudoRelatorio, "application/" + extensaoArquivoExportado, nomeRelatorioSaida + PONTO + extensaoArquivoExportado);
		return arquivoRetorno;
	}


}
