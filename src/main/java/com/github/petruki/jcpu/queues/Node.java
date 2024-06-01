package com.github.petruki.jcpu.queues;

import lombok.Data;

@Data
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

}

