package org.picverse.util;

public class ArrayUtlity {

	public static String convertToString(int arr[])
	{
		String str = "";
		for(int i = 0; i<arr.length; i++)
			str = str + " " + arr[i];
		return str;
	}
	
	public static int[] copyArr(int arr[])
	{
		int[] arr1 = new int[arr.length];
		for(int i = 0; i<arr.length; i++)
			arr1[i] = arr[i];
		return arr1;
	}
	
	public static int[] convertToBinaryArray(String binaryString)
	{
		int [] arr = new int[binaryString.length()];
		for(int i = 0; i<binaryString.length(); i++)
		{
			arr[i] = binaryString.charAt(i) - 48;
		}
		return arr;
	}
	
	public static void main(String args[])
	{
		System.out.println(ArrayUtlity.convertToString(ArrayUtlity.convertToBinaryArray("0110")));
	}
}
