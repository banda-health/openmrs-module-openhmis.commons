package org.openmrs.module.plm.db;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;

import org.hibernate.jdbc.Work;
import org.openmrs.module.plm.*;
import org.openmrs.module.plm.model.PersistentListItemModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseListProvider implements PersistentListProvider {
	private Log log = LogFactory.getLog(PersistentListServiceImpl.class);
	private SessionFactory sessionFactory;

	public DatabaseListProvider(SessionFactory factory) {
		sessionFactory = factory;
	}

	@Override
	public String getName() {
		return "Database List Provider";
	}

	@Override
	public String getDescription() {
		return "A persistent list provider that stores items in an OpenMRS database.";
	}

	@Override
	public void add(final PersistentListItemModel item) {
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = null;

		try
		{
			// Start transaction
			trans = session.beginTransaction();

			// Update all items >= index to be their current index + 1
			// TODO: Can this type of query be handled by Hibernate without needing to use hardcoded SQL?
			session.doWork(new Work() {
				public void execute(Connection connection) {
					try {
						PreparedStatement cmd = connection.prepareStatement("UPDATE table SET PrimaryOrder = PrimaryOrder + 1 WHERE ListId = ? AND PrimaryOrder >= ?");
						cmd.setInt(1, item.getItemId());
						cmd.setInt(2, item.getPrimaryOrder());

						cmd.executeUpdate();
					} catch (SQLException sex) {
						throw new PersistentListException(sex);
					}
				}
			});

			// Insert item with index
			session.save(item);
			session.flush();

			// Commit transaction
			trans.commit();
		} catch (Exception ex) {
			log.debug("The list add operation failed.  Rolling back transaction...");
			trans.rollback();
			log.debug("Transaction rolled back.");

			throw new PersistentListException("An exception occurred while attempting to add the item to the list.", ex);
		} finally {
			session.close();
		}
	}

	@Override
	public boolean remove(final PersistentListItemModel item) {
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = null;

		try
		{
			// Start transaction
			trans = session.beginTransaction();

			// Delete item with index
			session.delete(item);
			session.flush();

			// Update all items >= index to be their current index - 1
			// TODO: Can this type of query be handled by Hibernate without needing to use hardcoded SQL?
			session.doWork(new Work() {
				public void execute(Connection connection) {
					try {
						PreparedStatement cmd = connection.prepareStatement("UPDATE table SET PrimaryOrder = PrimaryOrder - 1 WHERE ListId = ? AND PrimaryOrder >= ?");
						cmd.setInt(1, item.getItemId());
						cmd.setInt(2, item.getPrimaryOrder());

						cmd.executeUpdate();
					} catch (SQLException sex) {
						throw new PersistentListException(sex);
					}
				}
			});

			// Commit transaction
			trans.commit();
		} catch (Exception ex) {
			log.error("The list item delete operation failed.  Rolling back transaction...", ex);
			trans.rollback();
			log.debug("Transaction rolled back.");

			throw new PersistentListException("An exception occurred while attempting to delete the item from the list.", ex);
		} finally {
			session.close();
		}

		return true;
	}

	@Override
	public void clear(PersistentList list) {
		// Delete all items with list key

	}

	@Override
	public PersistentListItemModel[] getItems(PersistentList list) {
		throw new NotImplementedException();

		// Return sorted list of items
	}
}

