package com.revature.project1.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/*
 * The Connection utility class that is designed to obtain a Connection to the database
 * is often structured as a Singleton
 */


public class ConnectionUtil {

	private static Properties properties = new Properties();
	private static final String propertiesPath = "C:\\Users\\zdn_8\\Desktop\\Project1\\src\\main\\resources\\application.properties";

	// loading properties, like url
	private static void loadProperties(){
		properties = new Properties();
		File file = new File(propertiesPath);
		try{
			InputStream stream = new FileInputStream(file.getAbsolutePath());
			properties.load(stream);
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	private static Connection conn = null;

	private ConnectionUtil() {
		super();
	}

	public static Connection getConnection() {

		if(properties != null){
			loadProperties();
		}

		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(
					properties.getProperty(("url")),
					properties.getProperty("username"),
					properties.getProperty("password")
			);

		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("WE FAILED TO GET A CONNECTION!");
			return null;
		}

		return conn;
	}
}

