package jdbcdemo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbcdemo.dao.mappers.ResultSetMapper;
import jdbcdemo.domain.IHaveId;

public abstract class RepositoryBase<TEntity extends IHaveId> implements Repository<TEntity>{
	
	protected Connection connection;
	protected Statement createTable;
	protected PreparedStatement insert;
	protected PreparedStatement selectAll;
	protected PreparedStatement update;
	protected PreparedStatement delete;
	
	private ResultSetMapper<TEntity> mapper;
	
	protected RepositoryBase(Connection connection, ResultSetMapper<TEntity> mapper) throws SQLException{
		this.mapper = mapper;
		try {
			this.connection = connection;
			createTable = connection.createStatement();
			insert = connection.prepareStatement(insertSql()); //precompiled if driver supports it
			update = connection.prepareStatement(updateSql());
			delete = connection.prepareStatement(deleteSql());
			selectAll = connection.prepareStatement(selectAllSql());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected abstract String createTableSql();
	protected abstract String tableName();
	protected abstract String insertSql();
	protected abstract String updateSql();
	protected abstract String deleteSql();
	protected abstract String selectAllSql();
	protected abstract void setupUpdate(TEntity entity) throws SQLException;
	protected abstract void setupInsert(TEntity entity) throws SQLException;
	
	public void createTable(){
		try {
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean tableExists = false;
			while(rs.next()){
				if(rs.getString("TABLE_NAME").equalsIgnoreCase(tableName())){
					tableExists=true;
					break;
				}
			}
			if(!tableExists){
				createTable.executeUpdate(createTableSql());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(TEntity entity) {
		try{
			delete.setInt(1, entity.getId());
			delete.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public void add(TEntity entity){
		try{
			setupInsert(entity); //Metoda abstrakcyjna, implementacja w podklasie dba o treść
			insert.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}

	public void update(TEntity entity) {
		try{
			setupUpdate(entity); //Metoda abstrakcyjna, implementacja w podklasie dba o treść
			update.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public List<TEntity> getAll(){
		List<TEntity> result = new ArrayList<TEntity>();
		try {
			ResultSet rs = selectAll.executeQuery();
			while(rs.next()){
				result.add(mapper.map(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public String toString() {
		String s = "";
		List<TEntity> all = getAll();
		for (TEntity e : all){
    		s = s + e.toString() + "\n";
    	}
		return s;
	}
	
	
}
