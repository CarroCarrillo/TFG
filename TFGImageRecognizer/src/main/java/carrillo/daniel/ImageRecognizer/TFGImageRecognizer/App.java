package carrillo.daniel.ImageRecognizer.TFGImageRecognizer;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

public class App 
{
    public static void main( String[] args )
    {
    	File dir = new File("C:/Users/Usuario/workspace/TFG/TFGMaven/data/");
    	File listFile[] = dir.listFiles();
    	for (File item : listFile) {
    		String subject = "";
    		String path = item.getPath();
    	    System.out.println(path);
    	    String ext = FilenameUtils.getExtension(path); 
    	    System.out.println(ext);
    	    if(!ext.equals("ico") && !ext.equals("db") && !ext.equals("")){
		    	List<String> resultList = ImagesRecognizer.recognize(path);
		       
		    	// Iteration of Result
		        for(String result : resultList) {
		        	subject += result + "/";
		        }
		        //Las imágenes .ico me dan error
		        AccessDB ac = new AccessDB();
		        ac.Guardar(subject, item.getName());
    	    }
	    }
    	System.out.println("Terminado");
    }
}
