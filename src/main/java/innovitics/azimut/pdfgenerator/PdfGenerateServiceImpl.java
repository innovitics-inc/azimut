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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobItem;
import com.lowagie.text.DocumentException;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.kyc.UserAnswer;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.fileutilities.BlobData;
import innovitics.azimut.utilities.fileutilities.BlobFileUtility;

@Service
public class PdfGenerateServiceImpl implements PdfGenerateService {
    private Logger logger = LoggerFactory.getLogger(PdfGenerateServiceImpl.class);

    @Autowired ConfigProperties  configProperties;
    @Autowired private TemplateEngine templateEngine;
    @Autowired private  BlobFileUtility blobFileUtility;
    @Autowired private ListUtility<BlobItem> blobItemListUtility; 

    @Override
    public void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName,BusinessUser businessUser) throws IOException, DocumentException, BusinessException {
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
            MyLogger.info("File Path:::::::::::::::::::"+outputFile.getAbsolutePath());
            ByteArrayOutputStream baos=new ByteArrayOutputStream (); 
            outputFile.delete();
*/           
             	ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream (); 
                ITextRenderer renderer = new ITextRenderer();
                renderer.setDocumentFromString(htmlContent);
                renderer.layout(); 
    			renderer.createPDF(byteArrayOutputStream, false);
    		    renderer.finishPDF();
    		    /*
    		    List<BlobItem> blobItems=this.listBlobItems(this.configProperties.getBlobKYCDocuments(),StringUtility.CONTRACTS_SUBDIRECTORY+"/"+businessUser.getUserId());

    		    if(this.blobItemListUtility.isListPopulated(blobItems))
    		    {
    		    	for(BlobItem blobItem:blobItems)
    		    	{
    		    		if(blobItem!=null&&StringUtility.stringsMatch(blobItem.getName(), pdfFileName))
    		    		{
    		    			this.blobFileUtility.deleteFileFromBlob(this.configProperties.getBlobKYCDocuments(),  StringUtility.CONTRACTS_SUBDIRECTORY+"/"+businessUser.getUserId(), pdfFileName, true);
    		    		}
    		    		if(blobItem!=null&&StringUtility.stringsMatch(blobItem.getName(), StringUtility.CONTRACT_DOCUMENT_NAME+".pdf"))
    		    		{
    		    			this.blobFileUtility.deleteFileFromBlob(this.configProperties.getBlobKYCDocuments(),  StringUtility.CONTRACTS_SUBDIRECTORY+"/"+businessUser.getUserId(), StringUtility.CONTRACT_DOCUMENT_NAME+".pdf", true);
    		    		}
    		    	}
    		    }
    		   */
    		    this.blobFileUtility.deleteFileFromBlob(this.configProperties.getBlobKYCDocuments(), StringUtility.CONTRACTS_SUBDIRECTORY+"/"+businessUser.getUserId(), pdfFileName+".pdf", false);
				
    		    this.blobFileUtility.uploadFileToBlob(byteArrayOutputStream.toByteArray(), true, this.configProperties.getBlobKYCDocuments(), StringUtility.CONTRACTS_SUBDIRECTORY+"/"+businessUser.getUserId(),pdfFileName, "pdf");
			
    }

    
    ByteArrayOutputStream populateUserDetailsPDFInByteArrayOutputStream(BusinessUser businessUser) throws BusinessException
    {
    	Map<String, Object> map=new HashMap<String,Object>();
		map.put("userDetails", businessUser);
    	return this.populateGenericPDF("userDetails", map); 
    }
    
    ByteArrayOutputStream populateSignaturePDFInByteArrayOutputStream(BusinessUser businessUser) throws BusinessException
    {
    	Map<String, Object> map=new HashMap<String,Object>();
		map.put("signature", businessUser);
		return this.populateGenericPDF("signature", map);
    }
    
    ByteArrayOutputStream populateGenericPDF(String templateName,Map<String, Object> data) throws BusinessException
    {
    	Context context = new Context();
        context.setVariables(data);
        String htmlContent = templateEngine.process(templateName, context);
    	ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream (); 
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout(); 
		try {
			renderer.createPDF(byteArrayOutputStream, false);
		} catch (DocumentException exception) {
			exception.printStackTrace();
			throw new BusinessException(ErrorCode.PDF_GENERATION_FAILED);
		}
	    renderer.finishPDF();
		return byteArrayOutputStream;    	
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
	@Override
	public String downloadContract(List<UserAnswer> userAnswers,BusinessUser businessUser,List<String> solvedPages) throws BusinessException, IOException 
	{
		
	try {
		
		this.blobFileUtility.deleteFileFromBlob(this.configProperties.getBlobKYCDocuments(), StringUtility.CONTRACTS_SUBDIRECTORY+"/"+businessUser.getUserId(),StringUtility.CONTRACT_DOCUMENT_NAME +".pdf", false);
		PDFMergerUtility pdfMergerUtility=new PDFMergerUtility();
		ByteArrayOutputStream mergingByteArrayOutputStream=new ByteArrayOutputStream();
		pdfMergerUtility.setDestinationStream(mergingByteArrayOutputStream);
		 
		 for(String pageOrder:solvedPages)
		 {
			 ByteArrayOutputStream byteArrayOutputStream=this.blobFileUtility.
					 downloadStreamFromBlob(this.configProperties.getBlobKYCDocuments(),StringUtility.CONTRACTS_SUBDIRECTORY+"/"+businessUser.getUserId(),pageOrder+".pdf");
			pdfMergerUtility.addSource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
		 }
		 
		 for(UserAnswer userAnswer:userAnswers)
		 {
				ByteArrayOutputStream byteArrayOutputStream=this.blobFileUtility.
						downloadStreamFromBlob(this.configProperties.getBlobKYCDocuments(), userAnswer.getDocumentSubDirectory(),userAnswer.getDocumentName());
				pdfMergerUtility.addSource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
		}
	

		pdfMergerUtility.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
		
		
		BlobData blobData= this.blobFileUtility.
		uploadFileToBlob(mergingByteArrayOutputStream.toByteArray(), true, this.configProperties.getBlobKYCDocuments(),StringUtility.CONTRACTS_SUBDIRECTORY+"/"+businessUser.getUserId(),StringUtility.CONTRACT_DOCUMENT_NAME, StringUtility.PDF_EXTENSION);
		
		mergingByteArrayOutputStream.close();
		return blobData.getConcatinated(true);
		
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			throw new BusinessException(ErrorCode.CONTRACT_DOWNLOAD_FAILED);
		}
	}


	@Override
	public void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) throws IOException {
		  Context context = new Context();
	        context.setVariables(data);

	        String htmlContent = templateEngine.process(templateName, context);
    			  
	        try {
		            	ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream (); 
		                ITextRenderer renderer = new ITextRenderer();
		                renderer.setDocumentFromString(htmlContent);
		                renderer.layout(); 
		    			renderer.createPDF(byteArrayOutputStream, false);
		    		    renderer.finishPDF();   
						this.blobFileUtility.uploadFileToBlob(byteArrayOutputStream.toByteArray(), true, this.configProperties.getBlobKYCDocuments(), "userContracts/"+DateUtility.getCurrentDayMonthYear(),pdfFileName, "pdf");
					} catch (BusinessException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (DocumentException e) {
						e.printStackTrace();
					}
		
	}
	
	
	public List<BlobItem> listBlobItems(String container,String subDirectory)
	{
		return this.blobFileUtility.
		 listBlobItemsInFolder(this.blobFileUtility.generateBlobClient(container),subDirectory);	
	}


	@Override
	public List<BlobItem> listBlobItems() {
		BlobContainerClient blobContainerClient=this.blobFileUtility.generateBlobClient(this.configProperties.getBlobKYCDocumentsContainer()+"/Users/userContracts");
		
		return this.blobFileUtility.
				 listBlobItemsInFolder(this.blobFileUtility.generateBlobClient(this.configProperties.getBlobKYCDocumentsContainer()+"/Users/userContracts"),"29101012103956");	
	}
 
}