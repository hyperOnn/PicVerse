package org.picverse.imagehandling;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.picverse.constants.Constants;
import org.picverse.util.DecBinNumberSystem;
import org.picverse.util.FileUtil;


/**
 * This class constructs an image out of a binary file in the following pattern
 * 
 * @author Shiladitya Munshi
 *
 */
public class ImageConstructor {

	String _inpFileName;
	int _hight;
	int _width;
	
	public int getHight()
	{
		return _hight;
	}
	
	public int getWidth()
	{
		return _width;
	}
	
	public void setHight(int h)
	{
		_hight = h;
	}
	
	public void setWidth(int w)
	{
		_width = w;
	}
	
	private ImageConstructor(String file)
	{
		_inpFileName = file;
	}
	
	public static void construct(String inputFileName)
	{
		ImageConstructor ic = new ImageConstructor(inputFileName);
		String str = FileUtil.readFileContentToString(inputFileName);
		
		
		String lines[] = str.split("\n");
		ic.setHight(lines.length);
		ic.setWidth(lines[0].split(Constants.PIXEL_SEPARATOR).length);
		
		BufferedImage image = new BufferedImage(ic.getWidth(), ic.getHight(),BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		
		int rgb[] = new int[3];
		for(int i = 0; i<lines.length; i++)
		{
			String pixelsInLine[] = lines[i].split(Constants.PIXEL_SEPARATOR); // 
			for(int j = 0; j<pixelsInLine.length;j++)
			{
				String rgbBinWithGap[] = pixelsInLine[j].split(Constants.RGB_SEPARATOR);
				int rgbCount = 0;
				for(int k = 0; k<rgbBinWithGap.length; k++)
				{
					int bin[] = new int[8];
					int count = 0;
					for(int m = 0; m<rgbBinWithGap[k].length(); m++)
					{
						char ch = rgbBinWithGap[k].charAt(m);
						if( ch != ' ')
						{
							bin[count] = ch - 48;
							count = count + 1;
						}
					}
					rgb[rgbCount] = DecBinNumberSystem.binToDec(bin, true);
					rgbCount = rgbCount + 1;
					
				}
				
				g.setColor(new Color(rgb[0], rgb[1], rgb[2]));
				g.drawLine(j, i, j, i);
			}
		}
		try {
			ImageIO.write(image, "png", new File("reconstructed.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
