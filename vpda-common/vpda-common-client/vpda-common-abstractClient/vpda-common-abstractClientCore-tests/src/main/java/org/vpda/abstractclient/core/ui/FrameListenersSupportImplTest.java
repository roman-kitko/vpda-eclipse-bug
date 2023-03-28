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
package org.vpda.abstractclient.core.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.vpda.abstractclient.core.ui.dummy.DummyFrame;

/**
 * @author kitko
 *
 */
public class FrameListenersSupportImplTest {

    /**
     * Test method for
     * {@link org.vpda.abstractclient.core.ui.FrameListenersSupportImpl#addFrameListener(org.vpda.abstractclient.core.ui.FrameListener)}.
     */
    @Test
    public void testAddFrameListener() {
        FrameListenersSupport testee = createTestee();
        FrameListener listener = createTestListener();
        testee.addFrameListener(listener);
        assertTrue(testee.getFrameListeners().contains(listener));
    }

    /**
     * Test method for
     * {@link org.vpda.abstractclient.core.ui.FrameListenersSupportImpl#getFrameListeners()}.
     */
    @Test
    public void testListFrameListeners() {
        FrameListenersSupport testee = createTestee();
        assertTrue(testee.getFrameListeners().isEmpty());
    }

    /**
     * Test method for
     * {@link org.vpda.abstractclient.core.ui.FrameListenersSupportImpl#removeFrameListener(org.vpda.abstractclient.core.ui.FrameListener)}.
     */
    @Test
    public void testRemoveFrameListener() {
        FrameListenersSupport testee = createTestee();
        FrameListener listener = createTestListener();
        testee.addFrameListener(listener);
        assertTrue(testee.getFrameListeners().contains(listener));
        testee.removeFrameListener(listener);
        assertTrue(testee.getFrameListeners().isEmpty());

    }

    /**
     * Test
     */
    @Test
    public void testFireFrameEvent() {
        FrameListenersSupportWithFire testee = createTestee();
        TestFrameListener listener = createTestListener();
        testee.addFrameListener(listener);
        testee.fireAfterDisposed();
    }

    private FrameListenersSupportWithFire createTestee() {
        return new FrameListenersSupportImpl(new DummyFrame());
    }

    private static final class TestFrameListener extends FrameListenerAdaper {
        private static final long serialVersionUID = -2915851930032385247L;
    }

    private TestFrameListener createTestListener() {
        return new TestFrameListener();
    }

}
