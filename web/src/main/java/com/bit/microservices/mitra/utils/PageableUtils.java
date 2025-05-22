package com.bit.microservices.mitra.utils;

import com.bit.microservices.mitra.model.request.SearchRequestDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageableUtils {


    public static Pageable pageableUtils(SearchRequestDTO request) {
        int page = 0;
        int size = 20;

        // Handle page and size
        if (request.getPage() > 0) {
            page = request.getPage() - 1;  // Adjust for 0-based page index
        }
        if (request.getSize() > 0) {
            size = request.getSize();
        }

        // Build the Pageable object
        List<Sort.Order> orders = new ArrayList<>();

        // Handling sorting based on the "sortBy" map
        Map<String, String> sortBy = request.getSortBy();
        if (sortBy != null && !sortBy.isEmpty()) {
            for (Map.Entry<String, String> entry : sortBy.entrySet()) {
                String field = entry.getKey();
                String direction = entry.getValue().toUpperCase();  // Ensure sorting direction is uppercase

                // Determine the sort direction
                Sort.Order order = direction.equals("ASC") ?
                        Sort.Order.asc(field) : Sort.Order.desc(field);

                orders.add(order);
            }
        }

        // Create a Sort object if there are any sort orders
        Sort sort = orders.isEmpty() ? Sort.unsorted() : Sort.by(orders);

        // Create the Pageable object with sorting
        return PageRequest.of(page, size, sort);
    }
}
