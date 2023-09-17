package org.picverse.imagehandling;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BWImageExtractor {
	private File _inputFile;
	private int _height;
	private int _width;

	public BWImageExtractor(String inputFileName) {
		this._inputFile = new File(inputFileName);
		this._height = -1;
		this._width = -1;
	}

	public int getHeight() {
		return this._height;
	}

	public int getWidth() {
		return this._width;
	}

	@SuppressWarnings("finally")
	private Raster loadImageRaster() {
		BufferedImage buf_image = null;
		try {
			buf_image = ImageIO.read(_inputFile);
			this._height = buf_image.getHeight();
			this._width = buf_image.getWidth();
			buf_image = binarizeImage(buf_image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return buf_image.getData(); // return raster
		}
	}

	private BufferedImage binarizeImage(BufferedImage img_param) {
		BufferedImage image = new BufferedImage(img_param.getWidth(), img_param.getHeight(),
				BufferedImage.TYPE_BYTE_BINARY);
		Graphics g = image.getGraphics();
		g.drawImage(img_param, 0, 0, null);
		g.dispose();
		return image;
	}

	public void extract(String fileToWrite) {
		Raster ras = loadImageRaster();
		int[][] binArray = new int[ras.getHeight()][ras.getWidth()];
		int row = ras.getHeight() - 1;
		int column = 0;
		for (int i = 0; i < ras.getWidth(); i++) {
			for (int j = 0; j < ras.getHeight(); j++) {
				binArray[row][column] = ras.getSample(i, j, 0);
				row = row - 1;
			}
			row = ras.getHeight() - 1;
			column = column + 1;
		}
		File outFile = new File(fileToWrite);
		FileWriter fw = null;
		try {
			fw = new FileWriter(outFile);
			for (int i = 0; i < ras.getHeight(); i++) {
				for (int j = 0; j < ras.getWidth(); j++) {
					if (binArray[i][j] == 0)
						fw.append("0" + " ");
					else
						fw.append("1" + " ");

				}
				if (i != ras.getHeight())
					fw.append("\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

	}

}
