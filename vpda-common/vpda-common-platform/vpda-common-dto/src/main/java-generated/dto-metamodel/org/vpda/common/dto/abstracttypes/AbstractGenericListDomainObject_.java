package org.vpda.common.dto.abstracttypes;

import org.vpda.common.dto.annotations.StaticModelClass;
import org.vpda.common.dto.model.SingleAttribute;

@StaticModelClass(AbstractGenericListDomainObject.class)
public class AbstractGenericListDomainObject_ extends AbstractDomainObject_ {
  public static volatile SingleAttribute<AbstractGenericListDomainObject, String> name;

  public static volatile SingleAttribute<AbstractGenericListDomainObject, String> description;

  public static volatile SingleAttribute<AbstractGenericListDomainObject, String> code;
}
