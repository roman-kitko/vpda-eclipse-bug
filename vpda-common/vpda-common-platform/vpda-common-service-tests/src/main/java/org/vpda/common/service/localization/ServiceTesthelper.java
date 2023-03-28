package org.vpda.common.service.localization;

import org.vpda.common.context.ApplContext;
import org.vpda.common.context.ApplContextBuilder;
import org.vpda.common.context.TenementalContext;

public class ServiceTesthelper {
    
    private ServiceTesthelper() {}
    public static TenementalContext createTestContext() {
        ApplContext appCtx = new ApplContextBuilder().setId(0L).build();
        return TenementalContext.create(appCtx);
    }
}
