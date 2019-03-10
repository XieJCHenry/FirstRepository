package crawler;

import java.util.LinkedList;
/**
 * 队列，保存要访问的URL
 * @author a1098
 *
 */
public class Queue {
	private LinkedList<String> queue;

	public Queue() {
		queue = new LinkedList<String>();
	}

	public void enQueue(String s) {
		queue.addLast(s);
	}

	public String deQueue() {
		return queue.removeFirst();
	}

	public boolean isQueueEmpty() {
		return queue.isEmpty();
	}

	public boolean contains(String s) {
		return queue.contains(s);
	}

}
