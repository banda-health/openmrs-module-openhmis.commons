/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and
 * limitations under the License.
 *
 * Copyright (C) OpenHMIS.  All Rights Reserved.
 */
package org.openmrs.module.openhmis.commons.api;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;

/**
 * Allows for ordering hibernate queries by some customized sql (for example, a database function). Adapted from:
 * http://blog.hexican.com/2012/05/how-to-customize-hibernate-order-by/
 */
public class CustomizedOrderBy extends Order {
	private String sqlExpression;

	public static Order asc(String sqlFormula) {
		if (!StringUtils.endsWith(sqlFormula, " asc")) {
			sqlFormula += " asc";
		}

		return new CustomizedOrderBy(sqlFormula);
	}

	public static Order desc(String sqlFormula) {
		if (!StringUtils.endsWith(sqlFormula, " desc")) {
			sqlFormula += " desc";
		}

		return new CustomizedOrderBy(sqlFormula);
	}

	protected CustomizedOrderBy(String sqlExpression) {
		super(sqlExpression, true);

		this.sqlExpression = sqlExpression;
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
		return sqlExpression;
	}

	public String toString() {
		return sqlExpression;
	}

}
