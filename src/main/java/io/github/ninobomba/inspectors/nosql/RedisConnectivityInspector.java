package io.github.ninobomba.inspectors.nosql;

import io.github.ninobomba.inspectors.IResourceInspector;
import io.lettuce.core.RedisClient;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Builder
public class RedisConnectivityInspector implements IResourceInspector {
	private final String host;
	private final String port;
	private final String db;
	
	@Override
	public boolean isAvailable ( ) {
		String uri = ""
				.concat ( "redis://" )
				.concat ( host )
				.concat ( ":" )
				.concat ( port )
				.concat ( "/" )
				.concat ( db );
		
		log.debug ( "RedisConnectivityInspector::isAvailable() _: Attempting to connect to redis server: {}", uri );
		
		boolean isConnectionAvailable = false;
		try ( var client = RedisClient.create ( uri ) ) {
			try ( var connection = client.connect ( ) ) {
				isConnectionAvailable = Objects.nonNull ( connection ) && connection.isOpen ( );
			} catch ( Exception e ) {
				log.error ( "RedisConnectivityInspector::isAvailable() !: error while opening redis connection" );
			}
		}
		
		log.info ( "RedisConnectivityInspector::isAvailable() _: is db available: {}", isConnectionAvailable );
		
		return isConnectionAvailable;
	}
	
	@Override
	public String toString ( ) {
		return "uri: "
				.concat ( "redis://" )
				.concat ( host )
				.concat ( ":" )
				.concat ( port )
				.concat ( "/" )
				.concat ( db );
	}
}
