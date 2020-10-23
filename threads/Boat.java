package nachos.threads;
import nachos.ag.BoatGrader;

public class Boat
{
    static BoatGrader bg;
    
    static int numAdultsOahu;
    static int numAdultsMolokai;
    static int numChildrenOahu;
    static int numChildrenMolokai;
    
    static String boatLocation;
    
    // we need more variables
    
    
    public static void selfTest()
    {
	BoatGrader b = new BoatGrader();
	
	System.out.println("\n ***Testing Boats with only 2 children***");
	begin(0, 2, b);

//	System.out.println("\n ***Testing Boats with 2 children, 1 adult***");
//  	begin(1, 2, b);

//  	System.out.println("\n ***Testing Boats with 3 children, 3 adults***");
//  	begin(3, 3, b);
    }

    public static void begin( int adults, int children, BoatGrader b )
    {
	// Store the externally generated autograder in a class
	// variable to be accessible by children.
	bg = b;
	

	// Instantiate global variables here
	
	numAdultsOahu = 0;
	numAdultsMolokai = 0;
	numChildrenOahu = 0;
	numChildrenMolokai = 0;
	
	boatLocation = "Oahu";
	    
	    // we need to define the variables we added from above
	
	// Create threads here. See section 3.4 of the Nachos for Java
	
	KThread thread;
	
	// Walkthrough linked from the projects page.
	
	for(int i = 0; i < adults; i++) {
		
		 Runnable adult = new Runnable() {
		        public void run() {
		            AdultItinerary();
		        }
		    };
		    
		    thread = new KThread(adult);
		    thread.fork();	
		    
		    //setName
	}
	
    for(int i = 0; i < children; i++) {
    	
    	Runnable child = new Runnable() {
    	    public void run() {
                    ChildItinerary();
                }
            };
            
            thread = new KThread(child);
            thread.fork();
            
            //setName
    }

	Runnable r = new Runnable() {
	    public void run() {
                SampleItinerary();
            }
        };
        
        KThread t = new KThread(r);
        t.setName("Sample Boat Thread");
        t.fork();

    }

    static void AdultItinerary()
    {
	/* This is where you should put your solutions. Make calls
	   to the BoatGrader to show that it is synchronized. For
	   example:
	       bg.AdultRowToMolokai();
	   indicates that an adult has rowed the boat across to Molokai
	*/
    }

    static void ChildItinerary()
    {
    }

    static void SampleItinerary()
    {
	// Please note that this isn't a valid solution (you can't fit
	// all of them on the boat). Please also note that you may not
	// have a single thread calculate a solution and then just play
	// it back at the autograder -- you will be caught.
	System.out.println("\n ***Everyone piles on the boat and goes to Molokai***");
	bg.AdultRowToMolokai();
	bg.ChildRideToMolokai();
	bg.AdultRideToMolokai();
	bg.ChildRideToMolokai();
    }
    
}

