package ssh;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by YotamG on 31/05/2017.
 */
public final class ClientConfiguration {

    private static final Logger log = LogManager.getLogger();

    public static final String SESSION_TIMEOUT="SET";
    public static final String PRINT_STD_OUT="PRO";
    public static final String COMMAND_TIMEOUT="CMD";


    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty(ClientConfiguration.SESSION_TIMEOUT,"1000");
        properties.setProperty(ClientConfiguration.COMMAND_TIMEOUT,"4000");

        System.out.println(properties);

    }



}
