package innovitics.azimut.pdfgenerator;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
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

import innovitics.azimut.businessmodels.kyc.BusinessAnswer;
import innovitics.azimut.businessmodels.kyc.BusinessKYCPage;
import innovitics.azimut.businessmodels.kyc.BusinessQuestion;
import innovitics.azimut.businessmodels.kyc.BusinessSubmittedAnswer;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.utilities.crosslayerenums.AnswerType;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.fileutilities.BlobFileUtility;

@Component
public class PdfFiller {

	@Autowired BlobFileUtility blobFileUtility;
	@Autowired  ListUtility<PDField> pdfieldListUtility;
	
	public void fillPdfForm(List<BusinessKYCPage> businessKYCPages,String sourceContainerName,String sourceSubDirectory,String sourceFileName,String destinationContainerName,String destinationSubDirectory,String destinationFileName) throws BusinessException, IOException {

		
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
		
		InputStream input = null;
		ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        try 
        {
        	input=blobFileUtility.downloadInputStreamFromBlob(sourceContainerName,sourceSubDirectory, sourceFileName);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        
        try (PDDocument pdfDoc = Loader.loadPDF(input);) {
            PDDocumentCatalog docCatalog = pdfDoc.getDocumentCatalog();
            PDAcroForm acroForm = docCatalog.getAcroForm();
            List<PDField> pdfields= acroForm.getFields();
            for(BusinessKYCPage businessKYCPage:businessKYCPages)
            {
            	this.fillPdfFields(pdfields,acroForm,businessKYCPage,textAnswerTypes,choiceAnswerTypes);
            }
            
            acroForm.flatten();
            pdfDoc.save(byteArrayOutputStream);
            this.blobFileUtility.uploadFileToBlob(byteArrayOutputStream.toByteArray(), true, destinationContainerName, destinationSubDirectory, sourceFileName, destinationFileName);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }

    }
	
	
	public void fillPdfFields( List<PDField> pdfields,PDAcroForm acroForm,BusinessKYCPage businessKYCPage,List<String> textAnswerTypes,List<String> choiceAnswerTypes) throws IOException
	{
		
		
		if(pdfieldListUtility.isListPopulated(pdfields))
		{
			for(BusinessQuestion businessQuestion:businessKYCPage.getQuestions())	
			{
				PDField pdField = acroForm.getField(businessQuestion.getPdfieldName());
				if(pdField!=null)
				{
					if(textAnswerTypes.contains(businessQuestion.getAnswerType()))
					{
						pdField.setValue(businessQuestion.getUserAnswers().get(0).getAnswerValue());		
					}
					else if(choiceAnswerTypes.contains(businessQuestion.getAnswerType()))
					{
						for(BusinessAnswer businessAnswer:businessQuestion.getAnswers())
						{
							for(BusinessSubmittedAnswer businessSubmittedAnswer:businessQuestion.getUserAnswers())
							{
								if(NumberUtility.areLongValuesMatching(businessAnswer.getId(), businessSubmittedAnswer.getAnswerId()))
								{
									PDField answerPdField = acroForm.getField(businessAnswer.getPdFieldName());																
									((PDCheckBox) answerPdField).check();
								}

							}
						}
						
					}
				}
			}
		}
	
	}
	
	
}
