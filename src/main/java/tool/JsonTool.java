package tool;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * json工具
 */
public class JsonTool {
	public static ObjectMapper mapper = new ObjectMapper();

	public static void writeJson2File(String filename, Object obj) throws IOException {
		mapper.writeValue(new File(filename), obj);
	}

	public static <T> T readJsonFromFile(File src, Class<T> valueType) throws IOException {
		return mapper.readValue(src, valueType);
	}

	public static String obj2String(Object obj) throws JsonProcessingException {
		return mapper.writeValueAsString(obj);
	}

	public static <T> T string2obj(String content, Class<T> valueType) throws IOException {
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		JsonFactory factory = new JsonFactory();
		JsonParser jsonpParser = factory.createParser(content);
		if (jsonpParser.nextToken() == JsonToken.START_OBJECT) {
			return mapper.readValue(jsonpParser, valueType);
		} else {
			return null;
		}
	}
}
