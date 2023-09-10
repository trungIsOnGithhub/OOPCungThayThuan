import java.util.ArrayList;
import java.util.List;

import java.sql.*;

class DTOFactory {
    public static DTO create(String tableName, ResultSet executedResult) throws SQLException {
    	if(tableName.equals("customer_order")) {
    		return new CustomerOrderDTO(executedResult.getLong("total"));
    	}
    	return null;
    }
}

class CustomerOrderDTO implements DTO {
	Integer totalPrice;
	String phoneNumber = "";

	CustomerOrderDTO(long total) {
		this.totalPrice = total;
		this.phoneNumber = "";
	}

	CustomerOrderDTO(long total, String phoneNumber) {
		this.totalPrice = total;
		this.phoneNumber = phoneNumber;
	}
	
	public PreparedStatement fillDataSQLStatement(PreparedStatement statement) throws SQLException {
		statement.setInt(1, totalPrice.intValue());
		statement.setString(2, phoneNumber);
		
		return statement;	
	}
	
	@Override
	public String toString() {
		return "--> " + total + "  " + phone;
	}
}

class MenuItemDTO implements DTO {
    String nameOfItem;
    Integer priceOfItem;

	MenuItemDTO(long total) {
		this.total = total;
	}
	
	public PreparedStatement fillDataSQLStatement(PreparedStatement statement) throws SQLException {
		statement.setString(1, nameOfItem);
		statement.setInt(2, priceOfItem.intValue());
		
		return statement;	
	}
	
	@Override
	public String toString() {
		return "--> " + total + "  " + phone;
	}
}

interface DTO {
	public PreparedStatement fillDataSQLStatement(PreparedStatement insertSQLstatment) throws SQLException;
}

class connectionFactory {
	static String dbms = "mysql";
	static String dbName = "oop";
	static String user = "root";
	static String serverName = "localhost";
	static String portNumber = "3306";
	static String password = "1234";
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection conn = null;

		if (dbms.equals("mysql")) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:" + dbms + "://" +
					serverName +
					":" + portNumber + "/" + dbName,
					user, password);
		} else if (dbms.equals("derby")) {
			conn = DriverManager.getConnection(
					"jdbc:" + dbms + ":" +
					dbName +
					";create=true",
					user, password);
		}
		System.out.println("Connected to database");
		return conn;
	}
}

class DAO<T extends DTO> {
	private String tableName;

	public DAO(String tableName) {
		this.tableName = tableName;
	}

	@SuppressWarnings("unchecked")
	public T getOne(long id) {
			try {
				Connection connection = connectionFactory.getConnection();

				String sqlQuery = String.format("SELECT * FROM %s WHERE id=%s", tableName, id);
				Statement statement = connection.createStatement();

				ResultSet rs = statement.executeQuery(sqlQuery);

				if(rs.next()) {
					return (T)DTOFactory.create(this.tableName, rs);
				}
			} catch(Exception exception) {
				exception.printStackTrace();
			}

		return null;
	}
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		try {
			Connection connection = connectionFactory.getConnection();
			
			String sqlQuery = String.format("SELECT * FROM %s", tableName);
			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(sqlQuery);

			List<T> allRecord = new ArrayList<T>();

			while(rs.next()) {
				allRecord.add( (T)DTOFactory.create(this.tableName, rs) );
			}

			return allRecord;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
    public boolean insert(T newRecord) {
		// Connector connector = new Connector();
		// Connection connection = connector.getConnection();
		try {
			Connection connection = connectionFactory.getConnection();

			PreparedStatement statement = connection.prepareStatement("INSERT INTO " + this.tableName +"(total, phone) VALUES (?, ?)");
	
			newRecord.fillDataSQLStatement(statement);
			// ps.setString(1, );
			// ps.setString(2, user.getPass());
			// ps.setInt(3, user.getAge());

			int numRowAffected = statement.executeUpdate();

			if(numRowAffected == 1) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return false;
	}
    public boolean update(long id, T updatedRecord) {
		// Connector connector = new Connector();
		// Connection connection = connector.getConnection();
		try {
			Connection connection = connectionFactory.getConnection();

			PreparedStatement statement = connection.prepareStatement("UPDATE user SET name=?, pass=?, age=? WHERE id=?");
			
			updatedRecord.fillDataSQLStatement(statement);

			int numRowAffected = statement.executeUpdate();

			if(numRowAffected == 1) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return false;
	}
    public boolean delete(long id) {
		try {
			Connection connection = connectionFactory.getConnection();

			String sqlQuery = String.format("DELETE FROM %s WHERE id=%s", tableName, id);
			Statement statement = connection.createStatement();

			int numRowAffected = statement.executeUpdate(sqlQuery);

			if(numRowAffected == 1) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return false;
	}
}

public class Vui {
	public static void main(String[] args) {
//        try {
//        	Connection connection = connectionFactory.getConnection();
// 
//            Statement statement;
//            statement = connection.createStatement();
//            ResultSet resultSet;
//            resultSet = statement.executeQuery(
//                "select * from customer_order");
//            int code;
//            String title;
//            while (resultSet.next()) {
//                code = resultSet.getInt("total");
//                title = resultSet.getString("phone").trim();
//                System.out.println("Code : " + code
//                                   + " Title : " + title);
//            }
//            resultSet.close();
//            statement.close();
//            connection.close();
//        }
//        catch (Exception exception) {
//            System.out.println(exception);
//        }
		
		DAO<CustomerOrderDTO> customerOrderDAO = new DAO<>("customer_order");
		
		customerOrderDAO.insert(new CustomerOrderDTO(797979));
		
		for(CustomerOrderDTO co : customerOrderDAO.getAll())
			System.out.println(co);
	}
}