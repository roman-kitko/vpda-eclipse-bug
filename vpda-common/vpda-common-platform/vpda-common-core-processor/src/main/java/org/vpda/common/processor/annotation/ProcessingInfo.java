/**
 * View provider driven applications - java application framework for developing RIA
 * Copyright (C) 2009-2022 Roman Kitko, Slovakia
 *
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.gnu.org/licenses/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vpda.common.processor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.ProcessingFieldFilterFactory;
import org.vpda.common.processor.annotation.ProcessingInfo.ProcessingInfos;
import org.vpda.common.util.AnnotationConstants;

/**
 * Defines properties for processing class. This annotation can be placed on
 * class, which means we influence which processor will be used for processing
 * of class. When annotating the field with this annotation, actually we say
 * that when building the target type from the field, and field builder will use
 * processor for its type, that we want to use that particular processor.
 * 
 * @author kitko
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
@Inherited
@Repeatable(ProcessingInfos.class)
@GroupFiltering
@TargetClassFiltering
public @interface ProcessingInfo {
    /**
     * Specifies processor class
     * 
     * @return processor class
     */
    Class<? extends ClassProcessor> processorClass() default ClassProcessor.class;

    /**
     * Specifies whether processor should process also super class for fields
     * 
     * @return by default true
     */
    boolean processSuper() default true;

    /**
     * Specify whether we can cache result object of processing
     * 
     * @return true if we can cache result object by Processor class and being
     *         processed class
     */
    boolean cacheProcessedObject() default true;

    /**
     * Defines groups that this info is regarding to. Class can be annotated by more
     * ProcessingInfo for different purposes and group can be consider something
     * like discriminator
     */
    @GroupFilteringMethod
    Class[] processGroups() default {};

    /** Target class of constructed object */
    @TargetClassMethod
    Class<?> targetClass() default AnnotationConstants.UNDEFINED_TYPE.class;

    /**
     * @return filter used when processing fields
     */
    public Class<? extends ProcessingFieldFilterFactory> fieldFilterFactory() default ProcessingFieldFilterFactory.class;

    /** Repeatable container annotation type */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    public @interface ProcessingInfos {
        /** List of infos */
        ProcessingInfo[] value() default {};
    }
}
