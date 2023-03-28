package org.vpda.abstractclient.data.moduleentry;

import java.util.List;
import java.util.logging.Level;

import org.vpda.abstractclient.data.module.AbstractClientDataModule;
import org.vpda.common.entrypoint.AbstractModuleEntryPoint;
import org.vpda.common.entrypoint.AppEntryPoint;
import org.vpda.common.entrypoint.Module;
import org.vpda.common.entrypoint.ModuleEntryPoint;
import org.vpda.common.util.logging.LoggerMethodTracer;
import org.vpda.common.util.logging.MethodTimer;

public final class AbstractClientDataModuleEntryPointImpl extends AbstractModuleEntryPoint implements AbstractClientDataModuleEntryPoint {
    
    private static final LoggerMethodTracer logger = LoggerMethodTracer.getLogger(AbstractClientDataModuleEntryPointImpl.class);

    @Override
    public void entryPoint(AppEntryPoint appEntryPoint, Module module) {
    }

    @Override
    public List<Class<? extends ModuleEntryPoint>> getRequiredModuleEntryPointsClasses(AppEntryPoint appEntryPoint) {
        MethodTimer method = logger.methodEntry(Level.INFO);
        logger.methodExit(method);
        return null;
    }

    @Override
    public <T extends ModuleEntryPoint> Class<T> getDefImplClassForModule(Class<T> moduleIface) {
        return null;
    }

    @Override
    protected Module createModule() {
        return AbstractClientDataModule.getInstance();
    }

}
