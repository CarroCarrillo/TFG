package carrillo.daniel.ImageRecognizer.TFGImageRecognizer;

import java.io.File;
import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
    	File dir = new File("C:/Users/Usuario/workspace/TFG/TFGMaven/data/");
    	File listFile[] = dir.listFiles();
    	for (File item : listFile) {
    		String coverage = "";
    	    System.out.println(item.getPath());
	    	List<String> resultList = ImagesRecognizer.recognize(item.getPath());
	       
	    	// Iteration of Result
	        for(String result : resultList) {
	        	coverage += result + "/";
	        }
	        //Las imágenes .ico me dan error
	        AccessDB ac = new AccessDB();
	        ac.Guardar(coverage, item.getName());
	    }
    }
}
