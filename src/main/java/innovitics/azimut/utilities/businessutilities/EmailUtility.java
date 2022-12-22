package innovitics.azimut.utilities.businessutilities;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.azure.core.amqp.implementation.StringUtil;

import innovitics.azimut.utilities.ParentUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.fileutilities.BlobFileUtility;
import innovitics.azimut.utilities.logging.MyLogger;

@Component
public class EmailUtility extends ParentUtility{

	
    @Autowired
    private JavaMailSender emailSender;
    @Autowired BlobFileUtility blobFileUtility;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("noreply@innovitics.com");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        
        
        try {
        	emailSender.send(message);
        }
        catch (MailException ex) 
        {
        	MyLogger.error("Email could not be sent::::::::::");
        	ex.printStackTrace();
        }
        
        
    }
    
    public void sendMailWithAttachment(String to, String subject, String body,ByteArrayOutputStream byteArrayOutputStream)
    {
    	MimeMessage mimeMessage = emailSender.createMimeMessage();
    	try {
    	    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    	    helper.setTo(new InternetAddress(to));
    	    helper.setFrom(this.configProperties.getMailUserName());
    	    helper.setText(body);
    	  
    	    DataSource attachment = new  ByteArrayDataSource(byteArrayOutputStream.toByteArray(),"application/"+StringUtility.PDF_EXTENSION);
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.setDataHandler(new DataHandler(attachment));
        
            helper.addAttachment(StringUtility.CONTRACT_DOCUMENT_NAME+"."+StringUtility.PDF_EXTENSION, attachment);
    	        	    
    	} catch (MessagingException e) {
    	    e.printStackTrace();
    	}

    	try 
    	{
    	  this.emailSender.send(mimeMessage);
    	} 
    	catch (MailException ex) 
    	{
    		MyLogger.error("Email could not be sent::::::::::");
        	ex.printStackTrace();
    	}
       
    }
    
 
    
}