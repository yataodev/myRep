package com.yatao.phonebook;
import java.sql.*;

public class Phonebook {
	
	private String userName;
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	public Phonebook(String userName) {
		this.userName = userName;
		createPhonebook(userName);
	}
	
	public void createPhonebook(String userName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/phonebook", "root", "");
			stmt = conn.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS contacts_of_" + userName + " (" +
					"id INTEGER NOT NULL AUTO_INCREMENT," +
					"first_name VARCHAR(255)," +
					"last_name VARCHAR(255)," +
					"phone_number VARCHAR(20)," +
					"PRIMARY KEY (id)" +
					");");
			System.out.println("A phonebook of " + userName + " was created.");
		} catch (Exception e) {
			System.out.println("Action failed!\n" + e.getMessage());
		}
	}

	public void createContact(String firstName, String lastName, String phoneNumber) throws SQLException {
		rs = stmt.executeQuery("SELECT * FROM contacts_of_" + this.getUserName() +
				" WHERE first_name = '" + firstName + "'" +
				" AND last_name = '" + lastName + "'" + 
				" AND phone_number = '" + phoneNumber + "';");
		int rowCount = rs.getRow();
		System.out.println("Rwo Number: " + rowCount);
		stmt.executeUpdate("INSERT INTO contacts_of_" + this.getUserName() + 
				" (first_name, last_name, phone_number)" +
				" VALUES " +
				"('" + firstName + "', '" + lastName + "', '" + phoneNumber + "');" );
	}
	
	public void addUnique(String firstName, String lastName, String phoneNumber) throws SQLException {
		stmt.executeUpdate("ALTER TABLE contacts_of_" + this.getUserName() + 
				" (first_name, last_name, phone_number)" +
				" VALUES " +
				"('" + firstName + "', '" + lastName + "', '" + phoneNumber + "');" );
	}
	
	public void readContact(int id) throws SQLException {
		rs = stmt.executeQuery("SELECT * FROM contacts_of_" + this.getUserName() + " WHERE id = " + id + ";");
		printResultSet(rs);
	}
	
	public void updateContact(int id, String firstName, String lastName, String phoneNumber) throws SQLException {
		stmt.executeUpdate("UPDATE contacts_of_" + this.getUserName() +
				" SET first_name = '" + firstName + "'," +
				" last_name = '" + lastName + "'," +
				" phone_number = '" + phoneNumber + "'" +
				" WHERE id = " + id + ";");
	}
	
	public void deleteContact(int id) throws SQLException {
		stmt.executeUpdate("DELETE FROM contacts_of_" + this.getUserName() + " WHERE id = " + id + ";");
	}
	
	public void showContacts() throws SQLException {
		rs = stmt.executeQuery("SELECT * FROM contacts_of_" + this.getUserName() + ";");
		printResultSet(rs);
	}
	
	public void printResultSet(ResultSet rs) throws SQLException {
		System.out.printf("%-10s%-20s%-20s%s\n", "ID", "First Name", "Last Name", "Phone Number");
		System.out.println("+--------------------------------------------------------------+");
		while (rs.next()) {
			System.out.printf("%-10d%-20s%-20s%s\n", rs.getInt("id"), rs.getString("first_name"),
					rs.getString("last_name"), rs.getString("phone_number"));
		}
		System.out.println();
	}
	
	public void close() {	
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
