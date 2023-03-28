open module org.vpda.common.context {
    requires java.logging;

    requires com.fasterxml.jackson.databind;

    requires transitive org.vpda.common.util;
    requires transitive org.vpda.common.types;

    exports org.vpda.common.context;
    exports org.vpda.common.context.localization;
    exports org.vpda.common.criteria;
    exports org.vpda.common.criteria.page;
    exports org.vpda.common.criteria.sort;

}