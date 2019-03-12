package com.looser.enterprise.problem.solving;

public class RunLengthEncoding {

	public static String runLength(String input) {
		char[] inputCh = input.toCharArray();
		StringBuilder builder = new StringBuilder();
		int i=0;
		while(i < inputCh.length) {
			int count =0;
			while(i+count+1<inputCh.length && inputCh[i+count]==inputCh[i+count+1]) {
				count++;
			}
			builder.append(inputCh[i]);
			builder.append(count+1);
			count++;
			i= i+count;
			
		}
		return builder.toString();
	}
	public static void main(String[] args) {
		String result = "aaaabbeeeeebbcccc";
		System.out.println(runLength(result));
		System.out.println("aaaaaaa");
	}
	
}
