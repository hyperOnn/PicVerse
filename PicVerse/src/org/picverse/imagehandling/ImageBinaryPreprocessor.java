package org.picverse.imagehandling;

import java.io.FileWriter;
import java.io.IOException;

import org.picverse.constants.Constants;
import org.picverse.util.FileUtil;

public class ImageBinaryPreprocessor {
	String _sanitizedContent = "";
	
	private ImageBinaryPreprocessor(String filename)
	{
		String content = FileUtil.readFileContentToString(filename);
		
		_sanitizedContent = content.replace(Constants.RGB_SEPARATOR+" "," ");
		_sanitizedContent  = _sanitizedContent.replace(Constants.PIXEL_SEPARATOR+" "," ");
		_sanitizedContent = _sanitizedContent.trim();
		_sanitizedContent = _sanitizedContent.replace("\n" + " ","\n");
		_sanitizedContent = _sanitizedContent.replace(Constants.RGB_SEPARATOR+"\n", "\n");
		_sanitizedContent = _sanitizedContent.split(Constants.RGB_SEPARATOR)[0];
		
	}
	
	private void writeSanitizedContentToFile(String filename)
	{
		FileWriter myWriter;
		try {
			myWriter = new FileWriter(filename);
			myWriter.write(_sanitizedContent);
			myWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void preprocessBinaryFile(String inputFileName, String outputFileName)
	{
		ImageBinaryPreprocessor ibp = new ImageBinaryPreprocessor(inputFileName);
		ibp.writeSanitizedContentToFile(outputFileName);
	}
}
