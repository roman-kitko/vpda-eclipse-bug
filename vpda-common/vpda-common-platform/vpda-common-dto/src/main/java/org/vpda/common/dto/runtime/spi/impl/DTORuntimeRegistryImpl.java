package org.vpda.common.dto.runtime.spi.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.vpda.common.dto.runtime.DTORuntime;
import org.vpda.common.dto.runtime.spi.DTORuntimeRegistry;
import org.vpda.common.util.exceptions.VPDARuntimeException;

public final class DTORuntimeRegistryImpl implements DTORuntimeRegistry {
    
    private Map<String, DTORuntime> runtimes = new HashMap<>();

    @Override
    public DTORuntime getRuntime(String unitName) {
        return runtimes.get(unitName);
    }
    
    @Override
    public DTORuntime getRequiredRuntime(String unitName) {
        DTORuntime runtime = runtimes.get(unitName);
        if(runtime == null) {
            throw new VPDARuntimeException("No DTO runtime registered for unit : " + unitName);
        }
        return runtime;
    }

    @Override
    public void registerRuntime(DTORuntime runtime) {
        runtimes.putIfAbsent(runtime.getUnitName(), runtime);
    }

    @Override
    public Set<String> getUnitNames() {
        return Collections.unmodifiableSet(runtimes.keySet());
    }

}
