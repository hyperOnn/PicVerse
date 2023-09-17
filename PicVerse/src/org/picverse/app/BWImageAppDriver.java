package org.picverse.app;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.picverse.ca.PCASynthesizer;
import org.picverse.imagehandling.BWImageExtractor;
import org.picverse.util.PCAFileCompressor;


public class BWImageAppDriver {

	//run this code for bw images 
	public static void main(String args[]) throws IOException
	{
		String inputImage = "bwImg1.jpg"; //change this line as your image changes
		String binaryFile = "bwImg1.txt"; //change this line too
		BWImageExtractor bwie   = new BWImageExtractor(inputImage);
		bwie.extract(binaryFile);
		//change below line too
		//only change the file name nothing else
		PCASynthesizer pcas = new PCASynthesizer(bwie.getHeight(), bwie.getWidth(), true, binaryFile,"ca_bwImg1.txt",true);
		String seed = pcas.synthesize();  
		PCAFileCompressor pfc = new PCAFileCompressor("ca_bwImg1.txt", "compressed_ca_bwImg1.txt", seed);
		pfc.compress();
		System.out.println("Done");
	}
}
