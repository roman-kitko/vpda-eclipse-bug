package org.vpda.common.dto.model;

public interface DTOMetaModelRegistry extends DTOMetaModel {
    public <X, M> void registerMetaModelClassForManagedType(ManagedType<X> managedType, Class<M> modelClass);
}
