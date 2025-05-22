package com.bit.microservices.mitra.utils.UtilityService;

import com.bit.microservices.exception.ExceptionPrinter;
import com.bit.microservices.model.bit.va.ConditionQueryEnum;
import com.bit.microservices.model.bit.va.ConjunctionConditionEnum;
import com.bit.microservices.model.bit.va.WhereConditionDTO;
import com.bit.microservices.model.bit.va.configuration.FieldDetailDTO;
import com.bit.microservices.model.bit.va.configuration.FilterableField;
import com.bit.microservices.mitra.configuration.ParserFactory;
import com.bit.microservices.mitra.model.GeneratedQueryDTO;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.request.SearchRequestDTO;
import com.bit.microservices.utils.FieldInfo;
import com.bit.microservices.utils.PageImplCustom;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class QueryService {



    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    @Autowired
    private EntityManager em;

    @Autowired
    private ParserFactory parserFactory;



    public Pageable pageableUtils(SearchRequestDTO request) {
        int page = 1;
        int size = 20;


        if (request.getPage() > 0) {
            page = request.getPage();
        }

        if (request.getSize() > 0) {
            size = request.getSize();
        }


        Pageable pageable = PageRequest.of(page, size);

        return pageable;

    }



    public String generateExpression(FieldDetailDTO field, WhereConditionDTO condition) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String fieldName = field.getField();
        try{
            Class queryClazz = ConditionQueryEnum.valueOf(condition.getCondition().toUpperCase()).queryClass;
            if(!Objects.isNull(queryClazz)){
                String queryNative = ConditionQueryEnum.valueOf(condition.getCondition().toUpperCase()).queryNative;
                queryNative = queryNative.replaceAll("\\$field",fieldName);
                Object value =null;
                String valueTyped = "";
                if(queryClazz.equals(Collection.class)){
                    List<Object> listTest = (List<Object>) condition.getValue();

                    value = "('"+listTest.stream().map(String::valueOf).collect(Collectors.joining("','"))+"')";

                    valueTyped =value.toString();
                }else{
                    value =parserFactory.getParsedValue(condition.getValue(),field.getFieldType(),field.getEntityType());
                    valueTyped ="'"+value.toString()+"'";
                }
                queryNative = queryNative.replaceAll("\\$value",valueTyped);

                return queryNative;
            }
            else {
                String queryNative = ConditionQueryEnum.valueOf(condition.getCondition().toUpperCase()).queryNative;
                queryNative = queryNative.replaceAll("\\$field",fieldName);

                return queryNative;
            }


        }catch (Exception e){
            ExceptionPrinter print = new ExceptionPrinter(e);
            log.error(print.getMessage());

            return null;
        }
    }




    private GeneratedQueryDTO generateQueryDto(Class<?> clazz, GeneratedQueryDTO dslDto) throws InvocationTargetException, IllegalAccessException{
        HashMap<String,FieldDetailDTO> mapExp = dslDto.getFields();
        HashMap<String,FieldDetailDTO> allExp = dslDto.getAllFields();

        HashMap<String,FieldDetailDTO> mapHavingExp = dslDto.getHavingFields();
        Field[] fields = clazz.getDeclaredFields();
        Set<FilterableField> filterableFields = dslDto.getFilterableFields();
        Set<String> groups = dslDto.getGroups();
        Set<String> selects = dslDto.getSelects();
        for(Field field:fields){

            Annotation fieldAnnotation =field.getAnnotation(FieldInfo.class);

            FieldInfo fieldInfo = (FieldInfo) fieldAnnotation;
            if(!Objects.isNull(fieldInfo)){

                String entityName = fieldInfo.entityName();
                String fieldName = fieldInfo.fieldName();
                String exampleValue = fieldInfo.example();
                String entityField= fieldInfo.entityField().isEmpty()?field.getName(): fieldInfo.entityField();
                String entityType= fieldInfo.entityType();
                String getEntityType = entityType.substring(0,1).toUpperCase()+entityType.substring(1).toLowerCase();
                String groupFunc = fieldInfo.groupFunc();
                boolean isHavingFilter = fieldInfo.havingFilter();
                String alias = fieldInfo.alias();
                boolean filterAble = fieldInfo.filterable();
                boolean selectAble = fieldInfo.selectable();

                String fieldValue = fieldName.isEmpty()?null:fieldName;
                if(filterAble)
                    filterableFields.add(new FilterableField(field.getName(),alias.isEmpty()?field.getName():alias,entityType,exampleValue));


                if(fieldValue!=null && selectAble){

                    if(filterAble){
                        if(!groupFunc.isEmpty()) {
                            try {
                                fieldValue = groupFunc+"("+fieldName+")";
                                FieldDetailDTO fieldDto = new FieldDetailDTO(field.getType(),fieldValue,entityName+"."+fieldValue,fieldInfo.entityType());
                                mapHavingExp.put(field.getName(),fieldDto);
                                allExp.put(field.getName(),fieldDto);
                            } catch (Exception e) {

                                ExceptionPrinter print = new ExceptionPrinter(e);
                                log.error(print.getMessage());
//                                throw new RuntimeException(e);
                            }
                        }else if(isHavingFilter && groupFunc.isEmpty()){
                            groups.add(entityName+"."+fieldValue);
                            FieldDetailDTO fieldDto = new FieldDetailDTO(field.getType(),fieldValue,entityName+"."+fieldValue,fieldInfo.entityType());
                            mapHavingExp.put(field.getName(),fieldDto);
                            allExp.put(field.getName(),fieldDto);
                        }
                        else{
                            groups.add(entityName+"."+fieldValue);
                            FieldDetailDTO fieldDto = new FieldDetailDTO(field.getType(),fieldValue,entityName+"."+fieldValue,fieldInfo.entityType());
                            mapExp.put(field.getName(),fieldDto);
                            allExp.put(field.getName(),fieldDto);
                        }

                    }

                    selects.add(entityName+"."+fieldValue);

                }

            }


        }
        dslDto.setFields(mapExp);
        dslDto.setHavingFields(mapHavingExp);
        dslDto.setAllFields(allExp);
        dslDto.setFilterableFields(filterableFields);
        dslDto.setGroups(groups);
        dslDto.setSelects(selects);
        return dslDto;
    }

    public <T> List<T> transForm(Class<T> clazz, GeneratedQueryDTO dslDto, List<Tuple> results) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Field[] fields = clazz.getDeclaredFields();
        List<T> arrayList = new ArrayList<>();

        for (Tuple result : results) {

            T object =clazz.getDeclaredConstructor().newInstance();
            for (Field field : fields) {
                String fieldName = field.getName();

                String methodName= "set"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
                FieldDetailDTO detailDTO =dslDto.getFields().get(fieldName);
                if(Objects.isNull(detailDTO))
                    detailDTO=dslDto.getHavingFields().get(fieldName);


                if(!Objects.isNull(detailDTO)){
                    try{

                        Object data =result.get(detailDTO.getKey());


                        if(!Objects.isNull(data)){

                            Method method =clazz.getMethod(methodName,field.getType());

                            method.invoke(object,mapTo(data,field.getType()));
                        }
                    }catch (Exception e){
                        ExceptionPrinter print = new ExceptionPrinter(e);
                        log.error(print.getMessage());
                    }

                }

            }
            arrayList.add(object);

        }
        return arrayList;
    }
    private LocalDateTime map(OffsetDateTime dateTime){
        if (dateTime != null){
            return dateTime.toLocalDateTime();
        } else {
            return null;
        }
    }

    private OffsetDateTime map(LocalDateTime dateTime){

        if (dateTime != null){

            return dateTime.atOffset(ZoneOffset.UTC);
        } else {
            return null;
        }
    }
    private Object map(Date dateTime, Class<?> clazzTo){
        if (dateTime == null) {
            return null;
        }

        if(clazzTo.equals(OffsetDateTime.class)){
            return dateTime.toInstant().atOffset(ZoneOffset.UTC);
        }
        else return dateTime.toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime();
    }



