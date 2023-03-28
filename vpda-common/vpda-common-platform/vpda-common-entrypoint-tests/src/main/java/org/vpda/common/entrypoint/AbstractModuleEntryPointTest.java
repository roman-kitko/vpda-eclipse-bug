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
package org.vpda.common.entrypoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.picocontainer.DefaultPicoContainer;
import org.vpda.common.entrypoint.AbstractAppLauncher;
import org.vpda.common.entrypoint.AbstractModule;
import org.vpda.common.entrypoint.AbstractModuleEntryPoint;
import org.vpda.common.entrypoint.AbstractModulesConfigurationtImpl;
import org.vpda.common.entrypoint.AppConfiguration;
import org.vpda.common.entrypoint.AppConfigurationImpl;
import org.vpda.common.entrypoint.AppEntryPoint;
import org.vpda.common.entrypoint.BasicModuleKind;
import org.vpda.common.entrypoint.BasicModuleNamespace;
import org.vpda.common.entrypoint.EntryPointListener;
import org.vpda.common.entrypoint.EntryPointListenerAdapter;
import org.vpda.common.entrypoint.ModuleEntryPoint;
import org.vpda.common.entrypoint.ModuleKind;
import org.vpda.common.entrypoint.ModuleNamespace;
import org.vpda.common.entrypoint.ModulesConfiguration;
import org.vpda.common.entrypoint.ModulesConfigurationFactory;
import org.vpda.internal.common.util.Holder;

/** */
public class AbstractModuleEntryPointTest {

    /**
     * Test
     * 
     * @throws Exception
     */
    @Test
    public void testGetSubmodulesEntryPointClasses() throws Exception {
        createAndRunAppEntryPoint();
    }

    private AppEntryPoint createAndRunAppEntryPoint() throws Exception {
        DefaultPicoContainer parentContainer = new DefaultPicoContainer();
        parentContainer.addComponent(ModulesConfigurationFactory.class, new MyModulesConfigurationFactory());
        final Holder<Boolean> wasRunHolder = new Holder<>();
        EntryPointListener listener = new EntryPointListenerAdapter() {
            @Override
            public void moduleEntryStart(AppEntryPoint appEntryPoint, Class<? extends ModuleEntryPoint> moduleEntryClass, ModuleEntryPoint moduleEntryPoint, Module module) {
                if (ModuleEntryPoint1.class.equals(moduleEntryClass)) {
                    wasRunHolder.setValue(true);
                    ModuleEntryPoint1Impl moduleEntryPoint1Impl = (ModuleEntryPoint1Impl) moduleEntryPoint;
                    List<Class<? extends ModuleEntryPoint>> submodulesEntryPointClasses = moduleEntryPoint1Impl.getSubmodulesEntryPointClasses();
                    assertEquals(2, submodulesEntryPointClasses.size());
                    ModuleEntryPoint submoduleEntryPoint1 = moduleEntryPoint1Impl.createChildModuleEntryPoint(appEntryPoint, SubModuleEntryPoint1.class);
                    ModuleEntryPoint submoduleEntryPoint2 = moduleEntryPoint1Impl.createChildModuleEntryPoint(appEntryPoint, SubModuleEntryPoint2.class);
                    assertNotNull(submoduleEntryPoint1);
                    assertNotNull(submoduleEntryPoint2);
                    assertNotSame(submoduleEntryPoint1, submoduleEntryPoint2);

                    assertEquals(SubModule1.class, submoduleEntryPoint1.getModule().getClass());
                    assertEquals(SubModule2.class, submoduleEntryPoint2.getModule().getClass());

                    assertSame(moduleEntryPoint1Impl.getModule(), submoduleEntryPoint1.getModule().getParent());
                    assertSame(moduleEntryPoint1Impl.getModule(), submoduleEntryPoint2.getModule().getParent());

                    assertEquals(2, moduleEntryPoint1Impl.getModule().getSubModules().size());
                    assertSame(moduleEntryPoint1Impl.getModule().getSubModules().get(0), submoduleEntryPoint1.getModule());
                    assertSame(moduleEntryPoint1Impl.getModule().getSubModules().get(1), submoduleEntryPoint2.getModule());

                    moduleEntryPoint1Impl.runModule(appEntryPoint, SubModuleEntryPoint1.class, submoduleEntryPoint1);
                    assertTrue(((SubModuleEntryPoint1Impl) submoduleEntryPoint1).wasRun);
                    moduleEntryPoint1Impl.runModule(appEntryPoint, SubModuleEntryPoint2.class, submoduleEntryPoint2);
                    assertTrue(((SubModuleEntryPoint2Impl) submoduleEntryPoint2).wasRun);

                    List<ModuleEntryPoint> runSubModules = moduleEntryPoint1Impl.runAllSubmodules(appEntryPoint);
                    assertEquals(2, runSubModules.size());
                    assertEquals(SubModuleEntryPoint1Impl.class, runSubModules.get(0).getClass());
                    assertEquals(SubModuleEntryPoint2Impl.class, runSubModules.get(1).getClass());
                }
            }

        };
        AppEntryPoint entryPoint = AbstractAppLauncher.defaultLaunch(new AppConfigurationImpl(parentContainer), Collections.singletonList(listener));
        assertEquals(Boolean.TRUE, wasRunHolder.getValue());
        return entryPoint;
    }

    /** Module 1 */
    public static final class Module1 extends AbstractModule {
        /**
         * 
         */
        private static final long serialVersionUID = -1612150243255288798L;

        @Override
        protected void initName() {
            name = "Module1";
        }

        /**
         * 
         * @return module instance
         */
        public static Module getInstance() {
            return AbstractModule.getModule(Module1.class);
        }

