package org.picverse.util;

public class MathUtil {

	public static int log2(int x) {
        return (int)(Math.log(x) / Math.log(2));
    }
 
    public static void main(String[] args) {
        int x = 32;
 
        int log2x = log2(x);
        System.out.println(log2x);  
    }
	
}
