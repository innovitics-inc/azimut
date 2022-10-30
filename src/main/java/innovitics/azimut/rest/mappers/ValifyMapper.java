package innovitics.azimut.rest.mappers;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.rest.entities.BaseInput;
import innovitics.azimut.rest.entities.BaseOutput;
import innovitics.azimut.rest.models.BaseRestResponse;

public enum ValifyMapper {
	
	ECTRACT_OCR(null, "/v1/ocr/"),
	VALIDATE_ID(null, "/v1/ocr/"),
	GET_TOKEN(null, "/o/token/"),
	;	
	
	ValifyMapper(RestMapper<BaseInput, BaseOutput, BaseRestResponse, BaseBusinessEntity> restMapper, String resource) 
	{
		
		this.resource = resource;
		this.restMapper = restMapper;
	}
	
	private final String resource;
	private final RestMapper<BaseInput, BaseOutput, BaseRestResponse, BaseBusinessEntity> restMapper;
	
	public String getResource() 
	{
		return resource;
	}

	public RestMapper<BaseInput, BaseOutput, BaseRestResponse, BaseBusinessEntity> getRestMapper() {
		return restMapper;
	}
	
	
	
}
