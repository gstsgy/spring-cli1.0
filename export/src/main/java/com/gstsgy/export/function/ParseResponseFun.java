package com.gstsgy.export.function;

import java.util.List;

@FunctionalInterface
public interface ParseResponseFun {
    List<Object> parse(Object obj);
}
