package org.vpda.abstractclient.data.module;

import org.vpda.abstractclient.core.module.AbstractClientNamespace;
import org.vpda.common.entrypoint.AbstractModule;
import org.vpda.common.entrypoint.BasicModuleKind;
import org.vpda.common.entrypoint.Module;
import org.vpda.common.entrypoint.ModuleKind;
import org.vpda.common.entrypoint.ModuleNamespace;

public class AbstractClientDataModule extends AbstractModule {
    private static final long serialVersionUID = -8585670281930774216L;
    /** Name of module */
    public final static String MODULE_NAME = "AbstractClientData";

    /**
     * Constructor for module
     */
    private AbstractClientDataModule() {
    }

    @Override
    protected void initName() {
        name = MODULE_NAME;
    }

    /**
     * 
     * @return module instance
     */
    public static Module getInstance() {
        return AbstractModule.getModule(AbstractClientDataModule.class);
    }

    @Override
    public ModuleKind getKind() {
        return BasicModuleKind.COMMON;
    }

    @Override
    public ModuleNamespace getModuleNamespace() {
        return AbstractClientNamespace.ABSTRACT_CLIENT;
    }
}
