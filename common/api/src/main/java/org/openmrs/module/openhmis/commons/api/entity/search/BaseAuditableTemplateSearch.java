package org.openmrs.module.openhmis.commons.api.entity.search;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Auditable;

public class BaseAuditableTemplateSearch<T extends Auditable> extends BaseObjectTemplateSearch<T> {
	public static final long serialVersionUID = 0L;

	public BaseAuditableTemplateSearch(T template) {
		super(template);

		this.dateCreatedComparisonType = DateComparisonType.EQUAL;
		this.dateChangedComparisonType = DateComparisonType.EQUAL;
	}

	private DateComparisonType dateCreatedComparisonType;
	private DateComparisonType dateChangedComparisonType;

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
