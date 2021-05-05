package it.engineering;

public class TestFactory
{
	
	public static java.util.Collection generateCollection()
	{
		java.util.Vector collection = new java.util.Vector();
		collection.add(new PersonBean("Ted", 20) );
		collection.add(new PersonBean("Jack", 34) );
		collection.add(new PersonBean("Bob", 56) );
		collection.add(new PersonBean("Alice",12) );
		collection.add(new PersonBean("Robin",22) );
		collection.add(new PersonBean("Peter",28) );
		return collection;
	}

}