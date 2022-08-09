package innovitics.azimut.utilities.kycutilities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.kyc.BusinessQuestion;
import innovitics.azimut.models.kyc.UserAnswer;
import innovitics.azimut.utilities.crosslayerenums.AnswerType;
import innovitics.azimut.utilities.datautilities.StringUtility;
@Component
public class AnswerTypeUtility {

	public boolean isAnswerTypeValid(String answerType)
	{
		boolean result=false;
		
		List<String> answerTypes=new ArrayList<String>();
		answerTypes.add(AnswerType.RADIO.getType());
		answerTypes.add(AnswerType.CHECK.getType());
		answerTypes.add(AnswerType.DROP.getType());
		answerTypes.add(AnswerType.TEXT.getType());
		answerTypes.add(AnswerType.RICH.getType());
		answerTypes.add(AnswerType.CALENDER.getType());
		answerTypes.add(AnswerType.PHONE.getType());
		answerTypes.add(AnswerType.EMAIL.getType());
		answerTypes.add(AnswerType.DOCUMENT.getType());
		
		if(StringUtility.isStringPopulated(answerType)&&answerTypes.contains(answerType))
		{
			result=true;
		}
			

		return result;
		
	}
	
	public boolean isAnswerTypeMatching(UserAnswer userAnswer, AnswerType answerType)
	{
		boolean result=false;
		
		if(userAnswer!=null&&StringUtility.stringsMatch(userAnswer.getAnswerType(), answerType.getType()))
			result=true;
		
		return result;
	}
	public boolean isAnswerTypeMatching(BusinessQuestion businessQuestion, AnswerType answerType)
	{
		boolean result=false;
		
		if(businessQuestion!=null&&StringUtility.stringsMatch(businessQuestion.getAnswerType(), answerType.getType()))
			result=true;
		
		return result;
	}
	
	
}
