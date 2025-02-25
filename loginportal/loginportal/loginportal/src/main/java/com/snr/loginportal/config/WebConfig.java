package com.snr.loginportal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.Map;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Custom method to resolve pageable sorting dynamically
    /*public Pageable resolvePageable(Map<String, String> requestParams, Pageable pageable) {
       List<Sort.Order> orders = new ArrayList<>();

        pageable.getSort().forEach(sort -> {
            String propertyName = sort.getProperty();
            String dirKey = propertyName + ".dir";

            if (requestParams.containsKey(dirKey)) {
                String dirValue = requestParams.get(dirKey);
                Sort.Direction direction = "desc".equalsIgnoreCase(dirValue) ? Sort.Direction.DESC : Sort.Direction.ASC;
                orders.add(new Sort.Order(direction, propertyName));
            }
        });

        return orders.isEmpty()
                ? pageable
                : PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }*/


    // Custom method to resolve pageable without sorting
    public Pageable resolvePageable(Map<String, String> requestParams, Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
    }

}