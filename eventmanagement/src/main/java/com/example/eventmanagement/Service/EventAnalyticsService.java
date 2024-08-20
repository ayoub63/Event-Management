package com.example.eventmanagement.Service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EventAnalyticsService {
    private final Map<Long, EventAnalytics> analyticsData = new ConcurrentHashMap<>();

    public static class EventAnalytics {
        private int viewCount;
        private int registrationCount;
        private Map<String, Integer> demographicData;
        private List<String> popularReferrers;
        private LocalDateTime lastUpdated;
        
        public EventAnalytics() {
            this.viewCount = 0;
            this.registrationCount = 0;
            this.demographicData = new HashMap<>();
            this.popularReferrers = new ArrayList<>();
            this.lastUpdated = LocalDateTime.now();
        }

        public void incrementViewCount() {
            this.viewCount++;
            this.lastUpdated = LocalDateTime.now();
        }

        public void incrementRegistrationCount() {
            this.registrationCount++;
            this.lastUpdated = LocalDateTime.now();
        }

        public void addDemographicData(String demographic) {
            demographicData.merge(demographic, 1, Integer::sum);
        }

        public void addReferrer(String referrer) {
            popularReferrers.add(referrer);
        }

        // Getters
        public int getViewCount() { return viewCount; }
        public int getRegistrationCount() { return registrationCount; }
        public Map<String, Integer> getDemographicData() { return demographicData; }
        public List<String> getPopularReferrers() { return popularReferrers; }
        public LocalDateTime getLastUpdated() { return lastUpdated; }
    }

    public void trackEventView(Long eventId, String referrer) {
        EventAnalytics analytics = analyticsData.computeIfAbsent(eventId, k -> new EventAnalytics());
        analytics.incrementViewCount();
        if (referrer != null && !referrer.isEmpty()) {
            analytics.addReferrer(referrer);
        }
    }

    public void trackRegistration(Long eventId, String demographic) {
        EventAnalytics analytics = analyticsData.computeIfAbsent(eventId, k -> new EventAnalytics());
        analytics.incrementRegistrationCount();
        if (demographic != null && !demographic.isEmpty()) {
            analytics.addDemographicData(demographic);
        }
    }

    public EventAnalytics getEventAnalytics(Long eventId) {
        return analyticsData.getOrDefault(eventId, new EventAnalytics());
    }

    public Map<String, Object> getEventStatistics(Long eventId) {
        EventAnalytics analytics = getEventAnalytics(eventId);
        Map<String, Object> statistics = new HashMap<>();
        
        statistics.put("viewCount", analytics.getViewCount());
        statistics.put("registrationCount", analytics.getRegistrationCount());
        statistics.put("conversionRate", calculateConversionRate(analytics));
        statistics.put("demographics", analytics.getDemographicData());
        statistics.put("topReferrers", getTopReferrers(analytics.getPopularReferrers()));
        
        return statistics;
    }

    private double calculateConversionRate(EventAnalytics analytics) {
        if (analytics.getViewCount() == 0) return 0.0;
        return (double) analytics.getRegistrationCount() / analytics.getViewCount() * 100;
    }

    private List<Map.Entry<String, Long>> getTopReferrers(List<String> referrers) {
        return referrers.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                    r -> r,
                    java.util.stream.Collectors.counting()
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .collect(java.util.stream.Collectors.toList());
    }

    
} 