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
package org.vpda.common.comps.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.vpda.common.comps.ui.ContainerBorderLayout.BorderLayoutConstraint;

/**
 * @author kitko
 *
 */
public class ACContainerTest extends AbstractComponentTest {

    /**
     * Test method for
     * {@link org.vpda.common.comps.ui.ContainerAC#getComponent(java.lang.String)}.
     */
    @Test
    public void testGet() {
        ContainerAC c = createTestee();
        PushButtonAC pushButtonViewProviderComponent = new PushButtonAC("testButton");
        c.addComponent(pushButtonViewProviderComponent);
        assertNotNull(pushButtonViewProviderComponent.getId());
    }

    /**
     * Test method for
     * {@link org.vpda.common.comps.ui.ContainerAC#getContainerLayout()}.
     */
    @Test
    public void testGetContainerLayout() {
        ContainerAC c = createTestee();
        assertNotNull(c.getContainerLayout());
        ContainerVerticalLayout l = new ContainerVerticalLayout();
        c.setContainerLayout(l);
        assertSame(l, c.getContainerLayout());
    }

    /**
     * Test method
     */
    @Test
    public void testAddComponentViewProviderComponentOfTComponentLayoutConstraint() {
        ContainerAC c = createTestee();
        c.setContainerLayout(new ContainerBorderLayout());
        PushButtonAC pushButtonViewProviderComponent = new PushButtonAC("testButton");
        c.addComponent(pushButtonViewProviderComponent, ContainerBorderLayout.BorderLayoutConstraint.WEST);
        assertSame(BorderLayoutConstraint.WEST, c.getComponentLayoutConstraint(pushButtonViewProviderComponent.getId()));
    }

    /**
     * Test method
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testAddComponentViewProviderComponentOfT() {
        ContainerAC c = createTestee();
        AbstractComponent b = new PushButtonAC("testButton");
        c.addComponent(b, BorderLayoutConstraint.WEST);
        assertSame(b, c.getComponent(b.getId()));
    }

    /**
     * Test method for
     * {@link org.vpda.common.comps.ui.ContainerAC#getComponentsCount()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetComponentsCount() {
        ContainerAC c = createTestee();
        assertEquals(0, c.getComponentsCount());
        AbstractComponent b = new PushButtonAC("testButton");
        c.addComponent(b, BorderLayoutConstraint.WEST);
        assertEquals(1, c.getComponentsCount());
        c.removeAllComponents();
        assertEquals(0, c.getComponentsCount());
    }

    /**
     * Test method for
     * {@link org.vpda.common.comps.ui.ContainerAC#getComponent(int)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetViewProviderComponent() {
        ContainerAC c = createTestee();
        AbstractComponent b = new PushButtonAC("testButton");
        c.addComponent(b, BorderLayoutConstraint.WEST);
        assertSame(b, c.getComponent(0));
    }

    /**
     * Test method for
     * {@link org.vpda.common.comps.ui.ContainerAC#removeAllComponents()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testRemoveAllComponents() {
        ContainerAC c = createTestee();
        assertEquals(0, c.getComponentsCount());
        AbstractComponent b1 = new PushButtonAC("testButton1");
        c.addComponent(b1, BorderLayoutConstraint.WEST);
        AbstractComponent b2 = new PushButtonAC("testButton2");
        c.addComponent(b2, BorderLayoutConstraint.WEST);
        c.removeAllComponents();
        assertEquals(0, c.getComponentsCount());
    }

    /**
     * Test
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testRemoveComponentViewProviderComponent() {
        ContainerAC c = createTestee();
        AbstractComponent b1 = new PushButtonAC("testButton1");
        c.addComponent(b1, BorderLayoutConstraint.WEST);
        Object o = c.removeComponent(b1.getId());
        assertSame(b1, o);
        assertEquals(0, c.getComponentsCount());
        assertNull(c.getComponent(b1.getId()));
    }

    /**
     * Test method for
     * {@link org.vpda.common.comps.ui.ContainerAC#removeComponent(int)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testRemoveComponentInt() {
        ContainerAC c = createTestee();
        AbstractComponent b1 = new PushButtonAC("testButton1");
        c.addComponent(b1, BorderLayoutConstraint.WEST);
        Object o = c.removeComponent(0);
        assertSame(b1, o);
        assertEquals(0, c.getComponentsCount());
        assertNull(c.getComponent(b1.getId()));
    }

    /**
     * Test
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetComponentLayoutConstraint() {
        ContainerAC c = createTestee();
        AbstractComponent b1 = new PushButtonAC("testButton1");
        c.addComponent(b1, BorderLayoutConstraint.WEST);
        assertEquals(BorderLayoutConstraint.WEST, c.getComponentLayoutConstraint(b1.getId()));

    }

    @Override
    protected Object createTestValue() {
        return new Object();
    }

    @Override
    protected ContainerAC createTestee() {
        return new ContainerAC("test");
    }

}
