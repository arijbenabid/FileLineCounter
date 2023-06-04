package com.file;

import java.util.HashMap;
import java.util.Map;

class Counter {
    private int count;

    public synchronized void increment(int value) {
        count += value;
    }

    public synchronized int getCount() {
        return count;
    }
}