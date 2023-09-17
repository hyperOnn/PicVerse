package org.picverse.app;

import java.io.IOException;

import org.picverse.ca.PCASynthesizer;
import org.picverse.imagehandling.ImageBinaryPreprocessor;
import org.picverse.imagehandling.ImageConstructor;
import org.picverse.imagehandling.ImageExtractor;
import org.picverse.util.PCAFileCompressor;

public class ColoredImageAppDriver {

	//Run this code for colored images
	public static void main(String[] args) throws IOException {
		String imageToEncrypt = "img_101.png"; //change this line
		String rawExtractedFile = "img_101.txt"; //change this line
		String preprocessedExtractedFile = "img_101-sanitized.txt";//change this line
		ImageExtractor extractorObj = ImageExtractor.extract(imageToEncrypt, rawExtractedFile);
		int noOfCAs = extractorObj.getHeight() - 1;  
		int noOfRules = extractorObj.getWidth();
		ImageBinaryPreprocessor.preprocessBinaryFile(rawExtractedFile, preprocessedExtractedFile);
		//change below line
		//change the file name only
		PCASynthesizer obj = new PCASynthesizer(noOfCAs, noOfRules, true, preprocessedExtractedFile,"ca_img_101.txt",false);
		String seed = obj.synthesize();
		PCAFileCompressor pfc = new PCAFileCompressor("ca_img_101.txt", "compressed_ca_img_101.txt",seed);
		pfc.compress();
		System.out.println("Done");
		
	}

}