package org.openmrs.module.webservices.rest.resource;

import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.model.IAttributeType;
import org.openmrs.module.openhmis.commons.api.entity.model.ISimpleAttributeType;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.Converter;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.api.Resource;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingSubclassHandler;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;

// @formatter:off
/**
 * REST resource for {@link org.openmrs.module.openhmis.commons.api.entity.model.ISimpleAttributeType}s
 * @param <E> The simple attribute type class
 */
public abstract class BaseRestAttributeTypeResource<E extends IAttributeType>
        extends BaseRestMetadataResource<E>
        implements DelegatingSubclassHandler<IAttributeType, E>, Resource, Converter<E> {
// @formatter:on
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = super.getRepresentationDescription(rep);
		description.addProperty("attributeOrder");
		description.addProperty("format");
		description.addProperty("foreignKey");
		description.addProperty("regExp");
		description.addProperty("required");
		description.addProperty("retired");

		return description;
	}

	@Override
	public String getTypeName() {
		return getEntityClass().getSimpleName();
	}

	@Override
	public PageableResult getAllByType(RequestContext context) {
		PagingInfo info = PagingUtil.getPagingInfoFromContext(context);

		return new AlreadyPaged<E>(context, getService().getAll(info), info.hasMoreResults());
	}

	@Override
	protected PageableResult doSearch(RequestContext context) {
		if (context.getType().equals(getTypeName())) {
			return getAllByType(context);
		} else {
			throw new ResourceDoesNotSupportOperationException();
		}
	}

	@Override
	public Class<IAttributeType> getSuperclass() {
		return IAttributeType.class;
	}

	@Override
	public Class<E> getSubclassHandled() {
		return getEntityClass();
	}
}
