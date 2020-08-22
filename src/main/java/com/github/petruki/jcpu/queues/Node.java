package com.github.petruki.jcpu.queues;

public class Node {
	
	private Node prev;
	private Node next;
	private Object item;
	private int val;

	public Node(Object o) {
		this(o, 0);
	}

	public Node(Object o, int v) {
		prev = next = null;
		item = o;
		val = v;
	}

	public Node getPrev() {
		return prev;
	}

	public void setPrev(Node prev) {
		this.prev = prev;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Object getItem() {
		return item;
	}

	public void setItem(Object item) {
		this.item = item;
	}

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}
	
}

