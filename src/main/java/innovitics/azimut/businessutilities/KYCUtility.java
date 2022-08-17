package innovitics.azimut.businessutilities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.kyc.BusinessQuestion;
import innovitics.azimut.businessmodels.kyc.ObjectDrawing;
import innovitics.azimut.utilities.ParentUtility;
import innovitics.azimut.utilities.crosslayerenums.AnswerType;
import innovitics.azimut.utilities.crosslayerenums.ObjectWidthType;
import innovitics.azimut.utilities.datautilities.ArrayUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.kycutilities.AnswerTypeUtility;
@Component
public class KYCUtility extends ParentUtility
{
	@Autowired AnswerTypeUtility answerTypeUtility;

	List<ObjectDrawing>  populateObjectDrawings(List<BusinessQuestion> businessQuestions)
  {
	int width100=1;
	int width50or100=2;
	 List<ObjectDrawing> objectDrawings=new ArrayList<ObjectDrawing>() ;
	  for(BusinessQuestion businessQuestion:businessQuestions)
	  {
		  ObjectDrawing objectDrawing=new ObjectDrawing();
			if (businessQuestion != null) 
			{
				if (this.isObjectWidthType100(businessQuestion)) 
					{
						objectDrawing.setWidthId(width100);
					} 
					else 
					{
						objectDrawing.setWidthId(width50or100);
					}
				objectDrawing.setQuestionId(businessQuestion.getId());
				objectDrawings.add(objectDrawing);
			}
	  }
	  
	  return objectDrawings;
  	}

	ObjectDrawing[] determineObjectsWidthType(List< ObjectDrawing>  objectDrawings)
 {
		int value100=100;
		int value50=50;
		ObjectDrawing[] objectDrawingArray = new ObjectDrawing[objectDrawings.size()];
		objectDrawings.toArray(objectDrawingArray);
		
	 for(int i=0;i<objectDrawingArray.length;i++)
	 {
		 if(objectDrawingArray[i]!=null)
		 {
			if(NumberUtility.areIntegerValuesMatching(objectDrawingArray[i].getWidthId(),ObjectWidthType.WIDTH_50OR100.getTypeId()))
		  { 
				 if(objectDrawingArray[i].getPassedValue()!=null&&objectDrawingArray[i].getPassedValue())
				 {
					 objectDrawingArray[i].setWidth(value50);
				 }
				 else
				 {
				 	if (i + 1 < objectDrawingArray.length) 
				 		{
							if (objectDrawingArray[i + 1].getWidthId() != null) 
							{
								if (NumberUtility.areIntegerValuesMatching(objectDrawingArray[i + 1].getWidthId(),ObjectWidthType.WIDTH_100.getTypeId())) 
								{
									objectDrawingArray[i].setWidth(value100);
								}

								else 
								{
									objectDrawingArray[i].setWidth(value50);
									objectDrawingArray[i + 1].setPassedValue(true);
								}
							}		
				 		}
				 }
		  	}
			else 
			{
				objectDrawingArray[i].setWidth(value100);
			}
			
		 	} 
		 }
	 
	 if(objectDrawingArray[objectDrawingArray.length-1]!=null)
	 {
		if(objectDrawingArray[objectDrawingArray.length-1].getPassedValue()!=null&&objectDrawingArray[objectDrawingArray.length-1].getPassedValue()) 
		{
			objectDrawingArray[objectDrawingArray.length-1].setWidth(value50);
		}
		else
		{
			objectDrawingArray[objectDrawingArray.length-1].setWidth(value100);
		}
	 }
	 
	 
	 return objectDrawingArray;
	 
	 
	 
 }
 
	public List<BusinessQuestion> populateTheObjectsWidth(List<BusinessQuestion> businessQuestions)
	{
		ObjectDrawing[] objectDrawings=this.determineObjectsWidthType(this.populateObjectDrawings(businessQuestions));
		
		for(int i=0;i<objectDrawings.length;i++)
		{
			businessQuestions.get(i).setWidth(objectDrawings[i].getWidth());
		}
		return businessQuestions;
	}
	
	
	boolean isObjectWidthType100(BusinessQuestion businessQuestion)
	{

			if(this.answerTypeUtility.isAnswerTypeMatching(businessQuestion,AnswerType.TEXT)
					||this.answerTypeUtility.isAnswerTypeMatching(businessQuestion,AnswerType.DROP)
					||this.answerTypeUtility.isAnswerTypeMatching(businessQuestion,AnswerType.PHONE)
					||this.answerTypeUtility.isAnswerTypeMatching(businessQuestion,AnswerType.EMAIL)
					||this.answerTypeUtility.isAnswerTypeMatching(businessQuestion,AnswerType.CALENDER))
			{
				return false;
			}
			else
			{
				return true;
			}
		
	}
		
	
}
