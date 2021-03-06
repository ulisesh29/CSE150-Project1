package nachos.threads;

import nachos.machine.*;

import java.util.PriorityQueue;

/**
 * Uses the hardware timer to provide preemption, and to allow threads to sleep
 * until a certain time.
 */
public class Alarm {
    /**
     * Allocate a new Alarm. Set the machine's timer interrupt handler to this
     * alarm's callback.
     *
     * <p><b>Note</b>: Nachos will not function correctly with more than one
     * alarm.
     */
    public Alarm() {
    	Machine.timer().setInterruptHandler(new Runnable() {
    		public void run() { timerInterrupt(); }
	    	});
    }

    /**
     * The timer interrupt handler. This is called by the machine's timer
     * periodically (approximately every 500 clock ticks). Causes the current
     * thread to yield, forcing a context switch if there is another thread
     * that should be run.
     */
    public void timerInterrupt() {
	
    	//KThread.currentThread().yield();
    	
    	long currentTime = Machine.timer().getTime();
    	boolean status = Machine.interrupt().disable();
    	
    	WaitingThread nextThread;
    	nextThread = waitingQueue.peek();

		while (nextThread.wakeTime() <= currentTime && waitingQueue != null) {
			
			waitingQueue.poll().thread().ready();
		}

		KThread.yield();
		Machine.interrupt().restore(status);
    }

    /**
     * Put the current thread to sleep for at least <i>x</i> ticks,
     * waking it up in the timer interrupt handler. The thread must be
     * woken up (placed in the scheduler ready set) during the first timer
     * interrupt where
     *
     * <p><blockquote>
     * (current time) >= (WaitUntil called time)+(x)
     * </blockquote>
     *
     * @param	x	the minimum number of clock ticks to wait.
     *
     * @see	nachos.machine.Timer#getTime()
     */
    public void waitUntil(long x) {
	// for now, cheat just to get something working (busy waiting is bad)
    	/*if(x <= 0) {
    		
    		return;
    	}
    	*/
    	long wakeTime = Machine.timer().getTime() + x;
    	
    	/*while (wakeTime > Machine.timer().getTime()) {
    		KThread.yield();
    	}
    	*/

		boolean status = Machine.interrupt().disable();

		waitingQueue.add(new WaitingThread(wakeTime, KThread.currentThread()));

		KThread.sleep();
		Machine.interrupt().restore(status);
    }
    
    private class WaitingThread implements Comparable<WaitingThread> {
    	
    	WaitingThread(long wakeTime, KThread thread) {
			
    		//Lib.assertTrue(Machine.interrupt().disabled());

			this.wakeTime = wakeTime;
			this.thread = thread;
		}

		public int compareTo(WaitingThread waitingThread) {
			
			if (wakeTime < waitingThread.wakeTime) {
				
				return -1;
			}
			else if (wakeTime > waitingThread.wakeTime) {
				
				return 1;
			}
			else {
				
				return 0; //thread.compareTo(waitingThread.thread);
			}
		}

		public KThread thread() {
			
			return thread;
		}
		
		public long wakeTime() {
			
			return wakeTime;
		}
		
		private KThread thread;
		private long wakeTime;
    }
    
	private PriorityQueue<WaitingThread> waitingQueue = new PriorityQueue<WaitingThread>();

}
