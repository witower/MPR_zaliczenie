package jdbcdemo.dao;

import java.sql.Connection;
import java.sql.SQLException;

import jdbcdemo.dao.mappers.*;
import jdbcdemo.dao.uow.UnitOfWork;
import jdbcdemo.domain.*;

public class RepositoryCatalog {
	
	Connection connection;
	UnitOfWork uow;
		
	public RepositoryCatalog(Connection connection) {
		this.connection = connection;
	}

	public Repository<Person> people() {
		try {
			return new PersonRepository(connection, new PersonResultMapper(), uow);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public Repository<Car> cars() {
		try {
			return new CarRepository(connection, new CarResultMapper(), uow);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
