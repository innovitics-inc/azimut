package innovitics.azimut.services.kyc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphType;
import com.cosium.spring.data.jpa.entity.graph.domain.NamedEntityGraph;

import innovitics.azimut.models.user.UserImage;
import innovitics.azimut.repositories.kyc.UserImageDynamicRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.datautilities.ArrayUtility;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.childparent.UserImageSpecification;
@Service
public class UserImageService extends AbstractService<UserImage, String>{

	@Autowired UserImageDynamicRepository userImageDynamicRepository;
	
	@Autowired UserImageSpecification userImageSpecification;
	public List<UserImage> saveImages (List<UserImage> userImages)
	{
		return this.userImageDynamicRepository.saveAll(userImages);
	}
	
	public List<UserImage> getUserImagesByUserAndTypes(Long userId,Integer [] imageTypes)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("id",userId,SearchOperation.PARENT_EQUAL,"user"));
		searchCriteriaList.add(new SearchCriteria("deletedAt","",SearchOperation.IS_NULL,null));
		if(this.arrayUtility.isArrayPopulated(imageTypes))
		{
			searchCriteriaList.add(new SearchCriteria("imageType", this.arrayUtility.generateObjectListFromObjectArray(imageTypes),SearchOperation.IN, null));
		}
		return this.userImageDynamicRepository.findAll(userImageSpecification.findByCriteria(searchCriteriaList),new NamedEntityGraph(EntityGraphType.FETCH, "UserImage.details"));
	}
	
	public void softDeleteOldUserIdImages(Long userId)
	{
		this.userImageDynamicRepository.deleteOldUserIdImages(userId);
	}
	public void softDeleteOldUserFaceImages(Long userId)
	{
		this.userImageDynamicRepository.deleteOldUserFaceImages(userId);
	}
}
