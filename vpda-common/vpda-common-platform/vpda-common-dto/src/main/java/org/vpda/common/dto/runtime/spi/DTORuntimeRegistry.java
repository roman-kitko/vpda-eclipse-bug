package org.vpda.common.dto.runtime.spi;

import java.util.Set;

import org.vpda.common.dto.runtime.DTORuntime;

public interface DTORuntimeRegistry {
    
    public DTORuntime getRuntime(String unitName);
    
    public DTORuntime getRequiredRuntime(String unitName);
    
    public void registerRuntime(DTORuntime runtime);
    
    public Set<String> getUnitNames(); 

}
