package org.vpda.clientserver.viewprovider;

import org.vpda.common.dto.annotations.StaticModelClass;
import org.vpda.common.dto.model.SingleAttribute;

@StaticModelClass(ViewProviderDef.class)
public class ViewProviderDef_ {
  public static volatile SingleAttribute<ViewProviderDef, String> spiClassName;

  public static volatile SingleAttribute<ViewProviderDef, String> code;

  public static volatile SingleAttribute<ViewProviderDef, String> implClassName;

  public static volatile SingleAttribute<ViewProviderDef, ViewProviderKind> kind;

  public static volatile SingleAttribute<ViewProviderDef, String> spiFactoryClassName;

  public static volatile SingleAttribute<ViewProviderDef, String> factoryClassName;

  public static volatile SingleAttribute<ViewProviderDef, String> definitionID;
}
