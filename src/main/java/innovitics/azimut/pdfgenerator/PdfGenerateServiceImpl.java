package innovitics.azimut.pdfgenerator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.fileutilities.BlobFileUtility;

@Service
public class PdfGenerateServiceImpl implements PdfGenerateService {
    private Logger logger = LoggerFactory.getLogger(PdfGenerateServiceImpl.class);

    @Autowired ConfigProperties  configProperties;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired BlobFileUtility blobFileUtility;

    @Override
    public void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) throws IOException {
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);
        try {
        	
        	final File outputFile = File.createTempFile(pdfFileName,".pdf");
        	
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);            
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(fileOutputStream, false);
            renderer.finishPDF();            
            FileInputStream fileInputStream=new FileInputStream(outputFile);                       
            this.logger.info("File Path:::::::::::::::::::"+outputFile.getAbsolutePath());
            
            //outputFile.delete();
            
            try {
				
            	this.blobFileUtility.uploadFileToBlob(fileInputStream, true, this.configProperties.getBlobKYCDocuments(), "userAnswers/"+DateUtility.getCurrentDayMonthYear(),"pdf");
			} catch (BusinessException e) 
            {
				e.printStackTrace();
			} 
            catch (IOException e) 
            {	
				e.printStackTrace();
			}
          } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
            this.logger.info("Failed to generate::::");
            e.printStackTrace();
        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
            this.logger.info("Failed to generate::::");
            e.printStackTrace();
        }
       
    }

	@Override
	public void download() {
		try {
			 PDFMergerUtility ut = new PDFMergerUtility();
			 ut.addSource(this.blobFileUtility.downloadFileToBlob(this.configProperties.getBlobKYCDocuments(), "userAnswers/"+DateUtility.getCurrentDayMonthYear(), "8421e66b-2ffb-4b9d-a73f-7974a5d9479a.pdf"));
			 ut.addSource(this.blobFileUtility.downloadFileToBlob(this.configProperties.getBlobKYCDocuments(), "userAnswers/"+DateUtility.getCurrentDayMonthYear(), "57df0465-60cb-420d-af2a-ca028a62217c.pdf"));
			 ut.setDestinationFileName("E:\\Backend Team\\Azimut\\new");
			 ut.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    
 
}