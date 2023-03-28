package org.vpda.common.dto.abstracttypes;

import java.util.UUID;
import org.vpda.common.dto.annotations.StaticModelClass;
import org.vpda.common.dto.model.SingleAttribute;

@StaticModelClass(AbstractPersistentDTO.class)
public class AbstractPersistentDTO_ {
  public static volatile SingleAttribute<AbstractPersistentDTO, Long> createdAt;

  public static volatile SingleAttribute<AbstractPersistentDTO, UUID> externalId;

  public static volatile SingleAttribute<AbstractPersistentDTO, Object> id;

  public static volatile SingleAttribute<AbstractPersistentDTO, Long> lastUpdatedAt;
}
