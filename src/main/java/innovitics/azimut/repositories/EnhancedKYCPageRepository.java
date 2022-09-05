package innovitics.azimut.repositories;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityGraph;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import innovitics.azimut.models.BaseEntity;
import innovitics.azimut.models.kyc.Answer;
import innovitics.azimut.models.kyc.KYCPage;
import innovitics.azimut.models.kyc.Question;
import innovitics.azimut.models.kyc.UserAnswer;
import innovitics.azimut.models.user.UserType;
import innovitics.azimut.utilities.kycutilities.EntityGraphBuilder;
import innovitics.azimut.utilities.kycutilities.EntityVisitor;

@Component
public class EnhancedKYCPageRepository extends AbstractRepository<KYCPage>{

	
	@SuppressWarnings("unchecked")
	public List<KYCPage>  getEnhanced (Long id)
	{
		this.logger.info("Writing the natvie query:::::::::::::::::::::::::::::::::::");
		LinkedList<Question> questions= new LinkedList<Question>();
		
		List<Object[]> results=  this.entityManager.createNativeQuery("SELECT * FROM kyc_pages p left join "
				+ "questions q on p.id=1 and p.id=q.page_id "
				+ "left join answers a on q.id=a.question_id "
				+ "left join user_answers ua on a.id=ua.answer_id "
				+ "left join answers ra on a.id=ra.parent_answer_id "
				+ "left join user_answers rua on ra.id=rua.answer_id "
				+ "LEFT JOIN questions subq on q.id=subq.parent_question_id "
				+ "left join answers subqa on subq.id=subqa.question_id "
				+ "left join user_answers subqua on subqa.id=subqua.answer_id "
				+ "left join answers subqra on subqa.id=subqra.parent_answer_id "
				+ "left join user_answers subqrua on subqra.id=subqrua.answer_id ", "PageMapping").getResultList();
		
		
		
		this.logger.info("Results::::"+results.toString());
		
		
		
		results.stream().forEach((record) -> 
		{	
			this.logger.info("\nRecord::");
			this.logger.info("\nRecord column0::"+record[0].getClass().getName());
			this.logger.info("\nRecord column1::"+record[1].getClass().getName());
			this.logger.info("\nRecord column2::"+record[2].getClass().getName());
			
			Question question = (Question)record[0];
			
			this.logger.info("\nquestion "+""+" ::"+question.toString());
			
		    Answer answer = (Answer)record[1];
		    this.logger.info("\nanswer "+""+" ::"+answer.toString());
		    
		    UserAnswer userAnswer=(UserAnswer) record[2];
		    this.logger.info("\nuserAnswer"+""+" ::"+userAnswer.toString());
		    		    
		    questions.add(question);
		    
		
		});

		KYCPage page= new KYCPage();
		LinkedList<KYCPage> pages=new LinkedList<KYCPage>();
		//page.setQuestions(questions);
		
		page.toString();
		
		return pages;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<KYCPage>  getEnhanced2 (Long id)
	{
		this.logger.info("Writing the natvie query:::::::::::::::::::::::::::::::::::");
		
		LinkedList<Question> questions= new LinkedList<Question>();

		List<Object[]> results = ((Session)this.entityManager.getDelegate()).createSQLQuery("SELECT * FROM "
				+ "questions q "
				+ "left join answers a on q.id=a.question_id "
				+ "left join user_answers ua on a.id=ua.answer_id "
				+ "left join answers ra on a.id=ra.parent_answer_id "
				+ "left join user_answers rua on ra.id=rua.answer_id "
				+ "LEFT JOIN questions subq on q.id=subq.parent_question_id "
				+ "left join answers subqa on subq.id=subqa.question_id "
				+ "left join user_answers subqua on subqa.id=subqua.answer_id "
				+ "left join answers subqra on subqa.id=subqra.parent_answer_id "
				+ "left join user_answers subqrua on subqra.id=subqrua.answer_id ").
				addEntity("q", Question.class).
				addEntity("a", Answer.class).
				addEntity("ua",UserAnswer.class).
				addEntity("ra",Answer.class).
				addEntity("rua",UserAnswer.class).
				addEntity("subq",Question.class).
				addEntity("subqa",Answer.class).
				addEntity("subqua",UserAnswer.class).
				addEntity("subqra",Answer.class).
				addEntity("subqrua",UserAnswer.class)
				.list();
				results.stream().forEach((record) -> {

					
					
				Question question = (Question)record[0];
				
				this.logger.info("\nquestion "+""+" ::"+question.toString());
				
			    Answer answer = (Answer)record[1];
			    this.logger.info("\nanswer "+""+" ::"+answer.toString());
			    
			    UserAnswer userAnswer=(UserAnswer) record[2];
			    this.logger.info("\nuserAnswer"+""+" ::"+userAnswer.toString());
			    		    
			    questions.add(question);
				

		});
		
		KYCPage page= new KYCPage();
		LinkedList<KYCPage> pages=new LinkedList<KYCPage>();
		
			
		return pages;
		
	}
	
	
	public List<KYCPage> buildCriteria()	
	{
	
		
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<KYCPage> kYCPageCriteriaQuery = criteriaBuilder.createQuery(KYCPage.class);
		Root<KYCPage> kycRoot = kYCPageCriteriaQuery.from(KYCPage.class);
		kycRoot.fetch("userType", JoinType.LEFT);
		kYCPageCriteriaQuery.where(criteriaBuilder.equal(kycRoot.get("userType").get("id"),1));
		TypedQuery<KYCPage> typedQuery= this.entityManager.createQuery(kYCPageCriteriaQuery);
		typedQuery.setHint("javax.persistence.fetchgraph", this.entityManager.getEntityGraph("KYCPage.details"));
		
		return typedQuery.getResultList();
	
		
	}


	@SuppressWarnings("unchecked")
	public KYCPage getEnhanced4(Long id)
	{
		
		EntityGraph<?> graph = this.entityManager.getEntityGraph("KYCPage.details");
		Map hints = new HashMap();
		hints.put("javax.persistence.fetchgraph", graph);		 
		return this.entityManager.find(KYCPage.class, id, hints);
		
	}
	
	public KYCPage getEnhanced5(Long id)
	{
		/*
		EntityGraph entityGraph = this.entityManager.getEntityGraph("KYCPage.details");

		CriteriaBuilder criteriaBuilder=this.entityManager.getCriteriaBuilder();
		CriteriaQuery<KYCPage> query = criteriaBuilder.createQuery(KYCPage.class);
		Root<KYCPage> message = query.from(KYCPage.class);
		*/
		EntityGraph<?> entityGraph = this.entityManager.getEntityGraph("KYCPage.details");
		TypedQuery<KYCPage> typedQuery = this.entityManager.createQuery("select p " +
			    " from KYCPage p "
				
				+ " LEFT JOIN p.questions q "
				+ " LEFT JOIN q.answers a "
			 	+ " LEFT JOIN q.userAnswers qu "	
				+ " LEFT JOIN a.relatedAnswers ra " 
				+ " LEFT JOIN a.userAnswers au "
				+ " LEFT JOIN ra.userAnswers rau "
			
				+ " LEFT JOIN q.subQuestions sq "
				+ " LEFT JOIN sq.answers sa "
			 	+ " LEFT JOIN sq.userAnswers squ "	
				+ " LEFT JOIN sa.relatedAnswers sra " 
				+ " LEFT JOIN sa.userAnswers sau "
				+ " LEFT JOIN sra.userAnswers srau "
		 
			    + " where p.id=1", KYCPage.class);
		
		Properties props = new Properties();
		props.put("javax.persistence.fetchgraph", entityGraph);

		typedQuery.setHint("javax.persistence.fetchgraph",entityGraph);
		KYCPage result = typedQuery.getSingleResult();
		
		return result;
	}
	
	
	public List<KYCPage>  getEnhanced7 (Long id)
	{
	    String jpql = "SELECT c FROM Contact c";
        TypedQuery<KYCPage> query = entityManager.createQuery(jpql, KYCPage.class);
         
		KYCPage page= new KYCPage();
		LinkedList<KYCPage> pages=new LinkedList<KYCPage>();
		pages.add(page);
		return pages;
		
	}

	
}
