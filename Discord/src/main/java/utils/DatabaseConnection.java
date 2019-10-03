package utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

public class DatabaseConnection {
	
	public DatabaseConnection() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reddit","root","June162004");
			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery("select * from hentai");
			while(results.next()) {
				System.out.println("[" + results.getString(1) + "] " + results.getString(2) + " " + results.getString(3));
			}
			connection.close();
			System.out.println("connection closed");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
