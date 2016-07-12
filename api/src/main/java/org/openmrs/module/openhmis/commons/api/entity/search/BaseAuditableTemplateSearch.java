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
import org.openmrs.Auditable;

/**
 * Base template search class for {@link org.openmrs.Auditable} models.
 * @param <T> The model class.
 */
public class BaseAuditableTemplateSearch<T extends Auditable> extends BaseObjectTemplateSearch<T> {
	public static final long serialVersionUID = 0L;
	private DateComparisonType dateCreatedComparisonType;
	private DateComparisonType dateChangedComparisonType;

	public BaseAuditableTemplateSearch(T template) {
		super(template);

		this.dateCreatedComparisonType = DateComparisonType.EQUAL;
		this.dateChangedComparisonType = DateComparisonType.EQUAL;
	}

	public DateComparisonType getDateCreatedComparisonType() {
		return dateCreatedComparisonType;
	}

	public void setDateCreatedComparisonType(DateComparisonType dateCreatedComparisonType) {
		this.dateCreatedComparisonType = dateCreatedComparisonType;
	}

	public DateComparisonType getDateChangedComparisonType() {
		return dateChangedComparisonType;
	}

	public void setDateChangedComparisonType(DateComparisonType dateChangedComparisonType) {
		this.dateChangedComparisonType = dateChangedComparisonType;
	}

	@Override
	public void updateCriteria(Criteria criteria) {
		super.updateCriteria(criteria);

		T t = getTemplate();
		if (t.getCreator() != null) {
			criteria.add(Restrictions.eq("creator", t.getCreator()));
		}
		if (t.getDateCreated() != null) {
			criteria.add(createCriterion("dateCreated", t.getDateCreated(), dateCreatedComparisonType));
		}
		if (t.getChangedBy() != null) {
			criteria.add(Restrictions.eq("changedBy", t.getChangedBy()));
		}
		if (t.getDateChanged() != null) {
			criteria.add(createCriterion("dateChanged", t.getDateChanged(), dateChangedComparisonType));
		}
	}
}
