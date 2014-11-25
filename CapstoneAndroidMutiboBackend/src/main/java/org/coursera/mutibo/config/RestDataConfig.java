package org.coursera.mutibo.config;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.coursera.mutibo.client.MutiboServiceApi;
import org.coursera.mutibo.json.ResourcesMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.hateoas.Resources;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Configuration
@Import(RepositoryRestMvcConfiguration.class)
public class RestDataConfig extends RepositoryRestMvcConfiguration {
	
	@Override
	protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		super.configureRepositoryRestConfiguration(config);
		try {
			config.setBaseUri(new URI(MutiboServiceApi.MUTIBO_SERVICE_PATH));
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
//	@Override
//	public ObjectMapper halObjectMapper(){
//		return new ResourcesMapper();
//	}
	
	// We are overriding the bean that RepositoryRestMvcConfiguration 
	// is using to convert our objects into JSON so that we can control
	// the format. The Spring dependency injection will inject our instance
	// of ObjectMapper in all of the spring data rest classes that rely
	// on the ObjectMapper. This is an example of how Spring dependency
	// injection allows us to easily configure dependencies in code that
	// we don't have easy control over otherwise.
	@Override
	protected void configureJacksonObjectMapper(ObjectMapper objectMapper) {
		SimpleModule module = new SimpleModule();
		module.addSerializer(serializer);
		objectMapper.registerModule(module);
	}
	
	
	// This anonymous inner class will handle conversion of the Spring Data Rest
	// Resources objects into JSON. Resources are objects that Spring Data Rest
	// creates with the entities it obtains from your entityRepository
	@SuppressWarnings("rawtypes")
	private JsonSerializer<Resources> serializer = new JsonSerializer<Resources>() {

		// We are going to register this class to handle all instances of type
		// Resources
		@Override
		public Class<Resources> handledType() {
			return Resources.class;
		}

		@Override
		public void serialize(Resources value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
			// Extracted the actual data inside of the Resources object
			// that we care about (e.g., the list of Video objects)
			Object content = value.getContent();
			// Instead of all of the Resources member variables, etc.
			// Just mashall the actual content (Videos) into the JSON
			JsonSerializer<Object> s = provider.findValueSerializer(content.getClass(), null);
			s.serialize(content, jgen, provider);
		}
	};
}
