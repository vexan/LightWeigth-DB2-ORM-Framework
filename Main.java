import ru.btconsulting.orm.db2.classes.ConnectionManager;

import java.sql.Connection;

/**
 * Created by apple on 08.04.14.
 */
public class Main {
    public static void main(String args[]) {
        ConnectionManager cMgnr = new ConnectionManager();
        Connection connection = cMgnr.getConnection();
    }
}
