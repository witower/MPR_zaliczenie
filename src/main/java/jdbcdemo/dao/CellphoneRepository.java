package jdbcdemo.dao;

import java.sql.Connection;
import java.sql.SQLException;

import jdbcdemo.dao.mappers.ResultSetMapper;
import jdbcdemo.dao.uow.UnitOfWork;
import jdbcdemo.domain.Cellphone;

public class CellphoneRepository extends RepositoryBase<Cellphone> {
	
	public CellphoneRepository(Connection connection, ResultSetMapper<Cellphone> mapper, UnitOfWork uow) throws SQLException{
		super(connection, mapper, uow);
	}
	
	@Override
	protected String tableName() {
		return "cellphones";
	}
	
	@Override
	protected String createTableSql() {
		return "CREATE TABLE cellphones("
				+ "id INT GENERATED BY DEFAULT AS IDENTITY,"
				+ "brand VARCHAR(20),"
				+ "model VARCHAR(10)"
				+ ")";
	}
	
	@Override
	protected String insertSql() {
		return "INSERT INTO cellphones(brand,model) VALUES (?,?)";
	}
	
	@Override
	protected String updateSql() {
		return "UPDATE cellphones SET (brand, model) = (?,?) WHERE id=?";
	}
	
	@Override
	protected String deleteSql() {
		return "DELETE FROM cellphones WHERE id=?";
	}
	
	@Override
	protected String selectAllSql() {
		return "SELECT * FROM cellphones";
	}

	@Override
	protected void setupInsert(Cellphone entity) throws SQLException {
		insert.setString(1, entity.getBrand());
		insert.setString(2, entity.getModel());
	}

	@Override
	protected void setupUpdate(Cellphone entity) throws SQLException {
		update.setString(1, entity.getBrand());
		update.setString(2, entity.getModel());
		update.setInt(3, entity.getId());
		update.executeUpdate();
	}
}
