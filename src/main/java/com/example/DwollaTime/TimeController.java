package com.example.DwollaTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class TimeController {

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/time")
    @ResponseBody
    public String getTime(@RequestParam(required = false) String timeZoneOffset) throws JsonProcessingException {
        Instant currentTimeInUTC = Instant.now();
        Time currentTime = new Time(currentTimeInUTC, timeZoneOffset);
        return objectMapper.writeValueAsString(currentTime);
    }


}
