package com.example.DwollaTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.*;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Time {

    private String currentTime;

    @JsonIgnore
    private String timezone;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Optional<String> adjustedTime;

    public Time(Instant now, String offset) {
        this.currentTime = now.toString();
        this.adjustedTime = convertOffset(now, offset);
        this.timezone = offset;
    }

    private Optional<String> convertOffset(Instant now, String offset) {
        Optional<String> adjustedTimeString = Optional.empty();

        try {
            if (offset != null && !offset.isEmpty() && !offset.isBlank()) {
                ZoneOffset zone;
                String signProcessed = switch (offset.charAt(0)) {
                    case ' ' -> "+" + offset.substring(1); //TODO Positive Entries is blank
                    case '+', '-' -> offset.substring(0);
                    default -> "";
                };

                zone = ZoneOffset.of(signProcessed);

                adjustedTimeString = Optional.of(LocalDateTime.ofEpochSecond(now.getEpochSecond(), now.getNano(), zone).toString());
            }
        }
        catch (Exception e) {
            System.out.println("Error converting for offset: " + offset);
        }

        return adjustedTimeString;
    }
}
