/*
 * Copyright 2016-present Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onosproject.yangutils.translator.tojava;

import org.onosproject.yangutils.datamodel.YangType;
import org.onosproject.yangutils.translator.exception.TranslatorException;

import static org.onosproject.yangutils.translator.tojava.utils.JavaFileGeneratorUtils.isTypeLeafref;
import static org.onosproject.yangutils.translator.tojava.utils.JavaFileGeneratorUtils.isTypeNameLeafref;

/**
 * Represents the attribute info corresponding to class/interface generated.
 */
public final class JavaAttributeInfo {

    /**
     * The data type info of attribute.
     */
    private YangType<?> attrType;

    /**
     * Name of the attribute.
     */
    private String name;

    /**
     * If the added attribute is a list of info.
     */
    private boolean isListAttr;

    /**
     * If the added attribute has to be accessed in a fully qualified manner.
     */
    private boolean isQualifiedName;

    /**
     * The class info will be used to set the attribute type and package info
     * will be use for qualified name.
     */
    private JavaQualifiedTypeInfoTranslator importInfo;

    /**
     * If conflict occurs.
     */
    private boolean isIntConflict;

    /**
     * If conflict occurs.
     */
    private boolean isLongConflict;

    /**
     * Creates a java attribute info object.
     */
    private JavaAttributeInfo() {
    }

    /**
     * Creates object of java attribute info.
     *
     * @param attrType        YANG type
     * @param name            attribute name
     * @param isListAttr      is list attribute
     * @param isQualifiedName is qualified name
     */
    public JavaAttributeInfo(YangType<?> attrType, String name, boolean isListAttr, boolean isQualifiedName) {
        this.attrType = attrType;
        this.name = name;
        this.isListAttr = isListAttr;
        this.isQualifiedName = isQualifiedName;
    }

    /**
     * Returns the data type info of attribute.
     *
     * @return the data type info of attribute
     */
    public YangType<?> getAttributeType() {
        return attrType;
    }

    /**
     * Sets the data type info of attribute.
     *
     * @param type the data type info of attribute
     */
    public void setAttributeType(YangType<?> type) {
        attrType = type;
    }

    /**
     * Returns name of the attribute.
     *
     * @return name of the attribute
     */
    public String getAttributeName() {

        if (name == null) {
            throw new TranslatorException("Expected java attribute name is null");
        }
        return name;
    }

    /**
     * Sets name of the attribute.
     *
     * @param attrName name of the attribute
     */
    public void setAttributeName(String attrName) {
        name = attrName;
    }

    /**
     * Returns if the added attribute is a list of info.
     *
     * @return the if the added attribute is a list of info
     */
    public boolean isListAttr() {
        return isListAttr;
    }

    /**
     * Sets if the added attribute is a list of info.
     *
     * @param isList if the added attribute is a list of info
     */
    private void setListAttr(boolean isList) {
        isListAttr = isList;
    }

    /**
     * Returns if the added attribute has to be accessed in a fully qualified
     * manner.
     *
     * @return the if the added attribute has to be accessed in a fully
     * qualified manner.
     */
    public boolean isQualifiedName() {
        return isQualifiedName;
    }

    /**
     * Sets if the added attribute has to be accessed in a fully qualified
     * manner.
     *
     * @param isQualified if the added attribute has to be accessed in a fully
     *                    qualified manner
     */
    private void setIsQualifiedAccess(boolean isQualified) {
        isQualifiedName = isQualified;
    }

    /**
     * Returns the import info for the attribute type. It will be null, if the type
     * is basic built-in java type.
     *
     * @return import info
     */
    public JavaQualifiedTypeInfoTranslator getImportInfo() {
        return importInfo;
    }

    /**
     * Sets the import info for the attribute type.
     *
     * @param importInfo import info for the attribute type
     */
    public void setImportInfo(JavaQualifiedTypeInfoTranslator importInfo) {
        this.importInfo = importInfo;
    }

    /**
     * Returns true if conflict between int and uInt.
     *
     * @return true if conflict between int and uInt
     */
    public boolean isIntConflict() {
        return isIntConflict;
    }

    /**
     * Sets true if conflict between int and uInt.
     *
     * @param intConflict true if conflict between int and uInt
     */
    void setIntConflict(boolean intConflict) {
        isIntConflict = intConflict;
    }

    /**
     * Returns true if conflict between long and uLong.
     *
     * @return true if conflict between long and uLong
     */
    public boolean isLongConflict() {
        return isLongConflict;
    }

    /**
     * Sets true if conflict between long and uLong.
     *
     * @param longConflict true if conflict between long and uLong
     */
    void setLongConflict(boolean longConflict) {
        isLongConflict = longConflict;
    }

    /**
     * Returns java attribute info.
     *
     * @param importInfo        java qualified type info
     * @param attributeName     attribute name
     * @param attributeType     attribute type
     * @param isQualifiedAccess is the attribute a qualified access
     * @param isListAttribute   is list attribute
     * @return java attribute info.
     */
    public static JavaAttributeInfo getAttributeInfoForTheData(JavaQualifiedTypeInfoTranslator importInfo,
                                                               String attributeName,
                                                               YangType<?> attributeType, boolean isQualifiedAccess,
                                                               boolean isListAttribute) {

        if (attributeType != null) {
            attributeType = isTypeLeafref(attributeType);
        }
        attributeName = isTypeNameLeafref(attributeName, attributeType);
        JavaAttributeInfo newAttr = new JavaAttributeInfo();
        newAttr.setImportInfo(importInfo);
        newAttr.setAttributeName(attributeName);
        newAttr.setAttributeType(attributeType);
        newAttr.setIsQualifiedAccess(isQualifiedAccess);
        newAttr.setListAttr(isListAttribute);

        return newAttr;
    }
}
