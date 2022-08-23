package innovitics.azimut.pdfgenerator;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.exceptions.BusinessException;

public interface PdfGenerateService {
	void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) throws IOException;
	void downloadAndMerge ();
	void downloadAndMergeLoop ()throws BusinessException, IOException;
}
