package org.openmrs.module.plm.db;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.openmrs.module.plm.*;
import org.openmrs.module.plm.model.PersistentListItemModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseListProvider implements PersistentListProvider {
	private static final String ADD_SQL = "UPDATE plm_list_items SET primary_order = primary_order + 1 WHERE list_id = ? AND primary_order >= ?";
	private static final String REMOVE_SQL = "UPDATE plm_list_items SET primary_order = primary_order - 1 WHERE list_id = ? AND primary_order >= ?";
	private static final String CLEAR_SQL = "DELETE FROM plm_list_items WHERE list_id = ?";

	private Log log = LogFactory.getLog(PersistentListServiceImpl.class);
	private SessionFactory sessionFactory;

	public DatabaseListProvider(SessionFactory sessionFactory) {
		sessionFactory = sessionFactory;
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
						PreparedStatement cmd = connection.prepareStatement(ADD_SQL);
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
						PreparedStatement cmd = connection.prepareStatement(REMOVE_SQL);
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
	public void clear(final PersistentList list) {
		Session session = sessionFactory.getCurrentSession();

		try {
			// Delete all items with list key
			session.doWork(new Work() {
				public void execute(Connection connection) {
					try {
						PreparedStatement cmd = connection.prepareStatement(CLEAR_SQL);
						cmd.setInt(1, list.getId());

						cmd.executeUpdate();
					} catch (SQLException sex) {
						throw new PersistentListException(sex);
					}
				}
			});
		} catch (Exception ex) {
			throw new PersistentListException("An exception occurred while attempting to get the list items.", ex);
		} finally {
			session.close();
		}
	}

	@Override
	public PersistentListItemModel[] getItems(PersistentList list) {
		List<PersistentListItemModel> result = null;

		Session session = sessionFactory.getCurrentSession();
		try {
			// Return the items in the specified list ordered by the primary order
			Criteria search = session.createCriteria(PersistentListItemModel.class)
					.add(Restrictions.eq("list_id", list.getId()))
					.addOrder(Order.asc("primary_order"))
					.addOrder(Order.asc("secondary_order"))
					.addOrder(Order.asc("tertiary_order"));

			result = new ArrayList<PersistentListItemModel>(search.list());
		} catch (Exception ex) {
			throw new PersistentListException("An exception occurred while attempting to get the list items.", ex);
		} finally {
			session.close();
		}

		if (result == null) {
			return new PersistentListItemModel[0];
		} else {
			return result.toArray(new PersistentListItemModel[0]);
		}
	}
}

