package com.bit.microservices.mitra.model;

import com.bit.microservices.model.bit.va.configuration.FieldDetailDTO;
import com.bit.microservices.model.bit.va.configuration.FilterableField;
import com.bit.microservices.model.bit.va.configuration.SortDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;


@Data
@NoArgsConstructor
public class GeneratedQueryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7620537983725036142L;

    Class classReflect;
    HashMap<String, FieldDetailDTO> fields;
    HashMap<String, FieldDetailDTO> havingFields;
    HashMap<String,FieldDetailDTO> allFields;
    String where;
    String having;
    Set<FilterableField> filterableFields;
    Set<String> groups;
    Set<String> groupsOnParentClass;
    Set<String> selects;
    SortDto order;
}