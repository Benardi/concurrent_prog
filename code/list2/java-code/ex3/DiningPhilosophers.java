
class DiningPhilosophers {
	static final int NUM_PHILOSOPHERS = 5;
	
    public static void main(String[] args) throws Exception { 
        final DiningPhilosophers.Philosopher[] philosophers = new DiningPhilosophers.Philosopher[NUM_PHILOSOPHERS];
        Object[] forks = new Object[philosophers.length];
 
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Object();
        }
 
        for (int i = 0; i < philosophers.length; i++) {
            Object leftFork = forks[i];
            Object rightFork = forks[(i + 1) % forks.length];
 
            if (i == philosophers.length - 1) {
                 
                // The last philosopher picks up the right fork first
                philosophers[i] = new  DiningPhilosophers.Philosopher(rightFork, leftFork); 
            } else {
                philosophers[i] = new DiningPhilosophers.Philosopher(leftFork, rightFork);
            }
             
            Thread t 
              = new Thread(philosophers[i], "Philosopher " + (i + 1));
            t.start();
        }
    }
    
	
	static class Philosopher implements Runnable {
	    // The forks on either side of this Philosopher 
	    private Object leftFork;
	    private Object rightFork;
	 
	    public Philosopher(Object leftFork, Object rightFork) {
	        this.leftFork = leftFork;
	        this.rightFork = rightFork;
	    }
	 
	    private void doAction(String action) throws InterruptedException {
	        System.out.println(
	          Thread.currentThread().getName() + " " + action);
	        Thread.sleep(((int) (Math.random() * 100)));
	    }
	    
		    @Override
		    public void run() {
		    	boolean hungry = true; 
		    	int hasFork = 0;
		        try {
		            while (hungry || hasFork > 0) {
		                 
		                // thinking
		                doAction(System.nanoTime() + ": Thinking");
		                synchronized (leftFork) {
		                    doAction(
		                      System.nanoTime() 
		                        + ": Picked up left fork");
		                    hasFork = 1;
		                    
		                    synchronized (rightFork) {
		                        // eating
		                        doAction(
		                          System.nanoTime() 
		                            + ": Picked up right fork - eating"); 
			                    hasFork = 2;
		                        hungry = false;
		                         
		                        doAction(
		                          System.nanoTime() 
		                            + ": Put down right fork");
			                    hasFork = 1;
		                    }
		                     
		                    // Back to thinking
		                    if(hungry) {
			                    doAction(
			                      System.nanoTime() 
			                        + ": Put down left fork. Back to thinking");
			                    hasFork = 0;
		                    }
		                    else {
			                    doAction(
			                    		System.nanoTime() 
			                    		+ ": Put down left fork. Isn't hungry anymore");
			                    hasFork = 0;		                   		                    	
		                    }
		                }
		            }
		        } catch (InterruptedException e) {
		            Thread.currentThread().interrupt();
		            return;
		        }
		    }
		}
}