package in.coder.main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import in.coder.util.JdbcUtil;

public class InsertApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connection = null; 
		PreparedStatement pstmt = null;
		Scanner scanner = null;
		
		String name = null;
		String pdfLoc = null;
		int rowsAffected = 0;
		try {
			connection = JdbcUtil.getJdbcConnection();
			String sqlInsertQuery = "insert into cities(`name`,`history`) values(?,?)";
			if(connection!=null) {
				pstmt = connection.prepareStatement(sqlInsertQuery);
			}
			
			if(pstmt!=null) {
				scanner = new Scanner(System.in);
				if(scanner!=null) {
					System.out.println("Enter the city name:: ");
					name = scanner.next();
					System.out.println("Enter image pdf location :: ");
					pdfLoc = scanner.next();
					pstmt.setString(1, name);
					pstmt.setCharacterStream(2, new FileReader(new File(pdfLoc)));
					
					rowsAffected = pstmt.executeUpdate();
					System.out.println("No of rows affected:: "+rowsAffected);
				}
			}
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JdbcUtil.cleanUp(connection, pstmt, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			scanner.close();
		}
		

	}

}
