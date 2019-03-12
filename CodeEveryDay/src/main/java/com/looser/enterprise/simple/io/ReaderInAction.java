package com.looser.enterprise.simple.io;

import java.io.File;

public class ReaderInAction {

	public static void main(String[] args) {
		/**
		 * Check for existence of file
		 * */
		File file = new File("ReaderInAction.java");
		System.out.println(file.exists());
		System.out.println(file.getName());
		
	}
}
