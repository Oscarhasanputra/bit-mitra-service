package com.bit.microservices.mitra.model.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum SearchFieldEnum {
    USER_INTERNAL(
            Map.ofEntries(
                    Map.entry("code", "code"),
                    Map.entry("name", "name"),
                    Map.entry("email", "email"),
                    Map.entry("status_active", "isActive")
            )
    ),
    HEAD_POSITION(
            Map.ofEntries(
                    Map.entry("code", "code"),
                    Map.entry("name", "name"),
                    Map.entry("status_active", "isActive")
            )
    ),
    POSITION(
            Map.ofEntries(
                    Map.entry("code", "code"),
                    Map.entry("name", "name"),
                    Map.entry("status_active", "isActive")
            )
    );

    private final Map<String, String> qualifiedSearchColumn;

    private SearchFieldEnum(Map<String, String> qualifiedSearchColumn) {
        this.qualifiedSearchColumn = qualifiedSearchColumn;
    }

    public static String getColumnName(SearchFieldEnum searchFieldEnum, String key) {
        return searchFieldEnum.qualifiedSearchColumn.get(key);
    }

    public List<String> getQualifiedSearchColumn() {
        return this.qualifiedSearchColumn.keySet().stream().toList();
    }

    public static List<String> getInvalidColumns(SearchFieldEnum searchFieldEnum, List<String> inputColumns) {
        List<String> invalidColumns = new ArrayList<>();
        List<String> validColumns = searchFieldEnum.getQualifiedSearchColumn();

        for (String column : inputColumns) {
            if (!validColumns.contains(column)) {
                invalidColumns.add(column);
            }
        }

        return invalidColumns;
    }
}
