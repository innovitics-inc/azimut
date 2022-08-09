package innovitics.azimut;

import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;

import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.utilities.exceptionhandling.FileUploadExceptionAdvice;

@SpringBootApplication

@ComponentScan(basePackages = {"innovitics.azimut.utilities"})
@ComponentScan(basePackages= {"innovitics.azimut.models"})
@ComponentScan(basePackages= {"innovitics.azimut.businessmodels"})
@ComponentScan(basePackages = {"innovitics.azimut.businessutilities"})
@ComponentScan(basePackages= {"innovitics.azimut.exceptions"})
@ComponentScan(basePackages= {"innovitics.azimut.controllers"})
@ComponentScan(basePackages= {"innovitics.azimut.rest"})
@ComponentScan(basePackages = {"innovitics.azimut.repositories"})
@ComponentScan(basePackages = {"innovitics.azimut.services"})
@ComponentScan(basePackages = {"innovitics.azimut.jobs"})

@EntityScan("innovitics.azimut.models")

@EnableJpaRepositories(basePackages = {"innovitics.azimut.repositories"},repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class)

@ConfigurationPropertiesScan(basePackages = {"innovitics.azimut.configproperties"})

@EnableConfigurationProperties(ConfigProperties.class) 

@EnableJpaAuditing
@EnableScheduling

@EnableCaching
public class AzimutApplication extends SpringBootServletInitializer {
	
	protected static final Logger logger = LoggerFactory.getLogger(AzimutApplication.class);
	public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "test");
		SpringApplication.run(AzimutApplication.class, args);
	}

	@Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	    return builder.sources(AzimutApplication.class);
	  }
	
	
	   @Bean
	    public MultipartResolver multipartResolver() {
	        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	        this.logger.info("File size:::");
	        multipartResolver.setResolveLazily(true);
	        multipartResolver.setMaxUploadSize(20000000);
	        return multipartResolver;
	    }
	   
	   
	   @Component
	   class MyRunner implements CommandLineRunner {

	       @Autowired
	       private Environment environment;

	       @Override
	       public void run(String... args) throws Exception {

	           System.out.println("Active profiles: " +
	                   Arrays.toString(environment.getActiveProfiles()));
	       }
	   }


	   @Component
	   @Profile(value="test & !dev")
	   class MyRunner3 implements CommandLineRunner {

	       @Override
	       public void run(String... args) throws Exception {

	           System.out.println("In development");
	       }
	   }

	   @Component
	   @Profile(value="dev & !test")
	   class MyRunner4 implements CommandLineRunner {

	       @Override
	       public void run(String... args) throws Exception {

	           System.out.println("In testing");
	       }
	   }
	   

}
