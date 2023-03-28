module org.vpda.common.processor {

    requires transitive org.vpda.common.util;
    requires transitive org.vpda.common.ioc;

    exports org.vpda.common.processor;
    exports org.vpda.common.processor.annotation;
    exports org.vpda.common.processor.annotation.eval;
    exports org.vpda.common.processor.ctx;
    exports org.vpda.common.processor.impl;

}