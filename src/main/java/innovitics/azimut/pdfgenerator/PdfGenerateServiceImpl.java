package innovitics.azimut.pdfgenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
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
    public void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) throws IOException {
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("//home//site//wwwroot//webapps"+"//"+ pdfFileName);            
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(fileOutputStream, false);
            renderer.finishPDF();
            /*
            File file=new File("//home//site//wwwroot//webapps"+"//"+ pdfFileName);
            FileInputStream fin=new FileInputStream("//home//site//wwwroot//webapps"+"//"+ pdfFileName);            
            file.delete();
            */
            
            PipedOutputStream pipedOutputStream = new PipedOutputStream();
            PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);
            this.read(pipedInputStream,pipedOutputStream);
            
            try {
				this.blobFileUtility.uploadFileToBlob(pipedInputStream, true, this.configProperties.getBlobKYCDocuments(), "userAnswers/"+DateUtility.getCurrentDayMonthYear());
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
    
    void read(PipedInputStream pipedInputStream,PipedOutputStream pipedOutputStream)
    {
    	
         Thread thread2 = new Thread(new Runnable() {
             @Override
             public void run() {
                 try {
                     int data = pipedInputStream.read();
                     System.out.print("Reading data using pipedInputStream:");
                     while (data != -1) {
                         System.out.print((char) data);
                         data = pipedInputStream.read();
                     }
                 } catch (IOException e) {

                 } finally {
                     // Closing the streams
                     if (pipedOutputStream != null)
                         try {
                             pipedOutputStream.close();
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                     if (pipedInputStream != null) {
                         try {
                             pipedInputStream.close();
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                     }
                 }
             }
         });
         thread2.start();
    }
 
}