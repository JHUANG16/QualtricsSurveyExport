/**
 * Created by Junjie on 6/20/17.
 */

public class QualtricsIntegration {
    public static void main(String[] args){
        ExportClient ec = new ExportClient();
        try{
            ec.Start();
        }catch (Exception ex){
            System.out.println("Error");
        }
    }
}

