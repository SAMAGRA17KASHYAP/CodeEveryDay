package com.looser.enterprise.problem.solving;

import java.util.Objects;

public class CapitalizeSmall {
	public static String javaToCPP(String input) {
		if (Objects.isNull(input))
			return input;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < input.toCharArray().length; i++) {
			char currentChar = input.charAt(i);
			if (currentChar >= 'A' && currentChar <= 'Z') {
				builder.append('_');
				builder.append((char) (currentChar - ('A' - 'a')));
			} else {
				builder.append(currentChar);
			}
		}
		return builder.toString();
	}
	public static String cppToJava(String input) {
		if (Objects.isNull(input))
			return input;
		StringBuilder builder = new StringBuilder();
		String[] splited = input.split("_");
		builder.append(splited[0]);
		for (int i = 1; i < splited.length; i++) {
			builder.append(splited[i].toUpperCase().charAt(0));
			builder.append(splited[i].toLowerCase().substring(1));
		}
		return builder.toString();
	}
	public static void main(String[] args) {
		String str1 = "isDisabledThere";
		System.out.println(cppToJava(null));
		System.out.println(str1 + "::" + javaToCPP(str1));
		System.out.println(str1 + "::" + cppToJava(javaToCPP(str1)));
	}
}
