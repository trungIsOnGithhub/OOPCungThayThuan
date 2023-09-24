package dto;

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