package org.vpda.common.dto.model.gen.impl;

import java.lang.annotation.Annotation;

import org.vpda.common.dto.annotations.StaticModelClass;

interface StaticModelClassI extends StaticModelClass{

    public static StaticModelClassI create(Class<?> clazz){
        
        return new StaticModelClassI() {
            @Override
            public Class<?> value() {
                return clazz;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return StaticModelClass.class;
            }
        };
    }
    
}
