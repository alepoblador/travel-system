
/**
 * Implements a graph using ArrayLists and HashMaps
 * 
 * @author Alessandra Poblador (acp2164)
 *
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph implements java.io.Serializable {

	ArrayList<City> startsArray;
	HashMap<String, City> startsHash;
	ArrayList<Edge> edges;
	
	public Graph( )
	{
		startsHash = null;
		edges = null;
	}
	
	public Graph( int n )
	{
		startsArray = new ArrayList<City>( n );
		startsHash = new HashMap<String, City>( n );
		edges = new ArrayList<Edge>( );
	}
		
	public void add( City c )
	{
		startsArray.add( c );
		startsHash.put( c.name , c );		
	}
	
	public void addEdge ( City a, City b )
	{
		edges.add ( new Edge ( a, b ) );
	}
	
	
	public City getRandomCity ( )
	{
		int randomPosition = (int)( Math.random() * startsArray.size() ); //random position in list of cities
		City randomCity = startsArray.get( randomPosition );

		return randomCity;

	}
	
	public void addCitiesFromFile( File f ) throws FileNotFoundException
	{
		Scanner scan = new Scanner ( f );
		String skip = scan.nextLine();   //skip first line holding N
		
		String name;
		float lat, lon;
		
		while ( scan.hasNextLine() )
		{
			name = scan.nextLine();			
			lat = Float.parseFloat( scan.nextLine() );
			lon = Float.parseFloat( scan.nextLine() );
			
			City c = new City ( name, lat, lon );
			this.add( c );
		}
	}
	
	public void assignConnections()
	{
		Iterator it = this.startsHash.values().iterator(); //go through all cities
		int randomConnections;
		
		while ( it.hasNext() )
		{
			randomConnections = ( 2 + (int)(Math.random()*7) ); //random number between 2-8
			City tmp = (City) it.next();
			
			for ( int i = 0; i < randomConnections; i++ )
			{
				City randomCity = this.getRandomCity();
				
				Edge fwd = new Edge ( tmp, randomCity );
				Edge bkwd = new Edge ( randomCity, tmp );
				
				//Ensure no duplicate edges are created
				if ( !this.edges.contains(fwd) || !this.edges.contains(bkwd) )
				{
					tmp.addAdjacent( randomCity );
					this.addEdge ( tmp, randomCity );
				}
			}
		}
	}

	
	public void countInOut()
	{
		Iterator<Edge> it = this.edges.iterator();
		while ( it.hasNext() )
		{
			Edge tmp = it.next();
			this.startsHash.get ( tmp.key1 ).outCount++;
			this.startsHash.get ( tmp.key2 ).inCount++;
		}
		
		
	}

	public void checkIncoming()
	{
		Iterator it = this.startsHash.values().iterator();
		while ( it.hasNext() )
		{
			City tmp = (City) it.next();
			if ( tmp.inCount == 0 )   // if the city has no incoming edges
			{
				City randomCity = this.getRandomCity();
				tmp.inCount = 1;  // set inCount to 1
				tmp.addAdjacent( randomCity );
				this.addEdge ( randomCity , tmp );
			}
		}
	}
	
	
}
