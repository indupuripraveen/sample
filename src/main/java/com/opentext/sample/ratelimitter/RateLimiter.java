package com.opentext.sample.ratelimitter;


import java.util.HashMap;
import java.util.Map;


public class RateLimiter {

    private final int maxRequests;
    private final long windowSizeInMillis;
    private final Map<String, RequestCounter> clientCounters;

    public RateLimiter(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        this.clientCounters = new HashMap<>();
    }

    public boolean isAllowed(String clientId) {
        long currentTime = System.currentTimeMillis();
        RequestCounter counter = clientCounters.get(clientId);
        //System.out.println("counter is " + counter);
        //System.out.println("Current TIme " + currentTime);
        if (counter == null || currentTime - counter.getWindowStart() > windowSizeInMillis) {
            // New window or window expired
            counter = new RequestCounter(currentTime, 1);
            clientCounters.put(clientId, counter);
            System.out.println("Counter incremented to " + counter.getCount());
            return true;
        } else if (counter.getCount() < maxRequests) {

            //  System.out.println("Tme  " + (currentTime - counter.getWindowStart()));
            //  System.out.println("windowSizeInMillis " + windowSizeInMillis);
            System.out.println("Hit the max Request " + maxRequests);
            // Request allowed within current window
            counter.incrementCount();
            return true;
        } else {
            System.out.println("Counter more than maxRequests  " + counter.getCount());
            // Request limit exceeded

            return false;
        }
    }

    private static class RequestCounter {

        private final long windowStart;
        private int count;

        public RequestCounter(long windowStart, int count) {
            this.windowStart = windowStart;
            this.count = count;
        }

        public long getWindowStart() {
            return windowStart;
        }

        public int getCount() {
            return count;
        }

        public void incrementCount() {
            count++;
            System.out.println("Increment Counter to " + count);
        }
    }
}
