package innovitics.azimut.pdfgenerator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.fileutilities.BlobData;
import innovitics.azimut.utilities.fileutilities.BlobFileUtility;

@Service
public class PdfGenerateServiceImpl implements PdfGenerateService {
    private Logger logger = LoggerFactory.getLogger(PdfGenerateServiceImpl.class);

    @Autowired ConfigProperties  configProperties;
    @Autowired private TemplateEngine templateEngine;
    @Autowired BlobFileUtility blobFileUtility;

    @Override
    public void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName,BusinessUser businessUser) throws IOException {
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);

        	
/*        	final File outputFile = File.createTempFile(pdfFileName,".pdf");
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);            
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(fileOutputStream, false);
            renderer.finishPDF();            
            FileInputStream fileInputStream=new FileInputStream(outputFile);                       
            this.logger.info("File Path:::::::::::::::::::"+outputFile.getAbsolutePath());
            ByteArrayOutputStream baos=new ByteArrayOutputStream (); 
            outputFile.delete();
*/           
        
         	try {
            	ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream (); 
                ITextRenderer renderer = new ITextRenderer();
                renderer.setDocumentFromString(htmlContent);
                renderer.layout(); 
    			renderer.createPDF(byteArrayOutputStream, false);
    		    renderer.finishPDF();   
				this.blobFileUtility.uploadFileToBlob(byteArrayOutputStream.toByteArray(), true, this.configProperties.getBlobKYCDocuments(), "userContracts/"+businessUser.getUserId(),pdfFileName, "pdf");
			} catch (BusinessException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
            
            	
        	
        
        
    }
	@Override
	public void downloadAndMerge() {
		try 
		
		{
			 /*
			 
			 PDFMergerUtility ut = new PDFMergerUtility();
			 ut.addSource(this.blobFileUtility.downloadFileToBlob(this.configProperties.getBlobKYCDocuments(), "userAnswers/"+DateUtility.getCurrentDayMonthYear(), "8421e66b-2ffb-4b9d-a73f-7974a5d9479a.pdf"));
			 ut.addSource(this.blobFileUtility.downloadFileToBlob(this.configProperties.getBlobKYCDocuments(), "userAnswers/"+DateUtility.getCurrentDayMonthYear(), "57df0465-60cb-420d-af2a-ca028a62217c.pdf"));
			 ut.setDestinationFileName("E:\\Backend Team\\Azimut\\new");
			 ut.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());			 			 			
			 */

			ByteArrayOutputStream byteArrayOutputStream1=this.blobFileUtility.downloadStreamFromBlob(this.configProperties.getBlobKYCDocuments(), "userAnswers/"+"21-8-2022", "8421e66b-2ffb-4b9d-a73f-7974a5d9479a.pdf");
			ByteArrayOutputStream byteArrayOutputStream2=this.blobFileUtility.downloadStreamFromBlob(this.configProperties.getBlobKYCDocuments(), "userAnswers/"+"21-8-2022", "57df0465-60cb-420d-af2a-ca028a62217c.pdf");
			ByteArrayOutputStream byteArrayOutputStream3=new ByteArrayOutputStream();
			PDFMergerUtility pdfMergerUtility=new PDFMergerUtility();
			pdfMergerUtility.addSource(new ByteArrayInputStream(byteArrayOutputStream1.toByteArray()));
			pdfMergerUtility.addSource(new ByteArrayInputStream(byteArrayOutputStream2.toByteArray()));
			pdfMergerUtility.setDestinationStream(byteArrayOutputStream3);
			pdfMergerUtility.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
			this.blobFileUtility.uploadFileToBlob(byteArrayOutputStream3.toByteArray(), true, this.configProperties.getBlobKYCDocuments(), "userAnswers/"+DateUtility.getCurrentDayMonthYear(),"contract", "pdf");
			byteArrayOutputStream3.close();
	        
		} 
		catch (BusinessException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		
	}
	@Override
	public void downloadAndMergeLoop() throws BusinessException, IOException {
		PDFMergerUtility pdfMergerUtility=new PDFMergerUtility();
		ByteArrayOutputStream byteArrayOutputStream3=new ByteArrayOutputStream();
		pdfMergerUtility.setDestinationStream(byteArrayOutputStream3);
		for(int i=0;i<23;i++)
		{
			ByteArrayOutputStream byteArrayOutputStream=this.blobFileUtility.downloadStreamFromBlob(this.configProperties.getBlobKYCDocuments(), "userAnswers/"+"21-8-2022", "8421e66b-2ffb-4b9d-a73f-7974a5d9479a.pdf");
			pdfMergerUtility.addSource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
		}
		
		pdfMergerUtility.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
		this.blobFileUtility.uploadFileToBlob(byteArrayOutputStream3.toByteArray(), true, this.configProperties.getBlobKYCDocuments(), "userAnswers/"+DateUtility.getCurrentDayMonthYear(),"contract", "pdf");
		byteArrayOutputStream3.close();
		
	}
	
 
}