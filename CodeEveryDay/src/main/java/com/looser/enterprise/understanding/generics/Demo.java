package com.looser.enterprise.understanding.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.looser.enterprise.understanding.generics.model.Employee;
import com.looser.enterprise.understanding.generics.model.Partner;
import com.looser.enterprise.understanding.generics.model.Person;

public class Demo {
	
	/**
	 * --------General Comments
	 * usage of ? vs T
	 * 
	 * Example of usage of T and ? with methods
	 * 
	 * <pre>
	 * {@code
	 * 		public static void acceptPerson(List<? extends Person> persons)
	 * 		{
	 * 		}
	 * 		public<T extends Person> static void acceptPerson(List<T> persons)
	 * 		{
	 * 		}
	 * 
	 * }
	 * </pre>
	 * 
	 * Example of user of  ? with class
	 * ?arg in Test constructor won't make any sense
	 * that is here name type parameter is necessary
	 * <pre>
	 * {@code
	 * 		class Test<?>
	 * 		{
	 * 			Test(? arg)
	 * 			{
	 *   
	 * 			}
	 * 		}
	 * 
	 * }
	 * </pre>
	 * 
	 * Conclusion:
	 * 		Use Named type parameter like T in case like class declaration where 
	 * you will be needing it to refer it further
	 * 		and user ? wildcard in case like method declaration
	 * -----------------------------
	 * 
	 * List<? super Person> personList
	 * 
	 * 	This puts restriction that  personList will be able to add anything of type
	 *  person or its parent
	 *  
	 *  ? super allows us to add value
	 * 
	 * -----------------------------
	 * List<?> vs List<? extends Object> vs List<Object>
	 * 
	 * ? and ? extends Object are the same thing
	 * <pre>
	 * {@code
	 *  List<?> personList = someMethodCall();
	 *  presoneList.add(new Person("test",45));//won't compile as we can't say what type
	 *  // of list it is 
	 *  personList.add(null);//will only work
	 * }
	 * </pre>
	 * */

	public static void main(String[] args) {

	}
	

	private static void exampleForListBeingNotCovarient() {
		List<Partner>partners = new ArrayList<>();
		partners.add(new Partner("name1", 55, "logo1"));
		partners.add(new Partner("name2", 52, "logo2"));
		//Uncomment: to see type mismatch error
		//Doesn't compile because lists are not covarient in java
		//List<Person>person = partners;
		List<? extends Person>person = partners;
		
		//Uncomment: Below to see compile time error
		//Doesn't work because as per lang construct now
		//person can hold List of Person or Partner or an Employee
		//if it is list of person or partner then no issues
		// But in case if person is holding list of Employee then we are in trouble
		//person.add(new Employee("empname", 12, "327256"));
		//person.add(new Person("empname", 12));
	}
	
	//Compiles because arrays are covarient in java
	public static void demoArrayStoreException() {
		Partner[]partners = new Partner[2];
		partners[0]= new Partner("name1", 55, "logo1");
		partners[1]= new Partner("name2", 52, "logo2");
		
		Person[] person = partners;
		
		person[0] = new Employee("empname", 12, "327256");
	}
	
}
