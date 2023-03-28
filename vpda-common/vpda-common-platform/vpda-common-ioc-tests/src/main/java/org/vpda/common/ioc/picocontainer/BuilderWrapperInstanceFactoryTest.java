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
/**
 * 
 */
package org.vpda.common.ioc.picocontainer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.picocontainer.PicoContainer;
import org.picocontainer.script.xml.XMLContainerBuilder;

/**
 * @author kitko
 *
 */
public class BuilderWrapperInstanceFactoryTest {

    @Test
    public void testMakeInstance() throws ClassNotFoundException {
        XMLContainerBuilder builder = new XMLContainerBuilder(getClass().getResource("builderWrapper.xml"), getClass().getClassLoader());
        PicoContainer pico = builder.buildContainer(null, "test", false);
        MyClass mc1 = (MyClass) pico.getComponent("key1");
        assertNotNull(mc1);
        assertEquals("Hello world", mc1.ss);
        assertEquals(Integer.valueOf(777), mc1.ii);

        MyClass mc2 = (MyClass) pico.getComponent("key2");
        assertNotNull(mc2);
        assertEquals("I like this IDE", mc2.ss);
        assertEquals(Integer.valueOf(222), mc2.ii);

    }

    /** */
    public static final class MyClass {
        String ss;
        Integer ii;

        private MyClass(Builder builder) {
            this.ss = builder.getSs();
            this.ii = builder.getIi();
        }

        /** */
        public static final class Builder implements org.vpda.common.util.Builder<MyClass> {
            String ss;
            Integer ii;

            /**
             * @return the ss
             */
            public String getSs() {
                return ss;
            }

            /**
             * @param ss the ss to set
             */
            public void setSs(String ss) {
                this.ss = ss;
            }

            /**
             * @return the ii
             */
            public Integer getIi() {
                return ii;
            }

            /**
             * @param ii the ii to set
             */
            public void setIi(Integer ii) {
                this.ii = ii;
            }

            @Override
            public MyClass build() {
                return new MyClass(this);
            }

            @Override
            public Class<? extends MyClass> getTargetClass() {
                return MyClass.class;
            }

        }

    }

}
