package com.example.eduforum.activity.util;

import com.google.firebase.Timestamp;

import java.util.Map;
import java.util.Objects;

public class ConvertUtil {
    public static Timestamp convertMapToTimestamp(Map<String, Object> data, String key) {
        if (data.containsKey(key) && data.get(key) instanceof Map) {
            Map<String, Object> timestampMap = (Map<String, Object>) data.get(key);
            long seconds;
            try {
                seconds = (long) timestampMap.get("_seconds");
            } catch (ClassCastException e) {
                seconds = ((Integer) Objects.requireNonNull(timestampMap.get("_seconds"))).longValue();
            }
            Integer nanoseconds = (Integer) timestampMap.get("_nanoseconds");
            assert nanoseconds != null;
            return new Timestamp(seconds, nanoseconds);
        }
        return Timestamp.now(); // fallback to the current time if conversion fails
    }
}
