open module org.vpda.common.dto {
    requires java.logging;
    requires java.management;
    requires java.compiler;
    requires java.sql;
    requires java.xml;

    requires info.picocli;
    requires com.squareup.javapoet;

    requires transitive org.vpda.common.types;
    requires transitive org.vpda.common.util;

    exports org.vpda.common.dto;
    exports org.vpda.common.dto.abstracttypes;
    exports org.vpda.common.dto.annotations;
    exports org.vpda.common.dto.factory;
    exports org.vpda.common.dto.model;
    exports org.vpda.common.dto.model.gen;
    exports org.vpda.common.dto.model.gen.impl;
    exports org.vpda.common.dto.model.impl;
    exports org.vpda.common.dto.runtime;
    exports org.vpda.common.dto.runtime.impl;
    exports org.vpda.common.dto.runtime.spi;
    exports org.vpda.common.dto.runtime.spi.impl;

    uses org.vpda.common.dto.runtime.spi.DTORuntimeProvider;

    provides org.vpda.common.dto.runtime.spi.DTORuntimeProvider with org.vpda.common.dto.runtime.spi.impl.DTORuntimeProviderImpl;
}