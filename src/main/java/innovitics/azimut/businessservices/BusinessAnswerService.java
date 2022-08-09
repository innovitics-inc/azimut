package innovitics.azimut.businessservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.kyc.BusinessAnswer;
import innovitics.azimut.models.kyc.Answer;
import innovitics.azimut.services.kyc.AnswerService;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.mapping.kyc.AnswerMapper;
@Service
public class BusinessAnswerService extends AbstractBusinessService<BusinessAnswer>{
	
@Autowired AnswerService answerService;
@Autowired ListUtility<Answer> listUtility;
@Autowired AnswerMapper  answerMapper;
	public BusinessAnswer getById(Long id)
	{

		return this.answerMapper.convertBasicUnitToBusinessUnit(this.answerService.getAnswerById(id));
		
	}
	
	public List<BusinessAnswer> getAnswersByQuestion(Long questionId)
	{
		this.logger.info("Enter here");
		return this.answerMapper.convertBasicListToBusinessList(this.answerService.getAnswerByQuestion(questionId));
	}
	

}
