package jdbcdemo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbcdemo.dao.mappers.ResultSetMapper;
import jdbcdemo.dao.uow.Entity;
import jdbcdemo.dao.uow.UnitOfWork;
import jdbcdemo.dao.uow.UnitOfWorkRepository;
import jdbcdemo.domain.IHaveId;

public abstract class RepositoryBase<TEntity extends IHaveId> 
implements Repository<TEntity>, UnitOfWorkRepository
{

	protected Connection connection;
	protected Statement createTable;
	protected PreparedStatement insert;
	protected PreparedStatement selectAll;
	protected PreparedStatement update;
	protected PreparedStatement delete;
	
	private ResultSetMapper<TEntity> mapper;
	UnitOfWork uow;
	
	protected RepositoryBase (
			Connection connection, 
			ResultSetMapper<TEntity> mapper,
			UnitOfWork uow
	) throws SQLException {
		this.mapper = mapper;
		this.uow = uow;
		this.connection = connection; // jest już po trycatchu, więc wynoszę poza
		createTable = connection.createStatement(); //to nie potrzebuje trycatcha?
		
		try {  // prepareStatement prekomipule się łącząc z bazą, więc trycatch
			//bez tego trycatcha nie można utworzyc tabeli, przez błąd prekompilacji prepareStatement
			//pyta o tablę np. Person, której jeszcze nie ma
			insert = connection.prepareStatement(insertSql());
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
	
	public void persistDelete(Entity entity) {
		try{
			delete.setInt(1, ((TEntity)entity.getEntity()).getId());
			delete.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public void persistAdd(Entity entity){
		try{
			setupInsert((TEntity)entity.getEntity());
			insert.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}

	public void persistUpdate(Entity entity) {
		try{
			setupUpdate((TEntity) entity.getEntity());
			update.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public void delete(TEntity entity) {
		Entity ent = new Entity();
		ent.setEntity(entity);
		uow.markAsDeleted(ent);
	}
	
	public void add(TEntity entity) {
		Entity ent = new Entity();
		ent.setEntity(entity);
		uow.markAsNew(ent);
	}
	
	public void update(TEntity entity) {
		Entity ent = new Entity();
		ent.setEntity(entity);
		uow.markAsChanged(ent);
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
