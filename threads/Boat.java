package nachos.threads;
import nachos.ag.BoatGrader;

public class Boat
{
    static BoatGrader bg;
    
    static int numAdultsOahu;
    static int numAdultsMolokai;
    static int numChildrenOahu;
    static int numChildrenMolokai;
    
    //we need more variables
    //static int ChildrenQueueforBoat; may need
    //static int NumberOfBoatTrips; may need
    //static String boatLocation; not sure how to implement
    
    static Lock lock;
    static Lock LBoat;
    static Lock WaitLock;
    static Lock Locka;
    static Lock Lockb;
    
    static Communicator doneWithride;
    
    static boolean LiftGiven;
    
    static Condition2 Coordinate;
    static Condition2 childrenMolokai;
    static Condition2 forTheboat;
    
    public static void selfTest()
    {
	BoatGrader b = new BoatGrader();
	
	System.out.println("\n ***Testing Boats with only 2 children***");
	begin(0, 2, b);

	System.out.println("\n ***Testing Boats with 2 children, 1 adult***");
  	begin(1, 2, b);

  	System.out.println("\n ***Testing Boats with 3 children, 3 adults***");
  	begin(3, 3, b);
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
//	ChildrenQueueforBoat = 0;
//	NumberOfBoatTrips = 0;
//	boatLocation = "Oahu";
	
	LiftGiven = false;
	
	doneWithride = new Communicator();
	
	lock = new Lock();
    LBoat = new Lock();
    WaitLock = new Lock();
    Locka = new Lock();
    Lockb = new Lock();
    
    childrenMolokai = new Condition2 (Locka);
    Coordinate = new Condition2 (Lockb);
    forTheboat =  new Condition2 (WaitLock);
	    
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
		    
		    thread.setName("Adult #" +i);
	}
	
    for(int i = 0; i < children; i++) {
    	
    	Runnable child = new Runnable() {
    	    public void run() {
                    ChildItinerary();
                }
            };
            
            thread = new KThread(child);
            thread.fork();
            
            thread.setName("Child #" + i);
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
    {	   begin(0, 0, bg);
	/* This is where you should put your solutions. Make calls
	   to the BoatGrader to show that it is synchronized. For
	   example:
	       bg.AdultRowToMolokai();
	   indicates that an adult has rowed the boat across to Molokai
	*/
	   lock.acquire(); 
	   numAdultsOahu++;
	   lock.release();
	   
	   Locka.acquire();
	   childrenMolokai.sleep();
	   Locka.release();
	   
	   bg.AdultRowToMolokai();
	   numAdultsOahu--;
	   
	   Lockb.acquire();
	   Coordinate.wake();
	   Lockb.release();
	    
    }

    static void ChildItinerary(){
    	
    	begin(0, 0, bg);
    	
	    lock.acquire();
	    numChildrenOahu++;
	    lock.release();
	    
	    KThread.alarm.waitUntil(500);
	    
	    while(true){
	    	
	    	LBoat.aquire();
	    	
	    	if(!LiftGiven && numChildrenOahu > 0 ){
	    		
	    		LiftGiven = true;
	    		bg.ChildRideToMolokai();
	    		
	    		numChildrenOahu--;
	    		numChildrenMolokai++;
	    		
	    		LBoat.release();
	    		
	    		while(true){
	    			
	    			Lockb.acquire();
	    			Coordinate.sleep();
	    			Lockb.release();
	    			
	    			bg.ChildRowToOahu();
	    			
	    			WaitLock.acquire();
	    			forTheboat.wake();
	    			WaitLock.release();
	    			
	    			bg.ChildRideToMolokai();
	    			
	    		}
	    		
	    	}else{
	    		
	    		LiftGiven = false;
	    		bg.ChildRowToMolokai();
	    		
	    		numChildrenOahu--;
	    		numChildrenMolokai++;
	    		
	    		if(numChildrenOahu > 0){
	    			
	    			bg.ChildRowToOahu();
	    			
	    			numChildrenMolokai--;
	    			numChildrenOahu++;
	    			
	    			LBoat.release();
	    			
	    		}else{
	    			
	    			bg.ChildRowToOahu();
	    			
	    			numChildrenMolokai--;
	    			numChildrenOahu++;
	    			
	    			while(numAdultsOahu > 0){
	    				
	    				Locka.acquire();
	    				childrenMolokai.wake();
	    				Locka.release();
	    				
	    				WaitLock.acquire();
	    				forTheboat.sleep();
	    				WaitLock.release();
	    				
	    				bg.ChildRowToMolokai();
	    				bg.ChildRowToOahu();
	    				
	    			}
	    			
	    			bg.ChildRowToMolokai();
	    			
	    			numChildrenMolokai++;
	    			numChildrenOahu--;
	    			
	    			doneWithride.speak(1);
	    			return;
	    			
	    		}	
	    		
	    	}
	    		
	    }
	    
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

