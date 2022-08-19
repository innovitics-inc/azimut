package innovitics.azimut.pdfgenerator;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PdfGenerateService {
	void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName);
}
