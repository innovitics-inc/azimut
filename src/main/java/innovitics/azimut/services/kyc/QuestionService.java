package innovitics.azimut.services.kyc;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphType;
import com.cosium.spring.data.jpa.entity.graph.domain.NamedEntityGraph;

import innovitics.azimut.models.kyc.Question;
import innovitics.azimut.repositories.kyc.QuestionDynamicRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.childparent.QuestionSpecification;
import innovitics.azimut.utilities.logging.MyLogger;
@Service
public class QuestionService extends AbstractService<Question, String>{

	
	@Autowired  QuestionSpecification questionSpecification;
	@Autowired QuestionDynamicRepository  questionDynamicRepository;
	
	public List<Question> getQuestionsByPage(Long pageId)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("id", pageId.toString(),SearchOperation.PARENT_EQUAL,"kycPage"));
		return this.questionDynamicRepository.findAll(this.questionSpecification.findByCriteria(searchCriteriaList));
	}
	@SuppressWarnings("unchecked")
	public List<BigInteger> getMandatoryQuestionsByPage(Long pageId)
	{
		/*
		  List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		  searchCriteriaList.add(new SearchCriteria("id",pageId.toString(),SearchOperation.PARENT_EQUAL,"kycPage"));
		  searchCriteriaList.add(new SearchCriteria("isAnswerMandatory",true,SearchOperation.EQUAL,null));
		  return this.questionDynamicRepository.findAll(this.questionSpecification.findByCriteria(searchCriteriaList));
		 */
		return (List<BigInteger>)(Object)questionDynamicRepository.getMandatoryQuestionCount(pageId);
		
	}
	
	public Question getQuestionsById(Long id)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("id", id.toString(),SearchOperation.EQUAL,null));
		Question question=this.questionDynamicRepository.findOne(this.questionSpecification.findByCriteria(searchCriteriaList)).get();
		
		MyLogger.info("Question:::"+question.toString());
		
		return question;
	}
}
