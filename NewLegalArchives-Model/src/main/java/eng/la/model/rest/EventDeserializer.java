package eng.la.model.rest;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
 



public class EventDeserializer extends JsonDeserializer<AbstractRest> {
	@Override
    public AbstractRest deserialize(JsonParser jp,  DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        ObjectNode root = (ObjectNode) mapper.readTree(jp);
        Class<? extends AbstractRest> instanceClass = null;
		try {
			instanceClass = (Class<? extends AbstractRest>) Class.forName( root.get("type").asText() );
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}         
        return mapper.readValue(root.toString(), instanceClass);
    }
}