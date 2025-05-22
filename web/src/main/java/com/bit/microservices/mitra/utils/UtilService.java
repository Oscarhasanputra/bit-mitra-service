package com.bit.microservices.mitra.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;

public class UtilService {

    public static void copyProperties(Object source, Object target){
        for (PropertyDescriptor propertyDescriptor : BeanUtils.getPropertyDescriptors(source.getClass())) {
            String propertyName = propertyDescriptor.getName();
            try {
                Object propertyValue = PropertyUtils.getProperty(source, propertyName);
                if (propertyValue != null) {
                    PropertyUtils.setProperty(target, propertyName, propertyValue);
                }
            } catch (Exception e) {
                // Handle exceptions as needed
            }
        }
    }
}