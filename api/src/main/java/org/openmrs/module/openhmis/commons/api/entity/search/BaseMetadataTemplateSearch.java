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
import org.openmrs.OpenmrsMetadata;

/**
 * Base template search class for {@link org.openmrs.OpenmrsMetadata} models.
 * @param <T> The model class.
 */
public class BaseMetadataTemplateSearch<T extends OpenmrsMetadata> extends BaseAuditableTemplateSearch<T> {
	public static final long serialVersionUID = 0L;
	private StringComparisonType nameComparisonType;
	private StringComparisonType descriptionComparisonType;
	private StringComparisonType retireReasonComparisonType;
	private DateComparisonType dateRetiredComparisonType;
	private Boolean includeRetired;

	public BaseMetadataTemplateSearch(T template) {
		this(template, StringComparisonType.EQUAL, null);
	}

	public BaseMetadataTemplateSearch(T template, Boolean includeRetired) {
		this(template, StringComparisonType.EQUAL, includeRetired);
	}

	public BaseMetadataTemplateSearch(T template, StringComparisonType nameComparisonType, Boolean includeRetired) {
		super(template);

		this.nameComparisonType = nameComparisonType;
		this.includeRetired = includeRetired;
		this.descriptionComparisonType = StringComparisonType.EQUAL;
		this.retireReasonComparisonType = StringComparisonType.EQUAL;
		this.dateRetiredComparisonType = DateComparisonType.EQUAL;
	}

	public StringComparisonType getNameComparisonType() {
		return nameComparisonType;
	}

	public void setNameComparisonType(StringComparisonType nameComparisonType) {
		this.nameComparisonType = nameComparisonType;
	}

	public StringComparisonType getDescriptionComparisonType() {
		return descriptionComparisonType;
	}

	public void setDescriptionComparisonType(StringComparisonType descriptionComparisonType) {
		this.descriptionComparisonType = descriptionComparisonType;
	}

	public StringComparisonType getRetireReasonComparisonType() {
		return retireReasonComparisonType;
	}

	public void setRetireReasonComparisonType(StringComparisonType retireReasonComparisonType) {
		this.retireReasonComparisonType = retireReasonComparisonType;
	}

	public DateComparisonType getDateRetiredComparisonType() {
		return dateRetiredComparisonType;
	}

	public void setDateRetiredComparisonType(DateComparisonType dateRetiredComparisonType) {
		this.dateRetiredComparisonType = dateRetiredComparisonType;
	}

	public Boolean getIncludeRetired() {
		return includeRetired;
	}

	public void setIncludeRetired(Boolean includeRetired) {
		this.includeRetired = includeRetired;
	}

	@Override
	public void updateCriteria(Criteria criteria) {
		super.updateCriteria(criteria);

		T t = getTemplate();
		if (t.getName() != null) {
			criteria.add(createCriterion("name", t.getName(), nameComparisonType));
		}
		if (t.getDescription() != null) {
			criteria.add(createCriterion("description", t.getName(), nameComparisonType));
		}

		if (includeRetired != null) {
			if (!includeRetired) {
				criteria.add(Restrictions.eq("retired", false));
			}
		} else if (t.isRetired() != null) {
			criteria.add(Restrictions.eq("retired", t.isRetired()));
		}

		if (t.getRetiredBy() != null) {
			criteria.add(Restrictions.eq("retiredBy", t.getRetiredBy()));
		}
		if (t.getDateRetired() != null) {
			criteria.add(createCriterion("dateRetired", t.getDateRetired(), dateRetiredComparisonType));
		}
		if (t.getRetireReason() != null) {
			criteria.add(createCriterion("retireReason", t.getRetireReason(), retireReasonComparisonType));
		}
	}
}
