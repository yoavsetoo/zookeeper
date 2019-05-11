package com.example.zookeeper;

import java.util.Comparator;

public class NodeIndexComparator implements Comparator<String> {

    private final String prefix;

    public NodeIndexComparator(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public int compare(String o1, String o2) {
        String ns1 = o1.substring(prefix.length(), o1.length());
        String ns2 = o2.substring(prefix.length(), o2.length());
        int n1 = Integer.parseInt(ns1);
        int n2 = Integer.parseInt(ns2);
        return n1 - n2;
    }

}
