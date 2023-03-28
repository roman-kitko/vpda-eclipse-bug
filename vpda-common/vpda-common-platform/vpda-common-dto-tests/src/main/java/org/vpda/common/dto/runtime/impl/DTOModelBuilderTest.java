/**
 * View provider driven applications - java application framework for developing RIA
 * Copyright (C) 2009-2022 Roman Kitko, Slovakia
 *
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.gnu.org/licenses/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vpda.common.dto.runtime.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.vpda.common.dto.PropertyPath;
import org.vpda.common.dto.annotations.DTOConcrete;
import org.vpda.common.dto.annotations.DTOEmbeddable;
import org.vpda.common.dto.annotations.DTOEntity;
import org.vpda.common.dto.annotations.DTOMappedSuperIdentifiableClass;
import org.vpda.common.dto.model.Attribute;
import org.vpda.common.dto.model.AttributeType;
import org.vpda.common.dto.model.ConcreteManagedType;
import org.vpda.common.dto.model.DTOMetaModel;
import org.vpda.common.dto.model.IdentifiableType;
import org.vpda.common.dto.model.ListAttribute;
import org.vpda.common.dto.model.MapAttribute;
import org.vpda.common.dto.model.MappedSuperIdentifiedType;
import org.vpda.common.dto.model.PluralAttribute.CollectionType;
import org.vpda.common.dto.model.ReferenceType;
import org.vpda.common.dto.model.SetAttribute;
import org.vpda.common.dto.model.SingleAttribute;
import org.vpda.common.dto.model.impl.BasicTypeImpl;
import org.vpda.common.dto.runtime.DTOModelConfiguration;
import org.vpda.common.dto.runtime.DefaultDTOModelConfiguration;
import org.vpda.common.dto.runtime.impl.DTOModelBuilder;
import org.vpda.common.dto.runtime.impl.DTORepositoryImpl;
import org.vpda.common.types.ExternalId;
import org.vpda.common.types.Id;
import org.vpda.common.types.IdentifiedObject;
import org.vpda.common.types.IdentifiedObjectReference;

public class DTOModelBuilderTest {

    private DTOModelConfiguration modelConfiguration = DefaultDTOModelConfiguration.getInstance();

    @DTOConcrete
    static class DtoWithNoFields {
    }

    @DTOConcrete
    static class DtoWithOneField {
        String a;
    }

    @DTOConcrete
    static class DtoWithTwoFields {
        String a;
        long b;
    }

    @DTOEntity
    static class DtoWithId {
        @Id
        Long id;
        @ExternalId
        String externalId;
        BigDecimal salary;
    }

    @DTOEmbeddable
    static class DtoEmbedded {
        String a;
        Long b;
    }

    @DTOMappedSuperIdentifiableClass
    static class SuperClass1 {
        @Id
        Long id;
        @ExternalId
        String externalId;
        String name;
    }

    @DTOConcrete
    static class SuperClass {
        Long a;
        int b;
    }

    @DTOConcrete
    static class ChildClass extends SuperClass {
        String c;
    }

    @DTOConcrete
    static class DtoWithList {
        List<String> c;
    }

    @DTOConcrete
    static class DtoWithSet {
        Set<String> c;
    }

    @DTOConcrete
    static class DtoWithMap {
        Map<String, Long> c;
    }

    @DTOConcrete
    static class DtoA {
        Long a;
    }

    @DTOConcrete
    static class DtoB {
        DtoA relA;
    }

    @DTOConcrete
    static class DtoWithListAssocation {
        List<DtoA> relA;
    }

    @DTOConcrete
    static class DtoWithSetAssocation {
        Set<DtoA> relA;
    }

    @DTOConcrete
    static class DtoWithMapAssocation {
        Map<String, DtoA> relA;
    }

    @DTOEntity
    static class EntA implements IdentifiedObject<Object> {
        @Override
        public Object getId() {
            return null;
        }

        @Override
        public UUID getExternalId() {
            return null;
        }
    }

    @DTOConcrete
    static class SingleReference {
        IdentifiedObjectReference<EntA> refA;
    }

    @DTOConcrete
    static class ListReference {
        List<IdentifiedObjectReference<EntA>> refA;
    }

    @DTOConcrete
    static class SetReference {
        Set<IdentifiedObjectReference<EntA>> refA;
    }

    @DTOConcrete
    static class MapReference {
        Map<String, IdentifiedObjectReference<EntA>> refA;
    }

    enum Color {
        RED, GREEN, BLUE;
    }

    @DTOConcrete
    static class DTOWithEnum {
        Color color;
    }

    @DTOConcrete
    static class DTOWithEnumCollection {
        List<Color> colorsList;
        Set<Color> colorsSet;
        Map<Color, String> colorsMap;
    }

    @Test
    public void testWithNoFields() {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(DtoWithNoFields.class), modelConfiguration).buildModel();
        assertNotNull(model.concreete(DtoWithNoFields.class));
        assertTrue(model.concreete(DtoWithNoFields.class).getAttributes().isEmpty());
    }

    @Test
    public void testWithOneField() throws NoSuchFieldException, SecurityException {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(DtoWithOneField.class), modelConfiguration).buildModel();
        assertNotNull(model.concreete(DtoWithOneField.class));
        Attribute<? super DtoWithOneField, ?> attribute = model.concreete(DtoWithOneField.class).getAttribute("a");
        assertNotNull(attribute);
        assertEquals(AttributeType.SINGLE_BASIC, attribute.getAttributeType());
        assertSame(model.concreete(DtoWithOneField.class), attribute.getDeclaringType());
        assertEquals(DtoWithOneField.class.getDeclaredField("a"), attribute.getJavaMember());
        assertEquals(String.class, attribute.getJavaType());
        assertEquals("a", attribute.getName());
        assertEquals(PropertyPath.createRoot(DtoWithOneField.class.getSimpleName()).createChild("a"), attribute.getPath());
    }

    @Test
    public void testWithTwoFields() throws NoSuchFieldException, SecurityException {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(DtoWithTwoFields.class), modelConfiguration).buildModel();
        assertNotNull(model.concreete(DtoWithTwoFields.class));
        Attribute<? super DtoWithTwoFields, ?> attributeA = model.concreete(DtoWithTwoFields.class).getAttribute("a");
        assertNotNull(attributeA);
        assertEquals(AttributeType.SINGLE_BASIC, attributeA.getAttributeType());
        assertSame(model.concreete(DtoWithTwoFields.class), attributeA.getDeclaringType());
        assertEquals(DtoWithTwoFields.class.getDeclaredField("a"), attributeA.getJavaMember());
        assertEquals(String.class, attributeA.getJavaType());
        assertEquals("a", attributeA.getName());
        assertEquals(PropertyPath.createRoot(DtoWithTwoFields.class.getSimpleName()).createChild("a"), attributeA.getPath());

        Attribute<? super DtoWithTwoFields, Long> attributeB = model.concreete(DtoWithTwoFields.class).getSingleAttribute("b", long.class);
        assertNotNull(attributeB);
        assertSame(model.concreete(DtoWithTwoFields.class), attributeB.getDeclaringType());
        assertEquals(long.class, attributeB.getJavaType());
    }

    @Test
    public void testWithId() throws NoSuchFieldException, SecurityException {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(DtoWithId.class), modelConfiguration).buildModel();
        Attribute<DtoWithId, ?> idAttribute = model.entity(DtoWithId.class).getIdAttribute();
        assertNotNull(idAttribute);
        Attribute<DtoWithId, ?> externalIdAttribute = model.entity(DtoWithId.class).getExternalIdAttribute();
        assertNotNull(externalIdAttribute);
    }

    @Test
    public void testEmbedded() throws NoSuchFieldException, SecurityException {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(DtoEmbedded.class), modelConfiguration).buildModel();
        assertNotNull(model.embeddable(DtoEmbedded.class).getAttribute("a"));
        assertNotNull(model.embeddable(DtoEmbedded.class).getAttribute("b"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAll() {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(DtoWithNoFields.class, DtoWithOneField.class, DtoWithTwoFields.class, DtoWithId.class, DtoEmbedded.class), modelConfiguration)
                .buildModel();
        assertNotNull(model.managedType(DtoWithNoFields.class));
        ConcreteManagedType<DtoWithNoFields> entity1 = model.managedType(DtoWithNoFields.class, ConcreteManagedType.class);
        assertNotNull(entity1);
        assertNotNull(model.managedType(DtoWithOneField.class));
        assertNotNull(model.managedType(DtoWithTwoFields.class));
        assertNotNull(model.managedType(DtoWithId.class));
        assertNotNull(model.managedType(DtoEmbedded.class));
    }

    @Test
    public void testSuperClass() throws SecurityException {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(SuperClass1.class), modelConfiguration).buildModel();
        MappedSuperIdentifiedType<SuperClass1> mappedSuperIdentifiedType = model.mappedSuperIdentifiedType(SuperClass1.class);
        assertNotNull(mappedSuperIdentifiedType);
        assertNotNull(mappedSuperIdentifiedType.getIdAttribute());
        assertNotNull(mappedSuperIdentifiedType.getExternalIdAttribute());
        assertNotNull(mappedSuperIdentifiedType.getAttribute("name"));
    }

    @Test
    public void testHierarchy() {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(SuperClass.class, ChildClass.class), modelConfiguration).buildModel();
        ConcreteManagedType<ChildClass> concreete = model.concreete(ChildClass.class);
        assertNotNull(concreete.getAttribute("c"));
        assertNotNull(concreete.getAttribute("a"));
        assertNotNull(concreete.getAttribute("b"));
        assertNull(concreete.getDeclaredAttribute("a"));
        assertNull(concreete.getDeclaredAttribute("b"));
    }

    @Test
    public void testBasicList() {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(DtoWithList.class), modelConfiguration).buildModel();
        ConcreteManagedType<DtoWithList> concreete = model.concreete(DtoWithList.class);
        ListAttribute<? super DtoWithList, String> attribute = concreete.getListAttribute("c", String.class);
        assertNotNull(attribute);
        assertEquals(AttributeType.COLLECTION_BASIC, attribute.getAttributeType());
        assertEquals(CollectionType.LIST, attribute.getCollectionType());
        assertEquals(new BasicTypeImpl<>(String.class), attribute.getElementType());
    }

    @Test
    public void testBasicSet() {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(DtoWithSet.class), modelConfiguration).buildModel();
        ConcreteManagedType<DtoWithSet> concreete = model.concreete(DtoWithSet.class);
        SetAttribute<? super DtoWithSet, String> attribute = concreete.getSetAttribute("c", String.class);
        assertNotNull(attribute);
        assertEquals(AttributeType.COLLECTION_BASIC, attribute.getAttributeType());
        assertEquals(CollectionType.SET, attribute.getCollectionType());
        assertEquals(new BasicTypeImpl<>(String.class), attribute.getElementType());
    }

    @Test
    public void testBasicMap() {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(DtoWithMap.class), modelConfiguration).buildModel();
        ConcreteManagedType<DtoWithMap> concreete = model.concreete(DtoWithMap.class);
        MapAttribute<? super DtoWithMap, String, Long> attribute = concreete.getMapAttribute("c", String.class, Long.class);
        assertNotNull(attribute);
        assertEquals(AttributeType.COLLECTION_BASIC, attribute.getAttributeType());
        assertEquals(CollectionType.MAP, attribute.getCollectionType());
        assertEquals(new BasicTypeImpl<>(Long.class), attribute.getElementType());
        assertEquals(new BasicTypeImpl<>(String.class), attribute.getKeyType());
        assertEquals(String.class, attribute.getKeyJavaType());
        assertEquals(Long.class, attribute.getValueJavaType());
    }

    @Test
    public void testSingleAssociation() {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(DtoA.class, DtoB.class), modelConfiguration).buildModel();
        ConcreteManagedType<DtoB> concreete = model.concreete(DtoB.class);
        assertNotNull(concreete);
        Attribute<? super DtoB, DtoA> attribute = concreete.getSingleAttribute("relA", DtoA.class);
        assertNotNull(attribute);
        assertEquals(AttributeType.SINGLE_ASSOCIATION, attribute.getAttributeType());
    }

    @Test
    public void testListAssociation() {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(DtoA.class, DtoWithListAssocation.class), modelConfiguration).buildModel();
        ConcreteManagedType<DtoWithListAssocation> concreete = model.concreete(DtoWithListAssocation.class);
        assertNotNull(concreete);
        ListAttribute<? super DtoWithListAssocation, DtoA> attribute = concreete.getListAttribute("relA", DtoA.class);
        assertNotNull(attribute);
        assertEquals(AttributeType.COLLECTION_ASSOCIATION, attribute.getAttributeType());
        assertEquals(CollectionType.LIST, attribute.getCollectionType());
        assertEquals(model.concreete(DtoA.class), attribute.getElementType());
        assertNotNull(attribute.getJavaMember());
    }

    @Test
    public void testSetAssociation() {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(DtoA.class, DtoWithSetAssocation.class), modelConfiguration).buildModel();
        ConcreteManagedType<DtoWithSetAssocation> concreete = model.concreete(DtoWithSetAssocation.class);
        assertNotNull(concreete);
        SetAttribute<? super DtoWithSetAssocation, DtoA> attribute = concreete.getSetAttribute("relA", DtoA.class);
        assertNotNull(attribute);
        assertEquals(AttributeType.COLLECTION_ASSOCIATION, attribute.getAttributeType());
        assertEquals(CollectionType.SET, attribute.getCollectionType());
        assertEquals(model.concreete(DtoA.class), attribute.getElementType());
        assertNotNull(attribute.getJavaMember());
    }

    @Test
    public void testMapAssociation() {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(DtoA.class, DtoWithMapAssocation.class), modelConfiguration).buildModel();
        ConcreteManagedType<DtoWithMapAssocation> concreete = model.concreete(DtoWithMapAssocation.class);
        assertNotNull(concreete);
        MapAttribute<? super DtoWithMapAssocation, String, DtoA> attribute = concreete.getMapAttribute("relA", String.class, DtoA.class);
        assertNotNull(attribute);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSingleReference() {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(EntA.class, SingleReference.class), modelConfiguration).buildModel();
        ConcreteManagedType<SingleReference> concreete = model.concreete(SingleReference.class);
        assertNotNull(concreete);
        SingleAttribute<? super SingleReference, IdentifiedObjectReference<EntA>> singleAttribute = (SingleAttribute) concreete.getSingleAttribute("refA", IdentifiedObjectReference.class);
        assertEquals(AttributeType.SINGLE_REFERENCE, singleAttribute.getAttributeType());
        ReferenceType<EntA, IdentifiedObjectReference<EntA>> refType = (ReferenceType) singleAttribute.getType();
        assertNotNull(refType);
        IdentifiableType<EntA> refereceToType = refType.getRefereceToType();
        assertNotNull(refereceToType);
        assertEquals(refereceToType, model.entity(EntA.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testListReference() {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(EntA.class, ListReference.class), modelConfiguration).buildModel();
        ConcreteManagedType<ListReference> concreete = model.concreete(ListReference.class);
        assertNotNull(concreete);
        ListAttribute<? super ListReference, IdentifiedObjectReference<EntA>> listAttribute = (ListAttribute) concreete.getListAttribute("refA", IdentifiedObjectReference.class);
        assertEquals(AttributeType.COLLECTION_REFERENCE, listAttribute.getAttributeType());
        ReferenceType<EntA, IdentifiedObjectReference<EntA>> refType = (ReferenceType) listAttribute.getElementType();
        assertNotNull(refType);
        IdentifiableType<EntA> refereceToType = refType.getRefereceToType();
        assertNotNull(refereceToType);
        assertEquals(refereceToType, model.entity(EntA.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSetReference() {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(EntA.class, SetReference.class), modelConfiguration).buildModel();
        ConcreteManagedType<SetReference> concreete = model.concreete(SetReference.class);
        assertNotNull(concreete);
        SetAttribute<? super SetReference, IdentifiedObjectReference<EntA>> setAttribute = (SetAttribute) concreete.getSetAttribute("refA", IdentifiedObjectReference.class);
        assertEquals(AttributeType.COLLECTION_REFERENCE, setAttribute.getAttributeType());
        ReferenceType<EntA, IdentifiedObjectReference<EntA>> refType = (ReferenceType) setAttribute.getElementType();
        assertNotNull(refType);
        IdentifiableType<EntA> refereceToType = refType.getRefereceToType();
        assertNotNull(refereceToType);
        assertEquals(refereceToType, model.entity(EntA.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testMapReference() {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(EntA.class, MapReference.class), modelConfiguration).buildModel();
        ConcreteManagedType<MapReference> concreete = model.concreete(MapReference.class);
        assertNotNull(concreete);
        MapAttribute<? super MapReference, String, IdentifiedObjectReference<EntA>> mapAttribute = (MapAttribute) concreete.getMapAttribute("refA", String.class, IdentifiedObjectReference.class);
        assertEquals(AttributeType.COLLECTION_REFERENCE, mapAttribute.getAttributeType());
        ReferenceType<EntA, IdentifiedObjectReference<EntA>> refType = (ReferenceType) mapAttribute.getElementType();
        assertNotNull(refType);
        IdentifiableType<EntA> refereceToType = refType.getRefereceToType();
        assertNotNull(refereceToType);
        assertEquals(refereceToType, model.entity(EntA.class));
        assertEquals(String.class, mapAttribute.getKeyJavaType());
    }

    @Test
    public void testWithBasicEnum() throws NoSuchFieldException, SecurityException {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(DTOWithEnum.class), modelConfiguration).buildModel();
        assertNotNull(model.concreete(DTOWithEnum.class));
        Attribute<? super DTOWithEnum, ?> attribute = model.concreete(DTOWithEnum.class).getAttribute("color");
        assertNotNull(attribute);
        assertEquals(AttributeType.SINGLE_BASIC, attribute.getAttributeType());
        assertSame(model.concreete(DTOWithEnum.class), attribute.getDeclaringType());
        assertEquals(Color.class, attribute.getJavaType());
    }

    @Test
    public void testWithCollectionEnum() throws NoSuchFieldException, SecurityException {
        DTOMetaModel model = new DTOModelBuilder(new DTORepositoryImpl(DTOWithEnumCollection.class), modelConfiguration).buildModel();
        assertNotNull(model.concreete(DTOWithEnumCollection.class));
        ListAttribute<? super DTOWithEnumCollection, Color> listAttribute = model.concreete(DTOWithEnumCollection.class).getListAttribute("colorsList", Color.class);
        assertEquals(Color.class, listAttribute.getJavaElementType());

        SetAttribute<? super DTOWithEnumCollection, Color> setAttribute = model.concreete(DTOWithEnumCollection.class).getSetAttribute("colorsSet", Color.class);
        assertEquals(Color.class, setAttribute.getJavaElementType());

        MapAttribute<? super DTOWithEnumCollection, Color, String> mapAttribute = model.concreete(DTOWithEnumCollection.class).getMapAttribute("colorsMap", Color.class, String.class);
        assertEquals(Color.class, mapAttribute.getKeyJavaType());
        assertEquals(String.class, mapAttribute.getValueJavaType());

    }

}
