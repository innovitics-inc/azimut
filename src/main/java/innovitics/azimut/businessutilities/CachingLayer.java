package innovitics.azimut.businessutilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.utilities.ParentUtility;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.DiskStorePathManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;

@Component
public class CachingLayer  extends ParentUtility {

	@Autowired CacheManager singletonManager;
	protected static final Logger logger = LoggerFactory.getLogger(CachingLayer.class);

	public Object getValueIfExisting (Object object,String methodName,Object[] parameters,Class<?>[] paramterTypes,String cacheKey,int timeToLive,int timeToIdle) throws BusinessException
	{
		/*DiskStoreConfiguration diskStoreConfiguration = new DiskStoreConfiguration();
		diskStoreConfiguration.setPath("/my/path/dir");
		Configuration configuration=new Configuration();
		configuration.addDiskStore(diskStoreConfiguration);
		CacheManager mgr = new CacheManager(configuration);
		*/		
		Cache cache = singletonManager.getCache("cacheLayer");
		if(cache.get(cacheKey)==null)
		 {
			this.logger.info("Cache Empty:::");
			Object result=this.getValueUsingReflection(object,methodName,parameters,paramterTypes);
			Element element =new Element(cacheKey,result);
			element.setTimeToLive(timeToLive);
			element.setTimeToIdle(timeToIdle);
			cache.put(element);
			return result;
		 }		
		else
		{
			this.logger.info("Cache Populated:::");
			return cache.get(cacheKey).getObjectValue();
		}
	}
		
}
