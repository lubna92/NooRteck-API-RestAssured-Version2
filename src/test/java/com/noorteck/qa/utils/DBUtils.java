package com.noorteck.qa.utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

public class DBUtils extends Constants {

	public static void connectToDB() {
		try {
			// Load the Driver
			Class.forName(CLASSNAME);

			// Create connection with database
			connection = DriverManager.getConnection(
					envDataMap.get("DBServerURL").toString(),
					envDataMap.get("DBUserName").toString(), 
					envDataMap.get("DBPassword").toString());

			// create statement object
			statement = connection.createStatement();

		} catch (Exception e) {
			System.out.println("Could not establish connection with DB");
			e.printStackTrace();
		}
	}

	/**
	 * This method takes query and retrieves the result set
	 * 
	 * @param query
	 * @return
	 */
	public static ResultSet executeQuery(String query) {
		try {
			rs = statement.executeQuery(query);
		} catch (Exception e) {
			System.out.println("Couldnot retireve data from table");
			e.printStackTrace();
		}
		return rs;
	}

	public static List<LinkedHashMap<String, String>> getResultSetInListOfMap(ResultSet rs) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {
				LinkedHashMap<String, String> lMap = new LinkedHashMap<String, String>();

				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					lMap.put(rsmd.getColumnName(i), rs.getString(i));
				}
				resultsetInListOfMaps.add(lMap);
			}
		} catch (Exception e) {
			System.out.println("Couldnot retireve data from table");
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					System.out.println("rs object is null");
					e.printStackTrace();
				}
			}
		}
		return resultsetInListOfMaps;
	}

	public static List<LinkedHashMap<String, String>> makeDBRequest(String query) {
		connectToDB();
		ResultSet rs = executeQuery(query);
		return getResultSetInListOfMap(rs);
	}

}
