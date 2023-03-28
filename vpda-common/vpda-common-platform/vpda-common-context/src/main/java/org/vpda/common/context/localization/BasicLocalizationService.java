package org.vpda.common.context.localization;

import org.vpda.common.context.TenementalContext;

public interface BasicLocalizationService {
    /**
     * Localize simple String, no resolve is performed, This is only helper method
     * that can be used by LocDataBuilders
     * 
     * @param locKey
     * @param context
     * @return localized String
     */
    public String localizeString(LocKey locKey, TenementalContext context);

    /**
     * Localize simple String, no resolve is performed, This is only helper method
     * that can be used by LocDataBuilders
     * 
     * @param locKey
     * @param context
     * @param defaultValue
     * @return localized String
     */
    public default String localizeString(LocKey locKey, TenementalContext context, String defaultValue) {
        String value = localizeString(locKey, context);
        return value != null ? value : defaultValue;
    }
}
