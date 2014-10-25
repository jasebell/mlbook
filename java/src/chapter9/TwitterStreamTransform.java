package chapter9;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.integration.transformer.MessageTransformationException;

public class TwitterStreamTransform {
	private ObjectMapper mapper = new ObjectMapper();

	public String transform(String payload) {
		try {
			StringBuilder sb = new StringBuilder();

			Map<String, Object> tweet = mapper.readValue(payload,
					new TypeReference<Map<String, Object>>() {
					});
			sb.append(tweet.get("created_at").toString());
			sb.append("|");
			sb.append(tweet.get("text").toString());
			return sb.toString();
		} catch (IOException e) {
			throw new MessageTransformationException(
					"[MLBook] - Cannot work on this tweet: " + e.getMessage(),
					e);
		}
	}
}
