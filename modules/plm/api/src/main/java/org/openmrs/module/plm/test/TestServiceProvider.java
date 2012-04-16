package org.openmrs.module.plm.test;

import org.openmrs.module.plm.List;
import org.openmrs.module.plm.ListServiceProvider;

import java.util.Collection;

public class TestServiceProvider implements ListServiceProvider {
	@Override
	public Collection<List> getLists() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void addList(List list) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void removeList(String key) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
