package com.looser.enterprise.understanding.generics;

import java.util.List;

/**
 * **************** Type Erasure **************** Generics concept is introduced
 * in Java language to provide tighter type checks at compile time and to
 * support generic programming. The way to implement generics, the Java compiler
 * applies type erasure to: Replace all type parameters in generic types with
 * their bounds or Object if the type parameters are unbounded.
 * 
 * Example of what happened at compile time
 * 
 * List<String>->List List<String>[] -> List[] T without bounds -> Object T
 * extends Foo -> Foo
 * 
 * Below code won't compile because of type Erasure
 * 
 * <pre>
 * {@code
 * static void tesT(List<Integer> num)
	{
		
	}
	static void tesT(List<Long> num)
	{
		
	}
 * }
 * </pre>
 * 
 * 
 * ****************
 * Erasure in action
 * *********************
 * use javap to see discriptor of method generated we can see type information is not
 * there
 * command
 * :javap -s com.looser.enterprise.understanding.generics.ErasureDemo
 * 
 * Output:
 * 	Compiled from "ErasureDemo.java"
		public class com.looser.enterprise.understanding.generics.ErasureDemo<T> {
		  public com.looser.enterprise.understanding.generics.ErasureDemo();
		    descriptor: ()V
		
		  public void print(java.util.List<T>);
		    descriptor: (Ljava/util/List;)V
		}
**************************
*Implication of Erasure
**************************
*
*1. instanceOf doesn't work
*
*Because compiler uses type erasure, the runtime does not keep track of type parameters, so at runtime difference between Box<Integer> and Box<String> cannot be verified using instanceOf operator.

Box<Integer> integerBox = new Box<Integer>();

//Compiler Error:
//Cannot perform instanceof check against 
//parameterized type Box<Integer>. 
//Use the form Box<?> instead since further 
//generic type information will be erased at runtime
if(integerBox instanceof Box<Integer>) { }
***************************************
*2. You can't create exception of generic type
**************************************
*	https://www.mscharhag.com/java/java-exceptions-and-generic-types
*
********************************************************
*3. Performance Impact
********************************************************
*int[] vs Integer[](internally used by ArrayList)
*Integer is an derived class of java.lang.Object. It takes very larger space in comparison
*to int.
*Integer brings a levl of indirection. ArrayList no longer hold data, it merely holds pinter
*which points to somewhere in heap. This makes caching tough for CPU in order to have
*good performance
*

 */
public class ErasureDemo<T> {

	public  void  print(List<T> demo) {
	}
}