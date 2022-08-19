package innovitics.azimut.pdfgenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

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
    public void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) {
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);
        try {
        	this.logger.info("generating the PDF file:::::::::::::::::::::::");
            FileOutputStream fileOutputStream = new FileOutputStream("//home//site//wwwroot//webapps"+"//"+ pdfFileName);            
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(fileOutputStream, false);
            renderer.finishPDF();
            
            File file=new File("//home//site//wwwroot//webapps"+"//"+ pdfFileName);
            FileInputStream fin=new FileInputStream("//home//site//wwwroot//webapps"+"//"+ pdfFileName);
            
            try {
				this.blobFileUtility.uploadFileToBlob(fin, pdfFileName, file.getTotalSpace(), true, this.configProperties.getBlobKYCDocuments(), "userAnswers/"+DateUtility.getCurrentDayMonthYear());
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
 
}