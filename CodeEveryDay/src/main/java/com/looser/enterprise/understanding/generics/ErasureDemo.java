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

 */
public class ErasureDemo<T> {

	public  void  print(List<T> demo) {
	}
}