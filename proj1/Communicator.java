package nachos;

public class Communicator {

	public Communicator(){
	//implement mutex function
	//here we implement a listen function and a speaking function 
	ListenFunc. //set this equal to the mutex func.
	SpeakFunc. //do the same to this one also
	}
	//we then have to be able to create 2 public void functions for speaking and listening
	//we also have to have the thread listen via the communicator.
	//from there it should transfer the message to ListingFunc.
	//so bascally the thread to be paired with one of the ListingFunc. thread.
	public void  SpeakFunc. (message){
	//add mutex function
	//initialize SpeakingFunc. And iterate twice ++
	while(ListingFunc doesn’t equal anything){
	//put to sleep the ListenFunc. 
	}
	//de-iterate the ListeningFunc. –
	//once that’s done we have to make the SpeakFunc.
	//add mutex
	}
	//add the public void function for listening
	public void ListenFunc. (){
	//add mutex function
	//wake up the ListenFunc. Then iterate it
	while(SpeakingFunc. doesn’t equal anyth{
	//put the SpeakFunc. to sleep
	}
	//de-iterate the SpeakingFunc. –
	//I think we have to return the message at this point
	}
	}

