import java.io.IOException;

 

public class MyStaticResourceProcessor
{
    public void process(final Request request, final Response response) {
        try {
            response.sendStaticResource();
        }
        catch (IOException e) {
            Logger.logToFile("MyStaticResourceProcessor:\t" + e.toString());
        }
    }
}
