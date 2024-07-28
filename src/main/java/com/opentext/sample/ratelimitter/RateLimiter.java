package com.opentext.sample.ratelimitter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RateLimiter {

    private int maxRequests;
    private long windowSizeInMillis;

    private Map<String, RequestCounter> clientCounters;

    public RateLimiter(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        this.clientCounters = new HashMap<>();
    }

    public boolean isAllowed(String clientId) {

        long currentTime = System.currentTimeMillis();
        RequestCounter counter = clientCounters.get(clientId);

        System.out.println("counter is "+counter);

        System.out.println("Current TIme "+currentTime);
        if (counter == null || currentTime - counter.getWindowStart() > windowSizeInMillis) {
            // New window or window expired
            counter = new RequestCounter(currentTime, 1);
            clientCounters.put(clientId, counter);
           System.out.println("Counter icremented "+counter.getCount());
            return true;
        } else if (counter.getCount() < maxRequests) {

            System.out.println("Tme  "+(currentTime - counter.getWindowStart()));
            System.out.println("windowSizeInMillis "+windowSizeInMillis);
            System.out.println("max Request "+maxRequests);


            System.out.println("Counter less than maxRequests  "+counter.getCount());
            System.out.println("counter.getWindowStart() "+counter.getWindowStart());

            // Request allowed within current window
            counter.incrementCount();
            return true;
        } else {
            System.out.println("Counter more than maxRequests  "+counter.getCount());
            // Request limit exceeded

            return false;
        }
    }

    private static class RequestCounter {

        private long windowStart;
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
            System.out.println("increment Counter to"+count);
        }
    }
}