//    private Object map(Timestamp dateTime, Class<?> clazzTo){
//        if (dateTime == null) {
//            return null;
//        }
//        if(clazzTo.equals(OffsetDateTime.class))
//            return OffsetDateTime.from(dateTime.toInstant());
//        else return LocalDateTime.from(dateTime.toInstant());
//
//    }



    private Object mapTo(Object data,Class<?> clazzTo){
//        if

        if(clazzTo.equals(LocalDateTime.class) && data instanceof OffsetDateTime){
            OffsetDateTime time= (OffsetDateTime) data;
            return map(time);
        }else if(clazzTo.equals(OffsetDateTime.class) && data instanceof LocalDateTime){
            LocalDateTime time = (LocalDateTime) data;
            return map(time);
        }
        else if (data instanceof Date){
            Date time = (Date) data;
            Object dataTemp = map(time,clazzTo);


            return dataTemp;


        }
        else{
            return data;
        }
    }


    public GeneratedQueryDTO generateQueryMetaData(Class<?> clazz){
        try {
            HashMap<String,FieldDetailDTO> mapExp = new HashMap<>();
            HashMap<String,FieldDetailDTO> mapHavingExp = new HashMap<>();

            HashMap<String,FieldDetailDTO> allExp = new HashMap<>();
            HashMap<String,Class> mapParsedValue = new HashMap<>();
            Field[] fields = clazz.getDeclaredFields();
            Set<FilterableField> filterableFields = new HashSet<>();
            Set<String> groups = new HashSet<>();
            Set<String> selects = new HashSet<>();
            GeneratedQueryDTO generatedDslDto = new GeneratedQueryDTO();

            generatedDslDto.setFields(mapExp);
            generatedDslDto.setHavingFields(mapHavingExp);
            generatedDslDto.setFilterableFields(filterableFields);
            generatedDslDto.setGroups(groups);
            generatedDslDto.setSelects(selects);
            generatedDslDto.setClassReflect(clazz);
            generatedDslDto.setAllFields(allExp);
            GeneratedQueryDTO dslDto  =generateQueryDto(clazz,generatedDslDto);
            return dslDto;
        }
        catch (Exception e){
            ExceptionPrinter print = new ExceptionPrinter(e);
            log.error(print.getMessage());
        }
        return null;

    }

    public void setConditionQuery(GeneratedQueryDTO dslDto,List<WhereConditionDTO> where){

        if(!Objects.isNull(where)){

            dslDto.setWhere(generatedBooleanExpression(where,dslDto.getFields(),dslDto.getAllFields()));

            dslDto.setHaving(generatedBooleanExpression(where,dslDto.getHavingFields(),dslDto.getAllFields()));

        }
    };


    public <T> Page<T> find(String query, GeneratedQueryDTO dslDto,SearchRequestDTO requestDTO,Pageable pageable){
        List<String> orderByQueryList = new ArrayList<>();
        for (Map.Entry<String, String> field : requestDTO.getSortBy().entrySet()) {

            String fieldName = field.getKey();
            String sortValue = field.getValue();

            if(!Objects.isNull(dslDto.getAllFields().get(fieldName))){
                orderByQueryList.add(dslDto.getAllFields().get(fieldName).getField()+" "+sortValue);
            }
        }
        if(!orderByQueryList.isEmpty() && orderByQueryList.size()>0){
            query = query.concat(" order by "+String.join(",",orderByQueryList));
        }
        log.info("query full : {}",query);
        Query countRow = this.em.createNativeQuery("" +
                "select count(*) from ("+query+") as sub"
        );
        int nextPage =(pageable.getPageNumber())*pageable.getPageSize();
        int startPage = (pageable.getPageNumber()-1)*pageable.getPageSize();
        query = query.concat(" limit "+pageable.getPageSize()).concat(" offset "+startPage);
        Query queryNative = this.em.createNativeQuery(query,Tuple.class);

        List<Tuple> data = queryNative.getResultList();

        Object totalRow = countRow.getSingleResult();

        try {
            List<T> dataResult = transForm(dslDto.getClassReflect(), dslDto,data);

            return new PageImplCustom<>(dataResult,pageable,Long.parseLong(totalRow.toString()),dslDto.getFilterableFields().stream().toList());
        }catch (Exception e){
            ExceptionPrinter print = new ExceptionPrinter(e);
            log.error(print.getMessage());
            String message = String.format(ResponseCodeMessageEnum.FAILED_CUSTOM.getMessage(), e.getCause());
            String messageCode = ResponseCodeMessageEnum.FAILED_CUSTOM.getCode();

        }

        return null;
    }

    public String generatedBooleanExpression(List<WhereConditionDTO> where, HashMap<String, FieldDetailDTO> fields,HashMap<String, FieldDetailDTO> allFields){


        String finalExp = null;

        try {

            for(WhereConditionDTO condition: where){

                FieldDetailDTO path = fields.get(condition.getField());



                if(!Objects.isNull(path)) {


                    if(Objects.isNull(finalExp)){
                        finalExp = generateExpression(path,condition);

                    }

                    else{
                        String conjunction = ConjunctionConditionEnum.valueOf(condition.getConjunction().toUpperCase()).queryDsl;

                        String anotherExpresion = generateExpression(path,condition);
                        if(!Objects.isNull(anotherExpresion))
                            finalExp =finalExp + " "+conjunction+" "+anotherExpresion;



                    }

                    if(!Objects.isNull(condition.getInner()) && !condition.getInner().getWhere().isEmpty()){
                        String conjunction = ConjunctionConditionEnum.valueOf(condition.getConjunction().toUpperCase()).queryDsl;
                        String innerExp ="("+generatedBooleanExpression(condition.getInner().getWhere(),fields,allFields)+")";
                        finalExp =finalExp + " "+conjunction+" "+innerExp;
                    }
                }else if(!Objects.isNull(condition.getInner())){

                    if(Objects.isNull(finalExp)){
                        finalExp="("+generatedBooleanExpression(condition.getInner().getWhere(),fields,allFields)+")";
                    }
                    else{
                        String conjunction = ConjunctionConditionEnum.valueOf(condition.getInner().getConjunction().toUpperCase()).queryDsl;
                        String innerExp ="("+generatedBooleanExpression(condition.getInner().getWhere(),fields,allFields)+")";
                        finalExp =finalExp + " "+conjunction+" "+innerExp;
                    }

                }


            }

        }
        catch (Exception e) {

            ExceptionPrinter print = new ExceptionPrinter(e);
            log.error("{}error query {}{}", LINE_SEPARATOR, print.getMessage());
        }

        if(Objects.isNull(finalExp))
            finalExp ="true = true";


        return finalExp;

    }



    public BooleanExpression generateWhereCondition(List<WhereConditionDTO> param, EntityPathBase clazz) throws Exception{
        Field[] fields = clazz.getClass().getDeclaredFields();
        BooleanExpression finalExpression =null;

        for(WhereConditionDTO condition: param){
            Field field = Arrays.stream(fields).filter(x -> x.getName().equalsIgnoreCase(condition.getField())).findFirst().orElse(null);

            if (field != null){
                try {
                    if(Objects.isNull(finalExpression))
                        finalExpression= generateExpressionReflect(field,condition,clazz);
                    else{

                        Method class2 = finalExpression.getClass().getMethod(ConjunctionConditionEnum.valueOf(condition.getConjunction().toUpperCase()).queryDsl, Predicate.class);
//
                        BooleanExpression anotherExpresion =generateExpressionReflect(field,condition,clazz);

                        finalExpression =(BooleanExpression) class2.invoke(finalExpression,anotherExpresion);

                    }


                } catch (Exception e) {
                    ExceptionPrinter print = new ExceptionPrinter(e);
                    log.error(print.getMessage());
                    throw new Exception("error Query in field "+condition.getField() +" with value : "+condition.getValue());
                }
            }

        }
        if(Objects.isNull(finalExpression))
            finalExpression =Expressions.asBoolean(true).isTrue();

        return finalExpression;
    }

    private static BooleanExpression generateExpressionReflect(Field field, WhereConditionDTO param,EntityPathBase clazz) throws Exception{

        try {
            Method class2 = null;
            BooleanExpression obj= null;
            if(!Objects.isNull(ConditionQueryEnum.valueOf(param.getCondition().toUpperCase()).queryClass)){
                class2 =field.getType().getMethod(ConditionQueryEnum.valueOf(param.getCondition().toUpperCase()).queryDsl,ConditionQueryEnum.valueOf(param.getCondition().toUpperCase()).queryClass);
                obj=(BooleanExpression) class2.invoke(field.get(clazz),param.getValue());
            }
            else {
                class2 =field.getType().getMethod(ConditionQueryEnum.valueOf(param.getCondition().toUpperCase()).queryDsl,null);

                obj=(BooleanExpression) class2.invoke(field.get(clazz),null);
            }


            return obj;
        } catch (IllegalAccessException e) {
            ExceptionPrinter print = new ExceptionPrinter(e);
            log.error(print.getMessage());

            throw new Exception(e.getMessage());
        } catch (NoSuchMethodException e) {
            ExceptionPrinter print = new ExceptionPrinter(e);
            log.error(print.getMessage());

            throw new Exception(e.getMessage());
        } catch (InvocationTargetException e) {
            ExceptionPrinter print = new ExceptionPrinter(e);
            log.error(print.getMessage());

            throw new Exception(e.getMessage());
        }

    }
}
