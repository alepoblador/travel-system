

/**
 * Implements a City which acts as a vertex on a graph
 * containing name of city, coordinates, and a list
 * of adjacent cities.
 * 
 * @author Alessandra Poblador (acp2164)
 *
 */

import java.util.*;

public class City implements java.io.Serializable {

	public String name, city, state;
	public final String DELIM = ", "; // used to split name into tokens
	public float latitude, longitude;
	public double weight, gpsDistance;
	public int inCount = 0, outCount = 0;
	public boolean hasBeenVisited = false;
	public boolean hasBeenSorted = false;
	ArrayList<City> connections;
		
	public City ( String n, float lat, float lon )
	{
		name = n;
		latitude = lat;
		longitude = lon;
		connections = new ArrayList<City>( 1 );
		
		//split name into separate strings for city and state
		String[] tokens = name.split(DELIM);
		city = tokens[0];
		try
		{
			state = tokens[1];
		} catch (ArrayIndexOutOfBoundsException a )
		{
			state = city;  // if state is not listed, duplicate city
		}
	}
	
	//used to add to this city's arraylist of connections
	public void addAdjacent ( City c )
	{
		connections.add ( c );
		
		//assign random weight to every connection
		double randomWeight = (double) (100 + (Math.random()* 1901) );
		connections.get( connections.size()-1 ).setWeight( randomWeight );
	}
	
	public void setWeight ( double d )
	{
		this.weight = d;
	}
	

	
	public String toString ()
	{
		String s = this.name;
		return s;
	}
	
	
	public boolean hasBeenSorted()
	{
		return hasBeenSorted;
	}
	
	// bubble sort arraylist of this city's connections in order of weight
	public void sortConnections()
	{		
		City hold;
		for ( int i = 0; i < this.connections.size()-1 ; i++)
		{
			for ( int j = 0; j < this.connections.size() - i - 1; j++ )
			{
				if ( this.connections.get( j ).weight > this.connections.get( j+1 ).weight )
				{
					hold = this.connections.get ( j );
					this.connections.set ( j, this.connections.get( j+1 ) );
					this.connections.set ( j+1, hold );					
				}
			}
		}

		//set sorted to true
		this.hasBeenSorted = true;
	}
	
	public void resetVisited()
	{
		for ( int i = 0; i < this.connections.size()-1 ; i++)
			this.connections.get( i ).hasBeenVisited = false;
	}
	
}
