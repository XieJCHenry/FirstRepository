package crawler;

import java.util.HashSet;
import java.util.Set;

public class LinkQueue {
	// 已访问过的URL
	private static Set<String> visitedList = new HashSet<String>();
	// 未访问过的URL
	private static Queue todoList = new Queue();

	// 获取未访问的URL列表
	public static Queue getUnVisitedList() {
		return todoList;
	}

	// 添加到访问过的URL队列中
	public static void addVisitedUrl(String url) {
		visitedList.add(url);
	}

	// 移除访问过的URL
	public static void removeVisitedUrl(String url) {
		visitedList.remove(url);
	}

	// 未访问过的URL出列
	public static String getUnVisitedUrl() {
		return todoList.deQueue();
	}

	// 保证每个URL只访问一次
	public static void addUnVisitedUrl(String url) {
		if (url != null && !url.trim().equals("") && !visitedList.contains(url)
				&& !todoList.contains(url))
			todoList.enQueue(url);
	}

	// 获取已访问过的URL数量
	public static int getVisitedUrlNum() {
		return visitedList.size();
	}

	// 判断未访问的URL队列是否为空
	public static boolean todoListIsEmpty() {
		return todoList.isQueueEmpty();
	}
}
