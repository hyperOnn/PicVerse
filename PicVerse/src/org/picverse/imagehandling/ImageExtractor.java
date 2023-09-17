package org.picverse.imagehandling;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;

import javax.imageio.ImageIO;

import org.picverse.constants.Constants;
import org.picverse.util.ArrayUtlity;
import org.picverse.util.DecBinNumberSystem;

/**
 * This class extracts an image in binary format and writes to a text file
 * 
 *
 */
public class ImageExtractor {

	BufferedImage _image;
	int _width;
	int _height;
	String[][] _imgStore;

	public int getWidth()
	{
		return _width;
	}
	
	public int getHeight()
	{
		return _height;
	}
	/**
	 * Constructor, non exposed
	 * @param imgToEncrypt
	 */
	private ImageExtractor(String imgToEncrypt) {
		try {
			
			File input = new File(imgToEncrypt);
			_image = ImageIO.read(input);
			_width = _image.getWidth();
			_height = _image.getHeight();
			int count = 0;
			_imgStore = new String[_height][_width];

			for (int i = 0; i < _height; i++) {

				for (int j = 0; j < _width; j++) {

					count++;
					Color c = new Color(_image.getRGB(j,i));
					String s = ArrayUtlity.convertToString(DecBinNumberSystem.decToBin(c.getRed(), 8, true)) + Constants.PIXEL_SEPARATOR + ArrayUtlity.convertToString(DecBinNumberSystem.decToBin(c.getGreen(), 8, true)) + Constants.PIXEL_SEPARATOR + ArrayUtlity.convertToString(DecBinNumberSystem.decToBin(c.getBlue(), 8, true));
					_imgStore[i][j] = s;
					
				}
			}
			

		} catch (Exception e) {
		}
	}

	/**
	 * Writes the extracted images to the text file
	 * @param fileName
	 */
	public void writeToFile(String fileName) {
		FileWriter myWriter;
		try {
			myWriter = new FileWriter(fileName);
			for (int i = 0; i < _height; i++) {
				for (int j = 0; j < _width; j++) {
					myWriter.write(_imgStore[i][j]);
					myWriter.write(Constants.RGB_SEPARATOR);
					 
				}
				myWriter.write("\n");
			}
			myWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Exposed gate for the class
	 * @param imageToEncrypt
	 * @param rawExtractedFile
	 */
	public static ImageExtractor extract (String imageToEncrypt, String rawExtractedFile){
		ImageExtractor obj = new ImageExtractor(imageToEncrypt);
		obj.writeToFile(rawExtractedFile);
		return obj;
	}
	
}
