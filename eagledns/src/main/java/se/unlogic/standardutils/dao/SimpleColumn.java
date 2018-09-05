/*******************************************************************************
 * Copyright (c) 2010 Robert "Unlogic" Olofsson (unlogic@unlogic.se).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0-standalone.html
 ******************************************************************************/
package se.unlogic.standardutils.dao;

import se.unlogic.standardutils.populators.QueryParameterPopulator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SimpleColumn<BeanType,ColumnType> implements Column<BeanType,ColumnType> {

    private final Field beanField;
    private final Method queryMethod;
    private final QueryParameterPopulator<?> queryParameterPopulator;
    private final String columnName;
    private final boolean autoGenerated;

    public SimpleColumn(Field beanField, Method queryMethod, QueryParameterPopulator<?> queryPopulator, String columnName, boolean autoGenerated) {
        super();
        this.beanField = beanField;
        this.queryMethod = queryMethod;
        this.columnName = columnName;
        this.autoGenerated = autoGenerated;
        this.queryParameterPopulator = queryPopulator;
    }

    @SuppressWarnings("unchecked")
    public ColumnType getParamValue(Object paramValue) {

        return (ColumnType) paramValue;
    }

    @SuppressWarnings("unchecked")
    public ColumnType getBeanValue(BeanType bean) {

        try {
            return (ColumnType) beanField.get(bean);

        } catch (IllegalArgumentException e) {

            throw new RuntimeException(e);

        } catch (IllegalAccessException e) {

            throw new RuntimeException(e);
        }
    }

    public Field getBeanField(){

        return beanField;
    }

    public Class<?> getParamType(){

        return beanField.getType();
    }

    public Method getQueryMethod() {
        return queryMethod;
    }

    public String getColumnName() {
        return columnName;
    }

    public boolean isAutoGenerated() {
        return autoGenerated;
    }

    public QueryParameterPopulator<?> getQueryParameterPopulator() {
        return queryParameterPopulator;
    }

    public static <BT,CT> SimpleColumn<BT,CT> getGenericInstance(Class<BT> beanClass, Class<CT> fieldClass, Field beanField, Method queryMethod, QueryParameterPopulator<?> queryPopulator, String columnName, boolean autoGenerated) {

        return new SimpleColumn<BT, CT>(beanField, queryMethod, queryPopulator, columnName, autoGenerated);
    }
}
