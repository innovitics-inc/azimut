package innovitics.azimut.pdfgenerator;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.azure.storage.blob.models.BlobItem;
import com.lowagie.text.DocumentException;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.kyc.UserAnswer;

public interface PdfGenerateService {
	void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName,BusinessUser businessUser) throws IOException, DocumentException, BusinessException;
	void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) throws IOException;
	void downloadAndMergeLoop ()throws BusinessException, IOException;
	String downloadContract(List<UserAnswer> userAnswers,BusinessUser businessUser,List<String> solvedPages) throws BusinessException, IOException;
	List<BlobItem> listBlobItems ();
	
}
