package org.picverse.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileUtil {

	public static String readFileContentToString(String fileName)
    {
 
        StringBuilder contentBuilder = new StringBuilder();
         try (Stream<String> stream
             = Files.lines(Paths.get(fileName),
                           StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
         catch (IOException e) {
             e.printStackTrace();
        }
         return contentBuilder.toString();
    }
	
	
}
