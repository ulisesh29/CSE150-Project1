package nachos.threads;

import nachos.machine.*;

/**
 * A <i>communicator</i> allows threads to synchronously exchange 32-bit
 * messages. Multiple threads can be waiting to <i>speak</i>,
 * and multiple threads can be waiting to <i>listen</i>. But there should never
 * be a time when both a speaker and a listener are waiting, because the two
 * threads can be paired off at this point.
 */
public class Communicator {
    /**
     * Allocate a new communicator.
     */
    public Communicator() {
    	mutex = new Lock();				//implement mutex function
    	
		//here we implement a listen function and a speaking function 
		LReadyUp = new Condition(mutex);		
		SReadyUp = new Condition(mutex); 		
    }

    /**
     * Wait for a thread to listen through this communicator, and then transfer
     * <i>word</i> to the listener.
     *
     * <p>
     * Does not return until this thread is paired up with a listening thread.
     * Exactly one listener should receive <i>word</i>.
     *
     * @param	word	the integer to transfer.
     */
    public void speak(int word) {
    	mutex.acquire();
		SpeakingFunc++;
		
		//put to sleep the ListenFunc. 
		while(ListingFunc = 0){
			LReadyUp.sleep();
		}
		
		--ListeningFunc; 
		words = word
		//once that’s done we have to wake the SpeakFunc.
		SReadyUp.wake();
		mutex.release();
    }

    /**
     * Wait for a thread to speak through this communicator, and then return
     * the <i>word</i> that thread passed to <tt>speak()</tt>.
     *
     * @return	the integer transferred.
     */    
    public int listen() {
    	mutex.acquire();
		ListeningFunc++;
		
		LReadyUp.wake();
		
		while(SpeakingFunc = 0){
			SReadyUp.sleep();
		}
		
		--SpeakingFunc;
		
		mutex.release();
    	
	return words;
    }
    private Condition LReadyUp;
	private Condition SReadyUp;
	private int SpeakingFunc = 0;
	private int ListeningFunc = 0;
	private int words;
	private Lock mutex;
    
}
