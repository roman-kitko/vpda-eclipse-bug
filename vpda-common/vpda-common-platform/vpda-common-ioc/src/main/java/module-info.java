open module org.vpda.common.ioc {

    requires java.xml;

    requires transitive picocontainer;
    requires transitive picocontainer.script.core;

    requires org.vpda.common.util;

    exports org.vpda.common.ioc.objectresolver;
    exports org.vpda.common.ioc.picocontainer;
}