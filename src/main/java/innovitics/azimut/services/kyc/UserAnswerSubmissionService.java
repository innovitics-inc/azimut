package innovitics.azimut.services.kyc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphType;
import com.cosium.spring.data.jpa.entity.graph.domain.NamedEntityGraph;

import innovitics.azimut.models.kyc.UserAnswer;
import innovitics.azimut.repositories.kyc.UserAnswerDynamicRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.child.UserAnswerSpecification;

@Service
public class UserAnswerSubmissionService extends AbstractService<UserAnswer, String> {
	
	@Autowired UserAnswerDynamicRepository userAnswerDynamicRepository;
	@Autowired UserAnswerSpecification userAnwerSpecification;
	
	public List<UserAnswer> submitAnswers(List<UserAnswer> userAnswers)
	{
		return this.userAnswerDynamicRepository.saveAll(userAnswers);
	}
	
	public UserAnswer getPreviousAnswerIfExisting(Long userId,Long answerId)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("userId", userId.toString(),SearchOperation.EQUAL,null));
		searchCriteriaList.add(new SearchCriteria("answerId", answerId.toString(),SearchOperation.EQUAL,null));
		searchCriteriaList.add(new SearchCriteria("deletedAt", "",SearchOperation.IS_NULL,null));
		return this.userAnswerDynamicRepository.findOne(this.userAnwerSpecification.findByCriteria(searchCriteriaList),new NamedEntityGraph(EntityGraphType.FETCH, "UserAnswer.details")).get();
	}

	
	public List<UserAnswer> getUserAnswersByUserIdandPageId(Long pageId,Long userId)
	{
		List<UserAnswer> userAnswers=new ArrayList<UserAnswer>();
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("pageId", pageId.toString(),SearchOperation.EQUAL,null));
		searchCriteriaList.add(new SearchCriteria("userId", userId.toString(),SearchOperation.EQUAL,null));
		searchCriteriaList.add(new SearchCriteria("deletedAt", "",SearchOperation.IS_NULL,null));
		userAnswers= this.userAnswerDynamicRepository.findAll(this.userAnwerSpecification.findByCriteria(searchCriteriaList),new NamedEntityGraph(EntityGraphType.FETCH, "UserAnswer.details"));
		return userAnswers;
	}
	
	public List<UserAnswer> getUserAnswersByUserIdAndAnswerType(Long userId,String answerType)
	{
		List<UserAnswer> userAnswers=new ArrayList<UserAnswer>();
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("userId", userId.toString(),SearchOperation.EQUAL,null));
		searchCriteriaList.add(new SearchCriteria("answerType", answerType,SearchOperation.EQUAL,null));
		searchCriteriaList.add(new SearchCriteria("deletedAt", "",SearchOperation.IS_NULL,null));
		userAnswers= this.userAnswerDynamicRepository.findAll(this.userAnwerSpecification.findByCriteria(searchCriteriaList),new NamedEntityGraph(EntityGraphType.FETCH, "UserAnswer.details"));
		return userAnswers;
	}
	public UserAnswer updateAnswer(UserAnswer userAnswer)
	{
		return this.userAnswerDynamicRepository.save(userAnswer);
	}
	
	public void deleteOldUserAnswers(Long pageId,Long userId)
	{
		this.userAnswerDynamicRepository.deleteOldUserAnswersForThePage(pageId,userId);
	}
	public boolean checkOldAnswerExistence(Long pageId,Long userId)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("pageId", pageId.toString(),SearchOperation.EQUAL,null));
		searchCriteriaList.add(new SearchCriteria("userId", userId.toString(),SearchOperation.EQUAL,null));
		searchCriteriaList.add(new SearchCriteria("deletedAt", "",SearchOperation.IS_NULL,null));
		long count=this.userAnswerDynamicRepository.count(this.userAnwerSpecification.findByCriteria(searchCriteriaList));
		return count>0l;
	}
	

}