        @Override
        public ModuleKind getKind() {
            return BasicModuleKind.COMMON;
        }

        @Override
        public ModuleNamespace getModuleNamespace() {
            return BasicModuleNamespace.COMMON;
        }

    }

    /** Module 1 */
    public static final class SubModule1 extends AbstractModule {
        /**
         * 
         */
        private static final long serialVersionUID = -8182706094817362934L;

        @Override
        protected void initName() {
            name = "SubModule1";
        }

        /**
         * 
         * @return module instance
         */
        public static Module getInstance() {
            return AbstractModule.getModule(SubModule1.class);
        }

        @Override
        public boolean isSubModule() {
            return true;
        }

        @Override
        public ModuleKind getKind() {
            return BasicModuleKind.COMMON;
        }

        @Override
        public ModuleNamespace getModuleNamespace() {
            return BasicModuleNamespace.COMMON;
        }

    }

    /** Module 1 */
    public static final class SubModule2 extends AbstractModule {
        /**
         * 
         */
        private static final long serialVersionUID = 4609305238060599276L;

        @Override
        protected void initName() {
            name = "SubModule2";
        }

        /**
         * 
         * @return module instance
         */
        public static Module getInstance() {
            return AbstractModule.getModule(SubModule2.class);
        }

        @Override
        public boolean isSubModule() {
            return true;
        }

        @Override
        public ModuleKind getKind() {
            return BasicModuleKind.COMMON;
        }

        @Override
        public ModuleNamespace getModuleNamespace() {
            return BasicModuleNamespace.COMMON;
        }

    }

    /** */
    public static interface ModuleEntryPoint1 extends ModuleEntryPoint {
    }

    /** */
    public static interface SubModuleEntryPoint1 extends ModuleEntryPoint {
    }

    /** */
    public static interface SubModuleEntryPoint2 extends ModuleEntryPoint {
    }

    /** Module1 entry point */
    public static final class ModuleEntryPoint1Impl extends AbstractModuleEntryPoint implements ModuleEntryPoint1 {
        boolean wasRun;

        @Override
        public void entryPoint(AppEntryPoint appEntryPoint, Module module) {
            wasRun = true;
        }

        @Override
        public List<Class<? extends ModuleEntryPoint>> getRequiredModuleEntryPointsClasses(AppEntryPoint appEntryPoint) {
            return null;
        }

        @Override
        public <T extends ModuleEntryPoint> Class<T> getDefImplClassForModule(Class<T> moduleIface) {
            return null;
        }

        @Override
        protected Module createModule() {
            return Module1.getInstance();
        }

    }

    /** Sub Module1 entry point */
    public static final class SubModuleEntryPoint1Impl extends AbstractModuleEntryPoint implements SubModuleEntryPoint1 {
        boolean wasRun;

        @Override
        public void entryPoint(AppEntryPoint appEntryPoint, Module module) {
            wasRun = true;
        }

        @Override
        public List<Class<? extends ModuleEntryPoint>> getRequiredModuleEntryPointsClasses(AppEntryPoint appEntryPoint) {
            return null;
        }

        @Override
        public <T extends ModuleEntryPoint> Class<T> getDefImplClassForModule(Class<T> moduleIface) {
            return null;
        }

        @Override
        protected Module createModule() {
            return SubModule1.getInstance();
        }

    }

    /** Sub Module2 entry point */
    public static final class SubModuleEntryPoint2Impl extends AbstractModuleEntryPoint implements SubModuleEntryPoint2 {
        boolean wasRun;

        @Override
        public void entryPoint(AppEntryPoint appEntryPoint, Module module) {
            super.runAllSubmodules(appEntryPoint);
            wasRun = true;
        }

        @Override
        public List<Class<? extends ModuleEntryPoint>> getRequiredModuleEntryPointsClasses(AppEntryPoint appEntryPoint) {
            return null;
        }

        @Override
        public <T extends ModuleEntryPoint> Class<T> getDefImplClassForModule(Class<T> moduleIface) {
            return null;
        }

        @Override
        protected Module createModule() {
            return SubModule2.getInstance();
        }

    }

    /** */
    public static final class MyModulesConfigurationFactory implements ModulesConfigurationFactory {

        @Override
        public ModulesConfiguration createModulesConfiguration(AppConfiguration appConf) {
            List<Class<? extends ModuleEntryPoint>> modules = new ArrayList<>();
            modules.add(ModuleEntryPoint1.class);
            return new MyModulesConfiguration(appConf, modules);
        }
    }

    static final class MyModulesConfiguration extends AbstractModulesConfigurationtImpl implements ModulesConfiguration {

        /**
         * 
         */
        private static final long serialVersionUID = 6927623032149859077L;

        MyModulesConfiguration(AppConfiguration appConf, List<Class<? extends ModuleEntryPoint>> modules) {
            super(appConf, modules);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends ModuleEntryPoint> T createModuleEntryPoint(Class<T> clazz) {
            if (ModuleEntryPoint1.class.equals(clazz)) {
                return (T) new ModuleEntryPoint1Impl();
            }
            else if (SubModuleEntryPoint1.class.equals(clazz)) {
                return (T) new SubModuleEntryPoint1Impl();
            }
            else if (SubModuleEntryPoint2.class.equals(clazz)) {
                return (T) new SubModuleEntryPoint2Impl();
            }
            return super.createModuleEntryPoint(clazz);
        }

        @Override
        public List<Class<? extends ModuleEntryPoint>> getMappingsForInterfaceModule(Class<? extends ModuleEntryPoint> clazz) {
            return Collections.emptyList();
        }

    }

}
