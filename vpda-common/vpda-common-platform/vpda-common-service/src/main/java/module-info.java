open module org.vpda.common.service {
    requires java.logging;
    requires java.management;
    requires java.sql;
    
    requires transitive cache.api;
    requires ehcache;
    requires org.apache.commons.io;
    requires commons.vfs2;
    

    requires transitive org.vpda.common.util;
    requires transitive org.vpda.common.ioc;
    requires transitive org.vpda.common.command;
    requires transitive org.vpda.common.context;
    requires transitive org.vpda.common.processor;
    requires transitive org.vpda.common.entrypoint;
    
    exports org.vpda.common.cache;
    exports org.vpda.common.service;
    exports org.vpda.common.service.localization;
    exports org.vpda.common.service.preferences;
    exports org.vpda.common.service.resources;
    exports org.vpda.common.core.shutdown;
}