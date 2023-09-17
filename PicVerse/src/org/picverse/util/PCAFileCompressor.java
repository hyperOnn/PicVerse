package org.picverse.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PCAFileCompressor {

	private File _origPCA;
	private File _compressedPCA;
	private String _seed;
	public PCAFileCompressor(String originalPCAFileName, String compressedPCAFileName, String seed) {
		_origPCA = new File(originalPCAFileName);
		_compressedPCA = new File(compressedPCAFileName);
		_seed = seed;
	}

	public void compress() throws IOException {
		Scanner sc = new Scanner(_origPCA);
		double origRowCount = 1.0;
		double counter = 0;
		double origColCount = 0.0;
		FileWriter fw = new FileWriter(_compressedPCA);
		fw.append(_seed + "\n");
		while (sc.hasNextLine()) 
		{
			String line = sc.nextLine();
			origRowCount = origRowCount + Integer.parseInt(line.split("#")[1]);
			String rv = line.split("#")[0];
			rv = rv.substring(2, rv.indexOf(" >"));
			String[] ruleArray = rv.split(" ");
			for (int i = 0; i < ruleArray.length - 1; i++) {
				if (!ruleArray[i].equals("@")) {
					int matchCnt = 1;
					String key = ruleArray[i];
					for (int j = i + 1; j < ruleArray.length; j++) {
						if (key.equals(ruleArray[j])) {
							matchCnt = matchCnt + 1;
						} else
							break;
					}
					if (matchCnt >= 3) {
						for (int j = i + 1; j < i + matchCnt; j++) {
							if (j != ruleArray.length) {
								// System.out.println("Value of i = " + i);
								// System.out.println("Value of match count = " + matchCnt);
								ruleArray[j] = "@";
							}
						}
						ruleArray[i] = ruleArray[i] + "@" + matchCnt;
					}

				}
			}
			String strToWrite = "";
			for (int i = 0; i < ruleArray.length; i++) {
				if (!ruleArray[i].equals("@")) {
					if (ruleArray[i].contains("@"))
						counter = counter + (3.0 * 8.0);
					else
						counter = counter + 8.0;
					if(i == ruleArray.length - 1)
						strToWrite = strToWrite + ruleArray[i] + "\n";
					else
						strToWrite = strToWrite + ruleArray[i] + " ";
				}

			}
			if(!strToWrite.contains("\n"))
				strToWrite = strToWrite + "\n";

			if(origColCount == 0.0)
				origColCount = ruleArray.length;
			fw.append(strToWrite);
		}
		fw.close();
		
	}
}
