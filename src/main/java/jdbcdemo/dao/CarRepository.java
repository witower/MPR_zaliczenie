package jdbcdemo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbcdemo.domain.Car;
import jdbcdemo.domain.Person;

public class CarRepository extends RepositoryBase {
	
	public CarRepository(){
		super();
	}
	
	@Override
	protected String tableName() {
		return "cars";
	}
	
	@Override
	protected String createTableSql() {
		return "CREATE TABLE cars("
				+ "id INT GENERATED BY DEFAULT AS IDENTITY,"
				+ "brand VARCHAR(20),"
				+ "registration VARCHAR(10)"
				+ ")";
	}
	
	@Override
	protected String insertSql() {
		return "INSERT INTO cars(brand,registration) VALUES (?,?)";
	}
	@Override
	protected String updateSql() {
		return "UPDATE cars SET (brand, regstration) = (?,?) WHERE id=?";
	}
	@Override
	protected String deleteSql() {
		return "DELETE FROM cars WHERE id=?";
	}
	@Override
	protected String selectAllSql() {
		return "SELECT * FROM cars";
	}
	
	public List<Car> getAll(){
		List<Car> result = new ArrayList<Car>();
		try {
			ResultSet rs = selectAll.executeQuery();
			while(rs.next()){
				Car c = new Car();
				c.setId(rs.getInt("id"));
				c.setBrand(rs.getString("brand"));
				c.setRegistration(rs.getString("registation"));
				result.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void add(Car car){
		try{
			insert.setString(1, car.getBrand());
			insert.setString(2, car.getRegistration());
			insert.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public void update(Car car) {
		try{
			update.setString(1, car.getBrand());
			update.setString(2, car.getRegistration());
			update.setInt(3, car.getId());
			update.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public void delete(Person person) {
		try{
			delete.setInt(1, person.getId());
			delete.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
}
