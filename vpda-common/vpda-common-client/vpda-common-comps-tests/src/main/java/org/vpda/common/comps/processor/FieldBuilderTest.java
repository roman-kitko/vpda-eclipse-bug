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
package org.vpda.common.comps.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.vpda.common.comps.annotations.ComponentInfo;
import org.vpda.common.comps.annotations.FieldInfo;
import org.vpda.common.comps.ui.AbstractFieldAC;
import org.vpda.common.comps.ui.DateFieldAC;
import org.vpda.common.comps.ui.DecimalFieldAC;
import org.vpda.common.comps.ui.PasswordFieldAC;
import org.vpda.common.comps.ui.TextAreaAC;
import org.vpda.common.comps.ui.TextFieldAC;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.ioc.objectresolver.EmptyObjectResolver;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.service.localization.LocKeyInfo;
import org.vpda.common.util.AnnotationConstants;

/**
 * @author kitko
 *
 */
public class FieldBuilderTest {

    TextFieldAC field1;

    @ComponentInfo(localId = "myLocalId", enabled = AnnotationConstants.Boolean.FALSE, visible = AnnotationConstants.Boolean.FALSE, locKey = @LocKeyInfo(path = "locPath", key = "locKey"))
    TextFieldAC field2;

    @FieldInfo(columns = 3, editable = AnnotationConstants.Boolean.FALSE)
    TextFieldAC field3;

    @FieldInfo(formatPattern = "yyyy-DD-MM")
    DateFieldAC dateField;

    @FieldInfo(editable = AnnotationConstants.Boolean.FALSE)
    DecimalFieldAC decField = new DecimalFieldAC("myDecimalField");

    @ComponentInfo(type = TextAreaAC.class, locKey = @LocKeyInfo(path = "locPath", key = "locKey"))
    @FieldInfo(rows = 5)
    AbstractFieldAC textArea;

    PasswordFieldAC passwordField;

    /**
     * Test method
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void testSimpleField() throws NoSuchFieldException, SecurityException {
        FieldContext<TextFieldAC> fieldContext = FieldContext.createRootFieldContext(FieldBuilderTest.class.getDeclaredField("field1"), TextFieldAC.class);
        TextFieldAC b1 = new FieldBuilder().buildByType(fieldContext, (ProcessorResolver) null, EmptyObjectResolver.getInstance());
        assertNotNull(b1);
        assertEquals("field1", b1.getLocalId());
        assertEquals("FieldBuilderTest.field1", b1.getId());
    }

    /**
     * Test method
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void testFieldAnnotatedByComponentInfo() throws NoSuchFieldException, SecurityException {
        FieldContext<TextFieldAC> fieldContext = FieldContext.createRootFieldContext(FieldBuilderTest.class.getDeclaredField("field2"), TextFieldAC.class);
        TextFieldAC b1 = new FieldBuilder().buildByType(fieldContext, (ProcessorResolver) null, EmptyObjectResolver.getInstance());
        assertNotNull(b1);
        assertEquals("myLocalId", b1.getLocalId());
        assertEquals(FieldBuilderTest.class.getSimpleName(), b1.getGroupId());
        assertEquals("FieldBuilderTest.myLocalId", b1.getId());
        assertFalse(b1.isEnabled());
        assertFalse(b1.isVisible());
        assertEquals(new LocKey("locPath", "locKey"), b1.getLocKey());

    }

    /**
     * Test method
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void testFieldAnnotatedByFieldInfo() throws NoSuchFieldException, SecurityException {
        FieldContext<TextFieldAC> fieldContext = FieldContext.createRootFieldContext(FieldBuilderTest.class.getDeclaredField("field3"), TextFieldAC.class);
        TextFieldAC b1 = new FieldBuilder().buildByType(fieldContext, (ProcessorResolver) null, EmptyObjectResolver.getInstance());
        assertNotNull(b1);
        assertEquals("field3", b1.getLocalId());
        assertEquals(FieldBuilderTest.class.getSimpleName(), b1.getGroupId());
        assertEquals("FieldBuilderTest.field3", b1.getId());
        assertTrue(b1.isEnabled());
        assertTrue(b1.isVisible());
        assertFalse(b1.isEditable());
        assertEquals(3, b1.getColumns());

    }

    /**
     * Test method
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void testDateField() throws NoSuchFieldException, SecurityException {
        FieldContext<DateFieldAC> fieldContext = FieldContext.createRootFieldContext(FieldBuilderTest.class.getDeclaredField("dateField"), DateFieldAC.class);
        DateFieldAC b1 = new FieldBuilder().buildByType(fieldContext, (ProcessorResolver) null, EmptyObjectResolver.getInstance());
        assertNotNull(b1);
        assertEquals("dateField", b1.getLocalId());
        assertEquals(FieldBuilderTest.class.getSimpleName(), b1.getGroupId());
        assertEquals("FieldBuilderTest.dateField", b1.getId());
        assertTrue(b1.isEnabled());
        assertTrue(b1.isVisible());
        assertEquals("yyyy-DD-MM", b1.getFormatPattern());
    }

    /**
     * Test method
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void testDecimalField() throws NoSuchFieldException, SecurityException {
        FieldContext<DecimalFieldAC> fieldContext = FieldContext.createFieldContext(ClassContext.createRootInstanceClassContext(getClass(), Object.class, this),
                FieldBuilderTest.class.getDeclaredField("decField"), DecimalFieldAC.class);
        DecimalFieldAC b1 = new FieldBuilder().buildByType(fieldContext, (ProcessorResolver) null, EmptyObjectResolver.getInstance());
        assertNotNull(b1);
        assertSame(decField, b1);
        assertFalse(b1.isEditable());
    }

    /**
     * Test method
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void testTextArea() throws NoSuchFieldException, SecurityException {
        FieldContext<TextAreaAC> fieldContext = FieldContext.createFieldContext(ClassContext.createRootInstanceClassContext(getClass(), Object.class, this),
                FieldBuilderTest.class.getDeclaredField("textArea"), TextAreaAC.class);
        TextAreaAC b1 = new FieldBuilder().buildByType(fieldContext, (ProcessorResolver) null, EmptyObjectResolver.getInstance());
        assertNotNull(b1);
        assertEquals(5, b1.getRows());
        assertEquals(new LocKey("locPath", "locKey"), b1.getLocKey());
    }

    /**
     * Test method
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void testPasswordField() throws NoSuchFieldException, SecurityException {
        FieldContext<PasswordFieldAC> fieldContext = FieldContext.createRootFieldContext(FieldBuilderTest.class.getDeclaredField("passwordField"), PasswordFieldAC.class);
        PasswordFieldAC b1 = new FieldBuilder().buildByType(fieldContext, (ProcessorResolver) null, EmptyObjectResolver.getInstance());
        assertNotNull(b1);
        assertEquals("passwordField", b1.getLocalId());
        assertEquals("FieldBuilderTest.passwordField", b1.getId());
    }

}
