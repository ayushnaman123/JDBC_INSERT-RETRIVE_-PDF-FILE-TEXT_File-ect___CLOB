package in.coder.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

import in.coder.util.JdbcUtil;

public class RetrivalApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		Scanner scanner = null;
		int id = 0;
		try {
			connection = JdbcUtil.getJdbcConnection();
			
			String sqlSelectQuery = "Select id, name, history from cities where id = ?";
			if(connection!=null)
				pstmt = connection.prepareStatement(sqlSelectQuery);
			
			if(pstmt!=null) {
				scanner = new Scanner(System.in);
				
				if(scanner != null) {
					System.out.println("Enter id:: ");
				    id = scanner.nextInt();
				}
				pstmt.setInt(1, id);
				resultSet = pstmt.executeQuery();
			}
			
			if(resultSet!=null) {
				if(resultSet.next()) {
					System.out.println("ID\tNAME\tHISTORY");
					int cId = resultSet.getInt(1);
					String cName = resultSet.getString(2);
					Reader reader = resultSet.getCharacterStream(3);
					
					File file = new File("history_copy.txt");
					FileWriter writer = new FileWriter(file);
					
					IOUtils.copy(reader, writer);
					
					System.out.println(cId+"\t"+cName+"\t"+file.getAbsolutePath());
					writer.close();
				}
			}
			
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JdbcUtil.cleanUp(connection, pstmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			scanner.close();
		}

	}

}
