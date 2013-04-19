package org.openmrs.module.openhmis.commons.api.entity.search;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.OpenmrsData;

public class BaseDataTemplateSearch<T extends OpenmrsData> extends BaseAuditableTemplateSearch<T> {
	public static final long serialVersionUID = 0L;

	public BaseDataTemplateSearch(T template) {
		this(template, null);
	}

	public BaseDataTemplateSearch(T template, Boolean includeVoided) {
		super(template);

		this.includeVoided = includeVoided;
		this.dateVoidedComparisonType = DateComparisonType.EQUAL;
		this.voidReasonComparisonType = StringComparisonType.EQUAL;
	}

	private DateComparisonType dateVoidedComparisonType;
	private StringComparisonType voidReasonComparisonType;

	private Boolean includeVoided;

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
