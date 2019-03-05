package com.seproject.crowdfunder.models;

import java.util.Comparator;

public class sdd implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        if (o1.value > o2.value)
            return  1;
        else if (o1.value < o2.value)
            return -1;
        else return 0;
    }

    @Override
    public Comparator<User> reversed() {
        return null;
    }



}
