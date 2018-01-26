package jdbcdemo.dao.uow;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcUnitOfWork implements UnitOfWork {
	
	private Connection connection;
	private List<Entity> entities = new ArrayList<Entity>();
	
	public JdbcUnitOfWork(Connection connection) throws SQLException {
		this.connection = connection;
		this.connection.setAutoCommit(false);
	}

	public void markAsNew(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	public void markAsDeleted(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	public void markAsChanged(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	public void saveChanges() {
		// TODO Auto-generated method stub
		
	}

	public void rollback() {
		// TODO Auto-generated method stub
		
	}
s
}
