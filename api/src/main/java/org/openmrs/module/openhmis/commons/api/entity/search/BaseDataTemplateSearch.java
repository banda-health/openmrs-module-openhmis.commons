/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.openhmis.commons.api.entity.search;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.OpenmrsData;

/**
 * Base template search class for {@link org.openmrs.OpenmrsData} models.
 * @param <T> The model class.
 */
public class BaseDataTemplateSearch<T extends OpenmrsData> extends BaseAuditableTemplateSearch<T> {
	public static final long serialVersionUID = 0L;
	private DateComparisonType dateVoidedComparisonType;
	private StringComparisonType voidReasonComparisonType;
	private Boolean includeVoided;

	public BaseDataTemplateSearch(T template) {
		this(template, null);
	}

	public BaseDataTemplateSearch(T template, Boolean includeVoided) {
		super(template);

		this.includeVoided = includeVoided;
		this.dateVoidedComparisonType = DateComparisonType.EQUAL;
		this.voidReasonComparisonType = StringComparisonType.EQUAL;
	}

	public DateComparisonType getDateVoidedComparisonType() {
		return dateVoidedComparisonType;
	}

	public void setDateVoidedComparisonType(DateComparisonType dateVoidedComparisonType) {
		this.dateVoidedComparisonType = dateVoidedComparisonType;
	}

	public StringComparisonType getVoidReasonComparisonType() {
		return voidReasonComparisonType;
	}

	public void setVoidReasonComparisonType(StringComparisonType voidReasonComparisonType) {
		this.voidReasonComparisonType = voidReasonComparisonType;
	}

	public boolean getIncludeVoided() {
		return includeVoided;
	}

	public void setIncludeVoided(boolean includeVoided) {
		this.includeVoided = includeVoided;
	}

	@Override
	public void updateCriteria(Criteria criteria) {
		super.updateCriteria(criteria);

		T t = getTemplate();

		if (includeVoided != null) {
			if (!includeVoided) {
				criteria.add(Restrictions.eq("voided", false));
			}
		} else if (t.isVoided() != null) {
			criteria.add(Restrictions.eq("voided", t.isVoided()));
		}

		if (t.getVoidedBy() != null) {
			criteria.add(Restrictions.eq("voidedBy", t.getVoidedBy()));
		}
		if (t.getDateVoided() != null) {
			criteria.add(createCriterion("dateVoided", t.getDateVoided(), dateVoidedComparisonType));
		}
		if (t.getVoidReason() != null) {
			criteria.add(createCriterion("voidReason", t.getVoidReason(), voidReasonComparisonType));
		}
	}
}
