package nachos.threads;

import nachos.machine.Lib;
import nachos.machine.Machine;

/**
 * A scheduler that chooses threads using a lottery.
 *
 * <p>
 * A lottery scheduler associates a number of tickets with each thread. When a
 * thread needs to be dequeued, a random lottery is held, among all the tickets
 * of all the threads waiting to be dequeued. The thread that holds the winning
 * ticket is chosen.
 *
 * <p>
 * Note that a lottery scheduler must be able to handle a lot of tickets
 * (sometimes billions), so it is not acceptable to maintain state for every
 * ticket.
 *
 * <p>
 * A lottery scheduler must partially solve the priority inversion problem; in
 * particular, tickets must be transferred through locks, and through joins.
 * Unlike a priority scheduler, these tickets add (as opposed to just taking
 * the maximum).
 */
public class LotteryScheduler extends PriorityScheduler {
    /**
     * Allocate a new lottery scheduler.
     */
    public LotteryScheduler() {
    }
    
    /**
     * Allocate a new lottery thread queue.
     *
     * @param	transferPriority	<tt>true</tt> if this queue should
     *					transfer tickets from waiting threads
     *					to the owning thread.
     * @return	a new lottery thread queue.
     */
    public ThreadQueue newThreadQueue(boolean transferPriority) { //to intialize number of tickets
    //return null;
	return newThreadQueue(transferPriority);
    }

protected ThreadState getThreadState(KThread thread) { 
	if (thread.schedulingState == null)
		thread.schedulingState = new ThreadState(thread);

	return (ThreadState) thread.schedulingState;
}

public void setPriority(KThread thread, int priority) {
	Lib.assertTrue(Machine.interrupt().disabled());

	Lib.assertTrue(priority >= priorityMinimum
			&& priority <= priorityMaximum);

	getThreadState(thread).setPriority(priority);
}

public boolean increasePriority() { //for increasing priority count of the current thread 
	boolean intStatus = Machine.interrupt().disable();

	KThread thread = KThread.currentThread();

	int priority = getPriority(thread);
	if (priority == priorityMaximum)
		return false;

	setPriority(thread, priority + 1);

	Machine.interrupt().restore(intStatus);
	return true;
}

public boolean decreasePriority() { //for decreasing priority count of the current thread
	boolean intStatus = Machine.interrupt().disable();

	KThread thread = KThread.currentThread();

	int priority = getPriority(thread);
	if (priority == priorityMinimum)
		return false;

	setPriority(thread, priority - 1);

	Machine.interrupt().restore(intStatus);
	return true;
	}
}
