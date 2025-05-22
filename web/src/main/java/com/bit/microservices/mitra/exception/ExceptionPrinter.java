package com.bit.microservices.mitra.exception;


import lombok.Data;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Data
public class ExceptionPrinter {

    private Throwable exception;
    private String message;
    private String trace;

    public ExceptionPrinter(Throwable exception) {
        this.exception = exception;

        StringBuilder builder = new StringBuilder();

        builder.append("\n");
        builder.append("Caused by: ");
        builder.append("\n");
        builder.append(exception.toString());

        List<StackTraceElement> stElements = Arrays.asList(exception.getStackTrace());

        StackTraceElement first = stElements.get(0);
        builder.append("\n\n");
        builder.append("at ");
        builder.append(first.getClassName());
        builder.append(".");
        builder.append(first.getMethodName());
        builder.append(" (");
        builder.append(first.getClassName());
        builder.append(".java: ");
        builder.append(first.getLineNumber());
        builder.append(")");

        if (stElements.size() > 1) {
            builder.append("\n");
            builder.append("...");

//        	StackTraceElement firstElement = stElements.stream().filter(x -> x.getClassName().indexOf("com.bit.microservices") > -1).findFirst().get();
//        	int firstIndex = stElements.indexOf(firstElement);
//        	int index = lastIndexOf(stElements, s -> s.getClassName().indexOf("com.bit.microservices") > -1);
//
//    		Set<StackTraceElement> elements = stElements.subList(Math.max(1, firstIndex), index).stream()
//    				.collect(Collectors.toSet());

            Set<StackTraceElement> elements = stElements.subList(1, stElements.size()).stream()
                    .filter(s -> s.getClassName().indexOf("com.bit.microservices") > -1)
                    .collect(Collectors.toSet());

            for (StackTraceElement ste: elements) {
                builder.append("\n");
                builder.append("at ");
                builder.append(ste.getClassName());
                builder.append(".");
                builder.append(ste.getMethodName());
                builder.append(" (");
                builder.append(ste.getClassName());
                builder.append(".java: ");
                builder.append(ste.getLineNumber());
                builder.append(")");
            }
        }

        this.message = builder.toString();

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);

        this.trace = sw.toString();
    }

    private static <E> int lastIndexOf(List<E> list, Predicate<E> predicate) {
        for (ListIterator<E> iter = list.listIterator(list.size()); iter.hasPrevious(); )
            if (predicate.test(iter.previous()))
                return iter.nextIndex();
        return -1;
    }
}
