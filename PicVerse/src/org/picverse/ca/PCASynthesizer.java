package org.picverse.ca;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.picverse.util.ArrayUtlity;
import org.picverse.util.DecBinNumberSystem;

public class PCASynthesizer {
	
	int _noOfCAs;
	int _noOfRules;
	int _pcaTable[][];
	int _currentRuleTable[][];
	int _dontCareReplacer;
	File _file; 
	String _seed;
	String _program;
	
	public PCASynthesizer(int noOfCAs, int noOfRules,boolean dontCareZero, String filename, String programName, boolean isbw)
	{
		this._noOfCAs = noOfCAs;
		if(isbw)
			this._noOfRules = noOfRules;
		else
			this._noOfRules = noOfRules*3*8;
		this._pcaTable = new int[this._noOfCAs][this._noOfRules];
		this._currentRuleTable = new int[this._noOfRules][8];
		for(int i = 0; i<this._noOfCAs; i++)
		{
			for(int j = 0; j<this._noOfRules;j++)
			{
				_pcaTable[i][j] = 0;
			}
		}
		if(dontCareZero)
			this._dontCareReplacer = 0;
		else
			this._dontCareReplacer = 1;
		
		initCurrentRuleTable();
		_file = new File(filename);
		_program = programName;
	}
	
	private void initCurrentRuleTable()
	{
		for(int i = 0; i<_noOfRules; i++)
		{
			for(int j = 0; j<8; j++)
			{
				_currentRuleTable[i][j] = -1;
			}
		}
	}
	
	@SuppressWarnings("finally")
	public String synthesize()
	{
		String line = "";
		String output = "";
		int[] rmts;
		int count = 0;
		int[] outputArray;
		int flag = 0;
		String prog = "";
		String temp = "";
		int counter = 0;
		try 
		{
			Scanner scn = new Scanner(_file);
			if(scn.hasNextLine())
				line = scn.nextLine();
			else
				System.exit(1);
			while(true)
			{
				count = count + 1;
				output = "";
				if(count == 1 && flag == 0)
				{
					_seed = line;
					flag = 1;
				}
				if(!scn.hasNextLine())
				{
					break;
				}else
				{
					temp = scn.nextLine();
					String outputWithoutBlank[] = temp.split(" ");
					for(int i = 0; i<outputWithoutBlank.length; i++)
					{
						output = output + outputWithoutBlank[i];
					}
					outputArray = ArrayUtlity.convertToBinaryArray(output);
				}
				rmts = getRMTs(line);
				
				for(int i = 0; i<this._noOfRules;i++)
				{
					if(_currentRuleTable[i][7 - rmts[i]] == -1)
					{
						_currentRuleTable[i][7 - rmts[i]] = outputArray[i];
						
					}
					else
					{
						if(_currentRuleTable[i][7 - rmts[i]] != outputArray[i])
						{
							
							int [] ruleVector = buildRuleVectorFromCurrentRuleTable();
							prog = prog + "< ";
							for(int j = 0; j<ruleVector.length; j++)
							{
								prog = prog + ruleVector[j] + " ";
								counter = counter + 1;
							}
							prog = prog + ">#" + (count - 1) + "\n";
							counter = counter + 1;
							count = 1;
							initCurrentRuleTable();
							_currentRuleTable[i][7 - rmts[i]] = outputArray[i];
							
						}else {
							
						}
					}
				}
				line = temp;
			}
			int [] ruleVector = buildRuleVectorFromCurrentRuleTable();
			prog = prog + "< ";
			
			for(int j = 0; j<ruleVector.length; j++)
			{
				prog = prog + ruleVector[j] + " ";
				counter = counter + 1;
				
			}
			if(count == 1)
				count = count + 1;
			prog = prog + ">#" + (count -1) + "\n";
			counter = counter + 1;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				FileWriter fw = new FileWriter(new File(_program));
				fw.write(prog);
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				return _seed;
			}
		}
	}
	
	private int[] getRMTs(String line)
	{
		String str[] = line.split(" ");
		String lineWithoutBlank = "";
		int temp[] = new int[3];
		int rmts[] = new int[this._noOfRules];
		for(int i = 0; i<str.length; i++)
		{
			lineWithoutBlank = lineWithoutBlank + str[i];
		}
		for(int i = 0; i<lineWithoutBlank.length(); i++)
		{
			if(i == 0)
			{
				temp[0] = 0;
			}else {
				temp[0] = lineWithoutBlank.charAt(i - 1) - 48;
			}
			temp[1] = lineWithoutBlank.charAt(i) - 48;
			if(i == lineWithoutBlank.length() - 1)
				temp[2] = 0;
			else
				temp[2] = lineWithoutBlank.charAt(i+1) - 48;
			int dec = DecBinNumberSystem.binToDec(temp, true);
			rmts[i] = dec;
		}
		return rmts;
	}
	
	private int[] buildRuleVectorFromCurrentRuleTable()
	{
		int ruleVector[] = new int[this._noOfRules];
		for(int i = 0; i<this._noOfRules;i++)
		{
			for(int j = 0; j<8; j++)
			{
				if(this._currentRuleTable[i][j] == -1)
					this._currentRuleTable[i][j] = this._dontCareReplacer;
			}
		}
		for(int i = 0; i<this._noOfRules;i++)
		{
			ruleVector[i] = DecBinNumberSystem.binToDec(this._currentRuleTable[i],true);
		}
		return ruleVector;
	}
	
	
}
