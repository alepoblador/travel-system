
/**
 * Implements an Edge, or an ordered pair, which can be collected in a List
 * Uses hashcodes of hashmap elements
 * 
 * @author Alessandra Poblador (acp2164)
 *
 */


public class Edge implements java.io.Serializable {
	
	City city1;	   // Each edge holds the in and out city.
	City city2;
	String key1;   // City objects converted to Strings to be used in searches
	String key2;
	
	public Edge( City a, City b )
	{
		city1 = a;
		city2 = b;
		key1 = a.toString();
		key2 = b.toString();
	}

	public String toString ( )
	{
		String edge = city1.name+"\t\t\t"+city2.name;
		return "\n"+edge;
	}
	
	public void setWeight ( double w )
	{
		return;
	}
	
	
}
