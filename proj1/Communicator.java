import nachos;
package nachos;

public class Communicator {

	public Communicator() {
		mutex = new Lock();		//implement mutex function
	
		//here we implement a listen function and a speaking function 
		LReadyUp = new Condition(mutex);		
		SReadyUp = new Condition(mutex); 		
	}
	
	//we then have to be able to create 2 public void functions for speaking and listening
	//we also have to have the thread listen via the communicator.
	//from there it should transfer the message to ListingFunc.
	//so bascally the thread to be paired with one of the ListingFunc. thread.
	public void  SpeakFunc(int message){
		mutex.acquire();
		SpeakingFunc++;
		
		//put to sleep the ListenFunc. 
		while(ListingFunc = 0){
			LReadyUp.sleep();
		}
		
		--ListeningFunc;
		
		//once that’s done we have to wake the SpeakFunc.
		SReadyUp.wake();
		mutex.release();
	}
	
	//add the public void function for listening
	public int ListenFunc (){
		mutex.acquire();
		ListeningFunc++;
		
		LReadyUp.wake();
		
		while(SpeakingFunc = 0){
			SReadyUp.sleep();
		}
		
		--SpeakingFunc;
		
		mutex.release();
			
		//I think we have to return the message at this point
		return message;
	}
	private Condition LReadyUp;
	private Condition SReadyUp;
	private int SpeakingFunc = 0;
	private int ListeningFunc = 0;
	private int messenger;
	private Lock mutex;
	}

