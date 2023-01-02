package innovitics.azimut.pdfgenerator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import innovitics.azimut.businessmodels.kyc.BusinessAnswer;
import innovitics.azimut.businessmodels.kyc.BusinessKYCPage;
import innovitics.azimut.businessmodels.kyc.BusinessQuestion;
import innovitics.azimut.businessmodels.kyc.BusinessSubmittedAnswer;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.utilities.ParentUtility;
import innovitics.azimut.utilities.crosslayerenums.AnswerType;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.fileutilities.BlobFileUtility;
import innovitics.azimut.utilities.logging.MyLogger;

@Component
public class PdfFiller extends ParentUtility{

	 
	@Autowired  ListUtility<PDField> pdfieldListUtility;
	@Autowired  ListUtility<BusinessQuestion> businessQuestionsListUtility;
	@Autowired  ListUtility<BusinessSubmittedAnswer> businessSubmittedAnswerListUtility;
	public void fillPdfForm(BusinessUser businessUser,BlobFileUtility blobFileUtility,List<BusinessKYCPage> businessKYCPages,String sourceContainerName,String sourceSubDirectory,String sourceFileName,String destinationContainerName,String destinationSubDirectory,String destinationFileName) throws BusinessException, IOException {

		
		List<String> textAnswerTypes=new ArrayList<String>();
		List<String> choiceAnswerTypes=new ArrayList<String>();
		
		textAnswerTypes.add(AnswerType.TEXT.getType());
		textAnswerTypes.add(AnswerType.RICH.getType());
		textAnswerTypes.add(AnswerType.EMAIL.getType());
		textAnswerTypes.add(AnswerType.PHONE.getType());
		textAnswerTypes.add(AnswerType.EMAIL.getType());
		textAnswerTypes.add(AnswerType.CALENDER.getType());
		textAnswerTypes.add(AnswerType.DROP.getType());
		
		choiceAnswerTypes.add(AnswerType.CHECK.getType());
		choiceAnswerTypes.add(AnswerType.RADIO.getType());
		
		
		ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
    	MyLogger.info("Accessing the download input stream function in the pdf filler::::");
    	//InputStream input = null;
        //input = new FileInputStream(new File("E:\\Backend Team\\Azimut\\Template.pdf"));
    	//blobFileUtility.downloadInputStreamFromBlob(sourceContainerName,sourceSubDirectory, sourceFileName);        
        PdfReader reader = new PdfReader("E:\\Backend Team\\Azimut\\Template.pdf");
        PdfStamper stamper;
		try {
			stamper = new PdfStamper(reader, new FileOutputStream("E:\\Backend Team\\Azimut\\final-document.pdf"));
			AcroFields form = stamper.getAcroFields();
			
			
			for(BusinessKYCPage businessKYCPage:businessKYCPages)
            {
            	this.fillPdfFields(stamper,form,businessKYCPage,textAnswerTypes,choiceAnswerTypes);
            }
			
	        stamper.setFormFlattening(true);
	        stamper.close();
	        reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        //pdfDoc.save(byteArrayOutputStream);
        //blobFileUtility.uploadFileToBlob(byteArrayOutputStream.toByteArray(), true, destinationContainerName, destinationSubDirectory, destinationFileName, StringUtility.PDF_EXTENSION);

    }
	
	private void fillPdfFields(PdfStamper stamper,AcroFields form,BusinessKYCPage businessKYCPage,List<String> textAnswerTypes,List<String> choiceAnswerTypes) throws IOException, DocumentException
	{
		
			for(BusinessQuestion businessQuestion:businessKYCPage.getQuestions())	
			{
				this.checkQuestionTypeAndSetValue(stamper,form, businessQuestion, textAnswerTypes, choiceAnswerTypes);
				if(businessQuestionsListUtility.isListPopulated(businessQuestion.getSubQuestions()))
				{
					for(BusinessQuestion businessSubQuestion:businessQuestion.getSubQuestions())
					{
						this.checkQuestionTypeAndSetValue(stamper,form, businessSubQuestion, textAnswerTypes, choiceAnswerTypes);
					}
				}
			}	
	}
	
	private void checkQuestionTypeAndSetValue(PdfStamper stamper,AcroFields form,BusinessQuestion businessQuestion,List<String> textAnswerTypes,List<String> choiceAnswerTypes) throws IOException, DocumentException
	{
		MyLogger.info("PDF Field Name:::"+businessQuestion.getPdfieldName());
		if(StringUtility.isStringPopulated(businessQuestion.getPdfieldName()))
		{
				
				if(textAnswerTypes.contains(businessQuestion.getAnswerType()))
				{
					if(this.businessSubmittedAnswerListUtility.isListPopulated(businessQuestion.getUserAnswers()))
					{
						MyLogger.info("Assigned Answer in pdf Field:"+businessQuestion.getPdfieldName()+" is ::"+businessQuestion.getUserAnswers().get(0).getAnswerValue());
						form.setField(businessQuestion.getPdfieldName(),businessQuestion.getUserAnswers().get(0).getAnswerValue());
					}
				}				
		}
		else if(choiceAnswerTypes.contains(businessQuestion.getAnswerType()))
		{
			MyLogger.info("Choice Answer:::");
			if(this.businessSubmittedAnswerListUtility.isListPopulated(businessQuestion.getUserAnswers()))
			{
				for(BusinessAnswer businessAnswer:businessQuestion.getAnswers())
				{
					MyLogger.info("Answer:::"+businessAnswer.getId());
					for(BusinessSubmittedAnswer businessSubmittedAnswer:businessQuestion.getUserAnswers())
					{
						MyLogger.info("UserAnswerID:::"+businessSubmittedAnswer.getAnswerId());
						MyLogger.info("UserAnswerValue:::"+businessSubmittedAnswer.getAnswerValue());
						if(NumberUtility.areLongValuesMatching(businessAnswer.getId(), businessSubmittedAnswer.getAnswerId()))
						{
							MyLogger.info("Match Found:::"+businessAnswer.getId()+" and "+businessSubmittedAnswer.getAnswerId());
							MyLogger.info("Assigned Answer in pdf Field:::"+businessAnswer.getPdFieldName());
							form.setField(businessAnswer.getPdFieldName(),"Yes");
						}
					}
				}
			}
		}
	}
	
}
