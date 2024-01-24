import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.nio.charset.StandardCharsets;

public interface LocalAbstractSetupTest {
	
	@SneakyThrows
	public static < T > T getBodyFromPath ( String path, TypeReference < T > type ) {
		ObjectMapper objectMapper = new ObjectMapper ( );
		objectMapper.disable ( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES );
		
		try ( var inputStream = LocalAbstractSetupTest.class.getClassLoader ( ).getResourceAsStream ( path ) ) {
			assert inputStream != null;
			return objectMapper
					.readValue ( IOUtils.toString ( inputStream, StandardCharsets.UTF_8 ).replaceAll ( "[\\r\\n]+", "" ), type );
		}
	}
	
}
