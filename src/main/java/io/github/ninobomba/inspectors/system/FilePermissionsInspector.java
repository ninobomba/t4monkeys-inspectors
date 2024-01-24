package io.github.ninobomba.inspectors.system;

import io.github.ninobomba.inspectors.IResourceInspector;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Builder
@Slf4j
public class FilePermissionsInspector implements IResourceInspector {
	
	private final List < String > files;
	
	@Override
	public boolean isAvailable ( ) {
		if ( Objects.isNull ( files ) || files.isEmpty ( ) ) {
			log.warn ( "FilePermissionsInspector::isAvailable() !: file list is null or empty" );
			return false;
		}
		
		for ( String file : files ) {
			Path filePath = Paths.get ( file );
			
			boolean isReadable   = Files.isReadable ( filePath );
			boolean isWritable   = Files.isWritable ( filePath );
			boolean isExecutable = Files.isExecutable ( filePath );
			boolean isDirectory  = Files.isDirectory ( filePath );
			
			log.trace ( "FilePermissionsInspector::isAvailable() _: File-Path: {}, AbsolutePath: {} isDirectory: {} readable: {} writable: {} executable: {}",
					file,
					filePath.toFile ( ).getAbsolutePath ( ),
					isDirectory,
					isReadable,
					isWritable,
					isExecutable
			);
			
			if ( ! isReadable && ! isWritable ) {
				log.warn ( "FilePermissionsInspector::isAvailable() _: File-Path: {}, AbsolutePath: {} should have at least read and write permissions", file, filePath.toFile ( ).getAbsolutePath ( ) );
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public String toString ( ) {
		if ( Objects.isNull ( files ) || files.isEmpty ( ) )
			return "Files is empty or null : " + files;
		
		var fileList = new StringBuilder ( );
		files.forEach ( fileList::append );
		
		return fileList.toString ( );
	}
}
