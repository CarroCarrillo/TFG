package carrillo.daniel.ImageRecognizer.TFGImageRecognizer;

import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
    	List<String> resultList = ImagesRecognizer.recognize("C:/Users/Usuario/workspace/TFG/TFGMaven/data/1334c109-d797-448f-a524-553b0bf31f67.jpg");
        // Iteration of Result
        for(String result : resultList) {
        	System.out.println(result);
        }
    }
}
