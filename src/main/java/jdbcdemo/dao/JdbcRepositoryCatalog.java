package jdbcdemo.dao;

import java.sql.Connection;
import java.sql.SQLException;

import jdbcdemo.dao.mappers.*;
import jdbcdemo.dao.uow.UnitOfWork;
import jdbcdemo.domain.*;

public class JdbcRepositoryCatalog implements RepositoryCatalog {
	
	Connection connection;
	UnitOfWork uow;
		
	public JdbcRepositoryCatalog(Connection connection, UnitOfWork uow) {
		this.connection = connection;
		this.uow = uow;
	}

	/* (non-Javadoc)
	 * @see jdbcdemo.dao.RepositoryCatalog#people()
	 */
	public Repository<Person> people() {
		try {
			return new PersonRepository(connection, new PersonResultMapper(), uow);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	/* (non-Javadoc)
	 * @see jdbcdemo.dao.RepositoryCatalog#cars()
	 */
	public Repository<Car> cars() {
		try {
			return new CarRepository(connection, new CarResultMapper(), uow);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see jdbcdemo.dao.RepositoryCatalog#saveChanges()
	 */
	public void saveChanges() {
		uow.saveChanges();
	}
}
