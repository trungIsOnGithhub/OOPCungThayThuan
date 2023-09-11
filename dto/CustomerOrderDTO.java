package dto;

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