package innovitics.azimut.rest.mappers;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.businessmodels.WrapperBusinessEntity;
import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.AbstractBaseRestConsumer;
import innovitics.azimut.rest.MyGenericClass;
import innovitics.azimut.rest.entities.BaseInput;
import innovitics.azimut.rest.entities.BaseOutput;
import innovitics.azimut.rest.models.BaseRestRequest;
import innovitics.azimut.rest.models.BaseRestResponse;
import innovitics.azimut.rest.models.teacomputers.AddAccountResponse;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.exceptionhandling.ExceptionHandler;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
@Service
public abstract class RestMapper <I extends BaseInput,O extends BaseOutput, RES extends BaseRestResponse ,B extends BaseBusinessEntity>{
	public final static Logger logger = LogManager.getLogger(RestMapper.class.getName());
	@Autowired
	ExceptionHandler exceptionHandler;
	@Autowired
	ListUtility<B> listUtility;
	@Autowired protected ConfigProperties configProperties;
	//@Autowired AbstractBaseRestConsumer<BaseRestRequest, RES, I, O> abstractBaseRestConsumer;
	
	abstract B consumeRestService(B baseBusinessEntity,String params) throws IntegrationException, HttpClientErrorException, Exception;	
	
	abstract List<B> consumeListRestService(B baseBusinessEntity,String params) throws IntegrationException, HttpClientErrorException, Exception;
	
	abstract I createInput (B baseBusinessEntity) ;
	
	abstract B createBusinessEntityFromOutput(O BaseOutput);
	
    protected abstract List<B> createListBusinessEntityFromOutput(O BaseOutput);
	
	
	public WrapperBusinessEntity<B> wrapBaseBusinessEntity(Boolean isList,B baseBusinessEntity,String params) throws IntegrationException
	{
		WrapperBusinessEntity<B> wrapperBusinessEntity=new WrapperBusinessEntity<B>();
		try {
			if (!isList) {
				wrapperBusinessEntity.setData(this.consumeRestService(baseBusinessEntity, params));
			} else {
				logger.info("List Wrapper:::::");
				wrapperBusinessEntity.setDataList(this.consumeListRestService(baseBusinessEntity, params));
			}
		} catch (Exception exception) {
			this.logger.info("Exception Stack Trace:::");
			exception.printStackTrace();
			IntegrationException  integrationException=(IntegrationException)exception;
			throw  integrationException;
		}
		return wrapperBusinessEntity;
	}
	
	/*
	 public WrapperBusinessEntity<B> wrapAdvancedBaseBusinessEntity(Boolean isList,B baseBusinessEntity,String params) throws IntegrationException
	{
		//MyGenericClass<RES> myGenericClass=new MyGenericClass<RES>(RES);
		WrapperBusinessEntity<B> wrapperBusinessEntity=new WrapperBusinessEntity<B>();
		try {
			if (!isList) {
				wrapperBusinessEntity.setData(this.consumeRestAdvancedService(baseBusinessEntity,null, params));
			} else {
				logger.info("List Wrapper:::::");
				wrapperBusinessEntity.setDataList(this.consumeListRestAdvancedService(baseBusinessEntity,null, params));
			}
		} catch (Exception exception) {
			this.logger.info("Exception Stack Trace:::");
			exception.printStackTrace();
			IntegrationException  integrationException=(IntegrationException)exception;
			throw  integrationException;
		}
		return wrapperBusinessEntity;
	}
	
	
	B consumeRestAdvancedService(B baseBusinessEntity,Class<RES> clazz,String params) throws HttpClientErrorException, IntegrationException, Exception
	{	
		return this.createBusinessEntityFromOutput(this.abstractBaseRestConsumer.invoke(this.createInput(baseBusinessEntity),clazz, params));
			
	}
	
	List<B>  consumeListRestAdvancedService(B baseBusinessEntity,Class<RES> clazz,String params) throws HttpClientErrorException, IntegrationException, Exception
	{	
		return this.createListBusinessEntityFromOutput(this.abstractBaseRestConsumer.invoke(this.createInput(baseBusinessEntity),clazz, params));
			
	}
	*/

	

}
