open module org.vpda.common.command {
    requires java.logging;
    requires java.management;

    requires transitive org.vpda.common.util;
    requires transitive org.vpda.common.types;
    requires transitive org.vpda.common.ioc;

    exports org.vpda.common.command;
    exports org.vpda.common.command.call;
    exports org.vpda.common.command.env;
    exports org.vpda.common.command.executor.impl;
    exports org.vpda.common.command.request;
}