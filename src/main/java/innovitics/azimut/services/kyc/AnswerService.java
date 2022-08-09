package innovitics.azimut.services.kyc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.kyc.Answer;
import innovitics.azimut.repositories.kyc.AnswerDynamicRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.childparent.AnswerChildParentSpecification;
@Service
public class AnswerService extends AbstractService<Answer, String>{

	
	@Autowired  AnswerChildParentSpecification answerChildParentSpecification;
	@Autowired AnswerDynamicRepository  answerDynamicRepository;
	public List<Answer> getAnswerByQuestion(Long questionId)
	{
		this.logger.info("Enter here");
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("id", questionId.toString(),SearchOperation.PARENT_EQUAL,"question"));
		return this.answerDynamicRepository.findAll(this.answerChildParentSpecification.findByCriteria(searchCriteriaList));
	}
	
	public Answer getAnswerById(Long id)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("id", id.toString(),SearchOperation.EQUAL,null));
		Answer answer= this.answerDynamicRepository.findOne(this.answerChildParentSpecification.findByCriteria(searchCriteriaList)).get();
		this.logger.info("Answer Service:::::");
		return answer;
		
		
	}

	
}
