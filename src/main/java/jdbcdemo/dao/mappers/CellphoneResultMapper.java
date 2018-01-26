package jdbcdemo.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import jdbcdemo.domain.Cellphone;

public class CellphoneResultMapper implements ResultSetMapper<Cellphone> {

	public Cellphone map(ResultSet rs) throws SQLException {
		Cellphone c = new Cellphone();
		c.setId(rs.getInt("id"));
		c.setBrand(rs.getString("brand"));
		c.setModel(rs.getString("model"));
		return c;
	}

}