package carrillo.daniel.ImageRecognizer.TFGImageRecognizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JOptionPane;
import org.apache.commons.dbcp.BasicDataSource;

public class AccessDB {
	
	public int Guardar(String subject, String hashedName){

		//Pool metodospool = new Pool();
		BasicDataSource basicDataSource = new BasicDataSource();
		// Ejemplo con base de datos MySQL
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setUrl("jdbc:mysql://localhost:3306/tfgdb");
		basicDataSource.setUsername("root");
		basicDataSource.setPassword("laravel");
		int resultado = 0;
	
		Connection con = null;
	
		String SSQL = "UPDATE images SET subject = ? WHERE hashedName = ?";

	    try {
	    
	        con = basicDataSource.getConnection();
	        
	        PreparedStatement psql = con.prepareStatement(SSQL);
	        psql.setString(1, subject);
	        psql.setString(2, hashedName);
	        
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
