open module org.vpda.common.abstractclient.data {
    requires java.logging;
    requires java.sql;

    requires transitive org.vpda.common.util;
    requires transitive org.vpda.common.core;
    requires transitive org.vpda.common.abstractclient.core;

    exports org.vpda.abstractclient.data.module;
    exports org.vpda.abstractclient.data.moduleentry;

}