package innovitics.azimut.businessutilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.DiskStorePathManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;

@Component
public class CachingLayer {

	@Autowired CacheManager singletonManager;
	protected static final Logger logger = LoggerFactory.getLogger(CachingLayer.class);

	public Object getValueIfExisting (Object object,String methodName,Object[] parameters,Class<?>[] paramterTypes,String cacheKey,int timeToLive,int timeToIdle)
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
			Object result=this.getValueToCacheUsingReflection(object,methodName,parameters,paramterTypes);
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
				
		private Object getValueToCacheUsingReflection(Object object,String methodName,Object[] parameters,Class<?>[] paramterTypes)
		{				
			try 
			{
				Method method = object.getClass().getDeclaredMethod(methodName,paramterTypes);
				this.logger.info("Method Name::"+methodName);
				Object result = method.invoke(object, parameters); 
				return result;
			}
			catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) 
			{
				this.logger.info("Could not return the method invocation");
				exception.printStackTrace();
				return null;
			}  
		}
}
