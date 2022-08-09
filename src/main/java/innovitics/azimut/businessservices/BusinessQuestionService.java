package innovitics.azimut.businessservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.kyc.BusinessQuestion;
import innovitics.azimut.models.kyc.Question;
import innovitics.azimut.services.kyc.QuestionService;
import innovitics.azimut.utilities.mapping.kyc.QuestionMapper;
@Service
public class BusinessQuestionService extends AbstractBusinessService<BusinessQuestion>{

	@Autowired QuestionMapper questionMapper;
	@Autowired QuestionService  questionService;
	
	public BusinessQuestion getById(Long id)
	{
		Question question= this.questionService.getQuestionsById(id);
		return this.questionMapper.convertBasicUnitToBusinessUnit(question);
	}
	
	public List<BusinessQuestion> getByPageId(Long id)
	{
		return this.questionMapper.convertBasicListToBusinessList(this.questionService.getQuestionsByPage(id));
	}
	
}
