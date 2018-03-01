import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Get {
    public static void get(String url){
        try{
            //Create connection
            URL board = new URL(url);
            HttpURLConnection c = (HttpURLConnection) board.openConnection();
            int responseCode = c.getResponseCode();
        }catch (Exception e){
            System.out.println("error");
        }
    }
}
