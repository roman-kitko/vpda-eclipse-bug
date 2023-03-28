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
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vpda.common.processor.ItemsClassProcessor;
import org.vpda.common.processor.annotation.TargetItemProcessingInfo.TargetItemProcessingInfos;
import org.vpda.common.util.AnnotationConstants;

/**
 * Information for processing of class creating just particular items. Typically
 * we can create arbitrary classes that holds just fields and this classes need
 * very special kind of processor. When we need to define which processor to
 * use, we can place the annotation on the type. We can place annotation also on
 * field. In this case we will use this annotation just for field builder that
 * can use ItemsClassProcessor to build particular field. In this case
 * targetItemClass is mandatory
 * 
 * @author kitko
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
@Repeatable(TargetItemProcessingInfos.class)
@GroupFiltering
@TargetClassFiltering
public @interface TargetItemProcessingInfo {
    /** Class of processor */
    public Class<? extends ItemsClassProcessor> processorClass() default ItemsClassProcessor.class;

    /** Class of item we are interested in */
    @TargetClassMethod
    public Class<?> targetItemClass() default AnnotationConstants.UNDEFINED_TYPE.class;

    /** Switch whether to process also super class */
    boolean processSuper() default true;

    /** Switch whether to cache result */
    boolean cacheProcessedObject() default true;

    /** Array of groups that this info is applicable for */
    @GroupFilteringMethod
    Class[] processGroups() default {};

    /**
     * Separate annotation just for TargetItem class.
     * 
     * @author kitko
     *
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE, ElementType.FIELD })
    public @interface TargetItemClass {
        /** Type of item */
        Class<?> value();
    }

    /**
     * Array of {@link TargetItemProcessingInfos} infos
     * 
     * @author kitko
     *
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface TargetItemProcessingInfos {
        /** Array of target item info */
        public TargetItemProcessingInfo[] value() default {};
    }
}
