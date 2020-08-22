package com.github.petruki.jcpu.queues;

public class Queue {
	
	protected Node head, tail;

	public Queue() {
		head = tail = null;
	}
	
	public void enqueue(Object a) {
		Node t = new Node(a);
		t.setNext(tail);
		t.setPrev(null);
		if (tail != null) 
			tail.setPrev(t);
		tail = t;
		if (head == null)
			head = tail;
	}	
	
	public Object dequeue() {
		if (head == null) {
			System.err.println("Error when attempting to unqueue the first key of an empty queue");
			System.exit(-1);
		}
		Object temp = head.getItem();
		head = head.getPrev();
		if (head == null)
			tail = null;
		else
			head.setNext(null);
		return temp;
	}	
	
	public boolean find(Object a) {
		Node ptr;
		for(ptr = head; ptr != null; ptr = ptr.getNext())
			if (ptr.getItem().equals(a))
				return true;
		return false;
	}
	
	public boolean remove(Object a) {
		Node ptr;
		for(ptr = head; ptr != null; ptr = ptr.getNext())
			if (ptr.getItem().equals(a)) {
				if (head == ptr) {
					head = ptr.getNext();
					if (ptr.getNext() != null)
						ptr.getNext().setPrev(null);
				} else 
					if (tail == ptr) {
						ptr.getPrev().setNext(null);
						tail = ptr.getPrev();
					} else {
						ptr.getPrev().setNext(ptr.getNext());
						ptr.getNext().setPrev(ptr.getPrev());
					}
				return true;
			}
		return false;
	}
	
	public Object front() {
		if (head == null) {
			System.err.println("Error when attempting to check the beggining of an empty queue");
			System.exit(-1);
		}
		return head.getItem();
	}
	
	public Object rear() {
		if (tail == null) {
			System.err.println("Error when attempting to check the end of an empty queue");
			System.exit(-1);
		}
		return tail.getItem();
	}
	
	public int length() {
		int  l = 0;
		Node ptr;
		for(ptr = head; ptr != null; ptr = ptr.getPrev(), l++);
		return l;
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public void print() {
		for(Node ptr = head; ptr != null; ptr = ptr.getPrev())
			System.out.println("Item " + ptr.getItem());
	}
	
	
}
