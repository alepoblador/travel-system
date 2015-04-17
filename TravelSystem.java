

/**
 * Creates an instance of a map travel system.
 * 
 * @author Alessandra Poblador (acp2164)
 *
 */

import java.io.*;
import java.util.*;

public class TravelSystem {

	Graph g;
	City current;
	
	public TravelSystem()
	{
		g = null;
		current = null;
	}
	
	public String displayItems()
	{
		String menu = ("  1.  Load a text file into the system.\n");
		menu += ("  2.  Load a graph from mygraph.bin. \n");
		menu += ("  3.  Save current graph to mygraph.bin. \n");
		menu += ("  4.  Search for a state. \n");
		menu += ("  5.  Search for a city. \n");
		menu += ("  6.  Change current city. \n");
		menu += ("  7.  Print current city. \n");
		menu += ("  8.  Find n closest cities to current city by GPS distance. \n");
		menu += ("  9.  Find all direct routes from current city below certain price.\n");
		menu += ("  10. Find shortest path from current city to target city.\n");
		menu += ("  11. Quit.");
		
		return menu;
	}
	
	public void loadTextFile ( File f ) throws FileNotFoundException
	{
		Scanner scan = new Scanner ( f );
		
		int n = Integer.parseInt(scan.nextLine()); //reads n from first line of file
		this.g = new Graph ( n );
		
		this.g.addCitiesFromFile ( f );
		this.g.assignConnections();
		this.g.countInOut();
		this.g.checkIncoming();
		
	}
	
	public void loadGraph () 
	{
		try
		{
			FileInputStream fileIn = new FileInputStream ("mygraph.bin");
			ObjectInputStream in = new ObjectInputStream ( fileIn );
			this.g = (Graph) in.readObject();
			in.close();
			fileIn.close();
		} catch ( IOException i )
		{
			i.printStackTrace();
		} catch ( ClassNotFoundException c )
		{
			System.out.println("Class not found.");
			c.printStackTrace();
		}
	}
	
	
	public void saveGraph()
	{
		try
		{
			FileOutputStream fileOut = new FileOutputStream( "mygraph.bin" );
			ObjectOutputStream out = new ObjectOutputStream( fileOut );
			out.writeObject( this.g );
			out.close();
			fileOut.close();
		} catch ( IOException i )
		{
			i.printStackTrace();
		}
	}
	
	
	public String searchState ( String key )
	{
		String s = "\nCities in this state...\n";
		Iterator it = this.g.startsHash.values().iterator(); //go through all cities
		while ( it.hasNext() )
		{
			City tmp = (City) it.next();
			if ( tmp.state.equals( key ) )  //if city is in key state
			{
				s += "City: "+tmp.city+"\n  In: "+tmp.inCount+"  Out:"+tmp.outCount+"\n";
			}
		}			
		return s;
	}
	
	public String searchCity ( String key )
	{
		City tmp = this.g.startsHash.get( key );   //search for city in HashMap
		
		String s = "\nSome information about "+tmp+"...";
		s += "\n  GPS: " + tmp.latitude + ", " + tmp.longitude;
		s += "\n  In: "+tmp.inCount+"  Out: "+tmp.outCount;		
		
		return s;
	}
	
	public void setCurrent ( String key )
	{
		this.current = this.g.startsHash.get ( key );
	}
	
	public String printCurrent ()
	{
		if ( this.current == null )
		{
			this.current = this.g.getRandomCity();
		}
		
		String s = "The current city is: "+this.current;
		s += "\nSome information about "+this.current+"...";
		s += "\n  GPS: " + this.current.latitude + ", " + this.current.longitude;
		s += "\n  In: "+this.current.inCount+"  Out: "+this.current.outCount;	
		
		return s;
	}
	
	public double haversine ( double lat1, double lon1, double lat2, double lon2 )
	{
		double R = 6371.00;
		double dLat = Math.toRadians(lat2-lat1);
		double dLon = Math.toRadians(lon2-lon1);
		
		double lati1 = Math.toRadians(lat1);
		double lati2 = Math.toRadians(lat2);
		
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lati1) * Math.cos(lati2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = R * c;
		
		return d;
	}
	
