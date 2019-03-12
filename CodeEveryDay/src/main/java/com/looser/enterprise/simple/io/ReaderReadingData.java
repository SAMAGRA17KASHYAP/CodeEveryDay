package com.looser.enterprise.simple.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class ReaderReadingData {

	public static void main(String[] args) {
		File file = new File("ReaderInAction.java");
		try(Reader reader = new FileReader("ReaderInAction.java")) {
			BufferedReader  br = new BufferedReader(reader);
			String line = br.readLine();
			System.out.println(line);
			while(line != null) {
				line = br.readLine();
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
