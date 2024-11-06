package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ApiService {

    private static final Logger logger = Logger.getLogger(ApiService.class.getName());
    private final RestTemplate restTemplate;

    @Autowired
    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LocalDateTime getCurrentDateTime() {
        String url = "https://www.timeapi.io/api/Time/current/zone?timeZone=America/Argentina/Buenos_Aires";
        try {
            TimeApiResponse response = restTemplate.getForObject(url, TimeApiResponse.class);
            return response != null ? response.getDateTime() : null;
        } catch (RestClientException e) {
            logger.log(Level.SEVERE, "Error retrieving current date and time from API", e);
            return null;
        }
    }

    private static class TimeApiResponse {
        private LocalDateTime dateTime;

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }
    }
}