	public String findClosest ( int n )
	{
		if ( this.current == null ) { this.current = this.g.getRandomCity(); }	
		City curr = this.current;
		
		//create arraylist to hold cities
		ArrayList<City> distances = new ArrayList<City>( this.g.startsHash.size() );
		
		double lat1 = curr.latitude, lon1 = curr.longitude, lat2, lon2;
		
		//for the current city, goes through all cities and 
		//calculates GPS distance between them using haversine formula
		for ( int i = 0; i < this.g.startsArray.size(); i++ )
		{
			lat2 = this.g.startsArray.get ( i ).latitude;
			lon2 = this.g.startsArray.get ( i ).longitude;
			this.g.startsArray.get( i ).gpsDistance = haversine ( lat1, lat2, lat2, lon2  );
		}
		
		// sort arraylist by distances (closest first)
		distances = sortArrayList ( this.g.startsArray );
		
		// for i through n, list cities
		String s = "The closest cities by gps distance from "+curr+":\n";
		for ( int i = 0; i < n; i++ )
		{
			s += "  "+distances.get( i )+"  distance: "+distances.get( i ).gpsDistance+"\n";
		}
		return s;		
	}
	
	public ArrayList<City> sortArrayList( ArrayList<City> a )
	{	
		ArrayList<City> array = a;
		
		City hold;
		for ( int i = 0; i < array.size()-1 ; i++)
		{
			for ( int j = 0; j < array.size() - i - 1; j++ )
			{
				if ( array.get( j ).gpsDistance > array.get( j+1 ).gpsDistance )
				{
					hold = array.get ( j );
					array.set ( j, array.get( j+1 ) );
					array.set ( j+1, hold );					
				}
			}
		}
		return array;
	}

	
	public String findAdjacentBelow ( int Y )
	{
		if ( this.current == null ) { this.current = this.g.getRandomCity(); }	
		City tmp = this.current;
		
		//sort connections arraylist of current city
		if ( !tmp.hasBeenSorted() ) { tmp.sortConnections(); }
		
		String s = "Cities within cost "+Y+" from "+current+":\n";
		
		//go through connections listing cities that have weight below Y
		int i = 0;
		double l = tmp.connections.get( 0 ).weight;
		boolean found = false;
		while ( l < Y && i < tmp.connections.size()-1 ) 
		{
			s += "  "+tmp.connections.get( i )+"  weight: "+tmp.connections.get( i ).weight+"\n";
			l = tmp.connections.get( i+1 ).weight;
			i++;
			found = true;
		}
		
		if ( !found ) { s+= "No paths below your limit."; };
		
		return s;
	}
	
	
	
	public String findShortestPath ( String target )
	{
		if ( this.current == null ) { this.current = this.g.getRandomCity(); }	

		//when method is recalled, resets all visited values
		for ( City c : this.g.startsHash.values() ) { c.resetVisited(); }
		
		//create stack for nodes on the path
		ArrayList<City> stack = new ArrayList<City>(1);
		
		City destination = this.g.startsHash.get ( target );
		City tmp = this.current;  //tmp holds current node in path
		stack.add( 0, tmp ); //push current onto stack
		int pointer = 0;
		
		String s = "Start: " + tmp;
		boolean reachedTarget = false;
		
		while ( !reachedTarget )
		{
			// bubble sort tmp arraylist by weight
			if ( !tmp.hasBeenSorted() ) { tmp.sortConnections(); }

			// go through sorted connections list and find the closest one that
			// has not been visited
			int l = 0; 
			boolean found = false;
			while ( found == false )
			{
				// finds closest that has not been visited
				if ( l < tmp.connections.size() && !tmp.connections.get ( l ).hasBeenVisited)
				{
					tmp.connections.get( l ).hasBeenVisited = true;
					tmp = tmp.connections.get( l ); 
					stack.add( pointer+1, tmp );     //push current onto stack 
					found = true; pointer++;
				}
				else if ( l >= tmp.connections.size() )   // if all outnodes have been visited
				{
					tmp = stack.get( pointer-1 ); //pop from stack
					found = true; pointer--;      //go back and search previous
				}

				else { l++; }
			}			
			s += "\nto " + tmp;
			
			if ( tmp == destination )
			{
				reachedTarget = true;
				s += "\nEnd path.";
			}	
		}
		return s;
	}
	
	
	
}
