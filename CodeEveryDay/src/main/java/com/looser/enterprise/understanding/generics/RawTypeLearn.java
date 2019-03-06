package com.looser.enterprise.understanding.generics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RawTypeLearn {

	public static void main(String[] args) {
		legacyCode();
	}
	
	public static void legacyCode()
	{
		/**
		 * Here List list = new ArrayList(); is a raw type
		 * 
		 * RawType: is a usage of generic type that doesn't 
		 * have generic parameters
		 * 
		 * */
		List list = new ArrayList();
		list.add("abc");
		list.add(1);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			System.out.println(object);
		}
		/**
		 * Below line works for reasons of backword compatibility
		 * */
		List<String> listString = list;
		
		
		/**
		 * **********************
		 * 						*
		 * List vs List<Object>	*
		 * 						*
		 * **********************
		 * 
		 * List list = new ArrayList();
		 * 
		 * List<Object> objectList = new ArrayList();
		 * 
		 * listString = list;// Works for raw type 
		 * Raw type can be assigned to generic type and vice versa
		 * 
		 * listString = objectList;// Doesn't work
		 * objectList is not a raw type it is a generic type
		 * 
		 * Side effect of Rawtype
		 * 	ClassCastException could be very common if this is not handled properly because
		 * compiler do not do checking for r
		 * 
		 * */
	}

}

