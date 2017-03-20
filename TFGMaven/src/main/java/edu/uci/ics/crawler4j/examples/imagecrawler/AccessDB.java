package edu.uci.ics.crawler4j.examples.imagecrawler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JOptionPane;
import org.apache.commons.dbcp.BasicDataSource;

public class AccessDB {
	
	public int Guardar(String title, String description, String source, String format, String hashedName){

		//Pool metodospool = new Pool();
		BasicDataSource basicDataSource = new BasicDataSource();
		// Ejemplo con base de datos MySQL
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setUrl("jdbc:mysql://localhost:3306/tfgdb");
		basicDataSource.setUsername("root");
		basicDataSource.setPassword("laravel");
		int resultado = 0;
	
		Connection con = null;
	
		String SSQL = "INSERT INTO image (title, subject, description, source, languaje, relation, coverage, creator, publisher, contributor, rights, date, type, format, identifier, hashedName) "
		            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Date date = new Date();
	    try {
	    
	        con = basicDataSource.getConnection();
	        
	        PreparedStatement psql = con.prepareStatement(SSQL);
	        psql.setString(1, title);
	        psql.setString(2, null);
	        psql.setString(3, description);
	        psql.setString(4, source);
	        psql.setString(5, null);
	        psql.setString(6, null);
	        psql.setString(7, null);
	        psql.setString(8, null);
	        psql.setString(9, null);
	        psql.setString(10, null);
	        psql.setString(11, null);
	        psql.setDate(12, null);
	        psql.setString(13, null);
	        psql.setString(14, format);
	        psql.setString(15, null);
	        psql.setString(16, hashedName);
	        
	        resultado = psql.executeUpdate();
	        
	        psql.close();
	                    
	    } catch (SQLException e) {
	    
	        JOptionPane.showMessageDialog(null, "Error al intentar almacenar la información:\n"
	                                     + e, "Error en la operación", JOptionPane.ERROR_MESSAGE);
	        
	    }finally{
	    
	        try {
	            
	            if(con!=null){
	            
	                con.close();
	                
	            }
	            
	        } catch (SQLException ex) {
	        
	            JOptionPane.showMessageDialog(null, "Error al intentar cerrar la conexión:\n"
	                                     + ex, "Error en la operación", JOptionPane.ERROR_MESSAGE);
	            
	        }
	    
	    }

	    return resultado;
	    
	}  
}
