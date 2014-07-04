package org.openmrs.module.webservices.rest.resource;

import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.model.*;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.Converter;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.api.Resource;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingSubclassHandler;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;

public abstract class BaseRestInstanceAttributeTypeResource<
		E extends BaseInstanceAttributeType<TInstanceType>,
		TOwner extends ICustomizableInstance<TInstanceType, TAttribute>,
		TInstanceType extends IInstanceType<E>,
		TAttribute extends IInstanceAttribute<TOwner, E>>
	extends BaseRestMetadataResource<E>
	implements DelegatingSubclassHandler<IInstanceAttributeType, E>, Resource, Converter<E> {

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
	public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
		DelegatingResourceDescription description = super.getCreatableProperties();

		return description;
	}

	@Override
	public String getTypeName() {
		return getEntityClass().getSimpleName();
	}

	@Override
	public PageableResult getAllByType(RequestContext context) throws ResourceDoesNotSupportOperationException {
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
	public Class<IInstanceAttributeType> getSuperclass() {
		return IInstanceAttributeType.class;
	}

	@Override
	public Class<E> getSubclassHandled() {
		return getEntityClass();
	}
}

