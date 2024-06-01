package com.github.petruki.jcpu.queues;

public class PriorityQueue extends Queue {

    public void enqueue(Object a, int v) {
        Node t = new Node(a, v);
        if (head == null) {
            head = tail = t;
            t.setPrev(null);
            t.setNext(null);
        } else {
            Node ptr;
            for (ptr = tail; ptr != null && ptr.getVal() > v; ptr = ptr.getNext());

            if (ptr == null) {
                head.setNext(t);
                t.setPrev(head);
                t.setNext(null);
                head = t;
            } else if (ptr == tail) {
                tail.setPrev(t);
                t.setNext(tail);
                t.setPrev(null);
                tail = t;
            } else {
                t.setNext(ptr);
                t.setPrev(ptr.getPrev());
                ptr.getPrev().setNext(t);
                ptr.setPrev(t);
            }
        }
    }

    public int frontKey() {
        if (head == null) {
            System.err.println("Error when attempting to check the first key of a empty queue");
            System.exit(-1);
        }
        return head.getVal();
    }

    public int rearKey() {
        if (tail == null) {
            System.err.println("Error when attempting to check the last key of an empty queue");
            System.exit(-1);
        }
        return tail.getVal();
    }

    @Override
    public void print() {
        for (Node ptr = head; ptr != null; ptr = ptr.getPrev())
            System.out.println("Item: " + ptr.getItem() + " [Value: " + ptr.getVal() + "]");
    }

}

