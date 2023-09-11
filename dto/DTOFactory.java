package dto;

interface DTO {
	public PreparedStatement fillDataSQLStatement(PreparedStatement insertSQLstatment) throws SQLException;
}

public class DTOFactory {
    public static DTO create(String tableName, ResultSet executedResult) throws SQLException {
    	if(tableName.equals("customer_order")) {
    		return new CustomerOrderDTO(executedResult.getLong("total"));
    	}
    	return null;
    }
}