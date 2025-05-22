package com.bit.microservices.mitra.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
public class SearchRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4237891482631742120L;

    @Schema(description = "Application Code",
            example = "LIST",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String requestType;

    @Schema(
            description = "Number Maximum of items to being displayed",
            example = "10",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Integer size;

    @Schema(
            description = "Number Maximum of items to being displayed",
            example = "1",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Integer page;

    @Schema(description = "Application Code",
            example = "{}",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> sortBy;

    @Schema(description = "Application Code",
            example = "{}",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, FilterByClass> filterBy;


    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FilterByClass implements Serializable {
        @Serial
        private static final long serialVersionUID = -3396951405427479918L;
        private transient Object value;
        private List<String> values;
        private RangeNumber number;
        private RangeDateTime rangeDateTime;
        private RangeDate rangeDate;
        private Boolean isInvalidFormat = false;

        @JsonCreator
        public FilterByClass(JsonNode jsonNode) {
            try{
                if (jsonNode.isArray()) {
                    // Handle list values
                    this.values = new ArrayList<>();
                    for (JsonNode node : jsonNode) {
                        this.values.add(node.asText());
                    }
                } else if (jsonNode.isObject()) {
                    // Determine the object type dynamically
                    if (jsonNode.has("min") || jsonNode.has("max")) {
                        BigDecimal min = Objects.isNull(jsonNode.get("min")) || jsonNode.get("min").asText().isEmpty()?null:new BigDecimal(jsonNode.get("min").asText());
                        BigDecimal max = Objects.isNull(jsonNode.get("max")) || jsonNode.get("max").asText().isEmpty()?null:new BigDecimal(jsonNode.get("max").asText());
                        this.number = new RangeNumber(
                                min,
                                max
                        );
                    } else if (jsonNode.has("startDateTime") || jsonNode.has("endDateTime")) {

                        OffsetDateTime startDateTime = (Objects.isNull(jsonNode.get("startDateTime")) || jsonNode.get("startDateTime").asText().isEmpty())?null:OffsetDateTime.parse(jsonNode.get("startDateTime").asText());

                        OffsetDateTime endDateTime = Objects.isNull(jsonNode.get("endDateTime")) || jsonNode.get("endDateTime").asText().isEmpty()?null:OffsetDateTime.parse(jsonNode.get("endDateTime").asText());

                        this.rangeDateTime = new RangeDateTime(
                                startDateTime,
                                endDateTime
                        );
                    } else if (jsonNode.has("startDate") || jsonNode.has("endDate")) {
                        LocalDate startDate = Objects.isNull(jsonNode.get("startDate")) || jsonNode.get("startDate").asText().isEmpty()?null:LocalDate.parse(jsonNode.get("startDate").asText());
                        LocalDate endDate  = Objects.isNull(jsonNode.get("endDate")) || jsonNode.get("endDate").asText().isEmpty()?null:LocalDate.parse(jsonNode.get("endDate").asText());

                        this.rangeDate = new RangeDate(
                                startDate,
                                endDate
                        );
                    }
                } else if (jsonNode.isBoolean()) {
                    // Handle single values
                    this.value = jsonNode.asBoolean();
                }
            }catch (Exception err){
                this.isInvalidFormat = true;
            }
        }

        @Getter
        @Setter
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class RangeNumber implements Serializable {
            @Serial
            private static final long serialVersionUID = -3567864782865592166L;
            private BigDecimal min;
            private BigDecimal max;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class RangeDateTime implements Serializable {
            @Serial
            private static final long serialVersionUID = 731491968244929734L;
            private OffsetDateTime startDateTime;
            private OffsetDateTime endDateTime;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class RangeDate implements Serializable {
            @Serial
            private static final long serialVersionUID = -2691093755202268847L;
            private LocalDate startDate;
            private LocalDate endDate;
        }
    }
}
