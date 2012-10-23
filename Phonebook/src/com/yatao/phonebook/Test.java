package com.yatao.phonebook;

import java.sql.SQLException;

public class Test {

	public static void main(String[] args) {
		Phonebook myPhonebook = new Phonebook("yasser");
		try {
			myPhonebook.createContact("Taher", "Taoufiq", "055659949494");
			//myPhonebook.readContact(1);
			myPhonebook.showContacts();
			myPhonebook.deleteContact(11);
			myPhonebook.updateContact(6, "Abdelmonaim", "Remani", "01487997897");
			myPhonebook.showContacts();
			myPhonebook.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		System.out.printf("%-10s%-20s%-20s%s\n", "ID", "First Name", "Last Name", "Phone Number");
//		System.out.println("----------------------------------------------------------------");
	}

}
