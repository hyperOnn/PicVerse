package org.picverse.util;

public class DecBinNumberSystem {

	/**
	 * Decimal to Bin converter
	 * @param dec - Decimal Number to be Converted to the Binary
	 * @param binSize - Target binary size
	 * @param reverse - true if target binary array index 0 is MSB, false if target binary array index 0 is LSB) 
	 * @return target binary array
	 * 
	 * Example decToBin(8,8,true) will return  0 0 0 0 1 0 0 0
	 *         decToBin(8,8,false) will return 0 0 0 1 0 0 0 0
	 */
	public static int[] decToBin(int dec, int binSize, boolean reverse)
	{
		int bin[] = new int[binSize];
		int pos = 0; 
		if(reverse)
			pos = bin.length - 1;
		for(int i = 0; i<bin.length; i++)
			bin[i] = 0;
		while(dec > 0)
		{
			bin[pos] = dec%2;
			dec= dec /2;
			if(reverse)
				pos = pos - 1;
			else
				pos = pos + 1;
		}
		return bin;
	}
	
	/**
	 * Bin to Dec converter
	 * @param binArray - array containing binary number
	 * @param reverse true if binArray[0] = MSB and false if binArray[0] = LSB
	 * 
	 *  For example: binToDec([1 0 0],true) will return 4; 
	 * 				 binToDec([1 0 0],false) will return 1;
	 * @return decimal no 
	 */
	public static int binToDec(int[] binArray, boolean reverse)
	{
		int dec = 0;
		
		if(!reverse)
		{
			for(int i = 0; i<binArray.length; i++)
			{
				dec = dec + binArray[i]*(int)Math.pow(2, i);
			}
		}else
		{
			for(int i = 0; i<binArray.length; i++)
			{
				dec = dec + binArray[i]*(int)Math.pow(2, (binArray.length - 1) - i);
			}
		}
		return dec;
	}


	public static void main(String args[])
	{
		System.out.println(ArrayUtlity.convertToString(DecBinNumberSystem.decToBin(60, 8, true)));
		int arr[] = {0,0,1,1,1,1,0,0};
		System.out.println(DecBinNumberSystem.binToDec(arr, true));
	}
}
