
/**
 * Loads user interface to test map travel system.
 * 
 * @author Alessandra Poblador (acp2164)
 *
 */

import java.util.*;
import java.io.*;

public class test {


	
	public static void main ( String[] args ) 
	{
		
		TravelSystem sys = new TravelSystem ();
		boolean keepPlaying = true, validNumber = false;
		boolean fileFound;
		int item = 0, n, limit;
		Scanner input = new Scanner( System.in ).useDelimiter("\n");
										//all inputs automatically read to
										//end of line
		Scanner scan;
		String stateKey, cityKey, current, target;
		
		System.out.println ( "Welcome to map travel system!" );
		System.out.println ( sys.displayItems() );
		System.out.println ( "Which item would you like?" );

		while ( validNumber == false )
		{
			item = input.nextInt();
			if ( item < 1 || 11 < item )
			{
				System.out.println ( "Please enter a valid number.");
			}
			else
			{
				validNumber = true;
			}
		}
		
		
		while ( keepPlaying == true )
		{
			switch ( item )
			{
			case 1:
				
				// option to remove if graph already loaded
				if ( sys.g != null ) 
				{
					System.out.println("Remove current graph? 1 yes, 0 no");
					int answer = input.nextInt();
					if ( answer == 1) 
					{ 
						sys.g = null; 
					}
				}
				
				
				fileFound = false;
				System.out.println("Please enter .txt file.");
				String file;
				File f = null;
				while ( fileFound == false )
				{
					file = input.next();
					f = new File (file);
					try
					{
						scan = new Scanner ( f );
						fileFound = true;
						
						sys.loadTextFile( f );

						System.out.println( "Text file loaded.\n\n" );
						
						System.out.println( "Next item?" );
						System.out.println( sys.displayItems() );
						item = input.nextInt();
						break;
					}
					catch ( FileNotFoundException e )
					{
						System.out.println("File not found! Please enter file again.");
					}

				}
				break;
				
			case 2:
				sys.loadGraph();
				System.out.println("Graph loaded.");
				
				System.out.println( "\n\nNext item?" );
				System.out.println( sys.displayItems() );
				item = input.nextInt();
				break;
				
			case 3:
				sys.saveGraph();
				System.out.println("Graph saved.");
				
				System.out.println( "\n\nNext item?" );
				System.out.println( sys.displayItems() );
				item = input.nextInt();
				break;
			
			case 4:
				System.out.println("\n\nWhich state would you like to search for?");
				stateKey = input.next();
				System.out.println( stateKey );

				System.out.println( sys.searchState( stateKey ) );
				
				System.out.println( "\n\nNext item?" );
				System.out.println( sys.displayItems() );
				item = input.nextInt();
				break;
				
			case 5:
				System.out.println("\n\nWhich city would you like to search for?");
				System.out.println("Please enter: 'city, state' in full.");
				cityKey = input.next();
				System.out.println( sys.searchCity ( cityKey ) );
				
				System.out.println( "\n\nNext item?" );
				System.out.println( sys.displayItems() );
				item = input.nextInt();
				break;
				
			case 6:
				System.out.println("Which city would you like to set as current?");
				current = input.next();
				sys.setCurrent( current );
				
				System.out.println ("The current city is now: "+sys.current );				
				System.out.println( "\n\nNext item?" );
				System.out.println( sys.displayItems() );
				item = input.nextInt();
				break;
				
			case 7:
				System.out.println ( sys.printCurrent() );	
				
				System.out.println( "\n\nNext item?" );
				System.out.println( sys.displayItems() );
				item = input.nextInt();	
				break;
				
			case 8:
				System.out.println ( "How many cities?" );
				n = input.nextInt();
				System.out.println( sys.findClosest( n ) );
				
				System.out.println( "\n\nNext item?" );
				System.out.println( sys.displayItems() );
				item = input.nextInt();
				break;
				
			case 9:
				System.out.println ( "What is your cost limit?" );
				limit = input.nextInt();
				System.out.println( sys.findAdjacentBelow( limit ) );
				
				System.out.println( "\n\nNext item?" );
				System.out.println( sys.displayItems() );
				item = input.nextInt();
				break;
				
			case 10:
				System.out.println( "What is your target city?" );
				target = input.next();
				System.out.println( sys.findShortestPath(target) );
				
				System.out.println( "\n\nNext item?" );
				System.out.println( sys.displayItems() );
				item = input.nextInt();
				break;
				
			case 11:
				System.out.println("Bye bye!");
				keepPlaying = false;
				break;
				
			}
	
		}
		
		
	}
	

	
}
