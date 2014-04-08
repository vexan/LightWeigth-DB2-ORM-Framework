package ru.btconsulting.orm.db2.classes;

import ru.btconsulting.orm.db2.annotations.Database;
import ru.btconsulting.orm.db2.annotations.LazyLoad;
import ru.btconsulting.orm.db2.annotations.User;

import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * Created by apple on 08.04.14.
 */
@Database(server = "127.0.0.1", port = 50000)
@User(name = "db2admin" , password = "db2adminpwd")
public class ConnectionManager {
    private ConnectionPool pool = ConnectionPool.getInstance();
    private boolean initialized = false;

    public ConnectionManager() {
        try {
            Method method = this.getClass().getDeclaredMethod("init");
            LazyLoad lzLoad = method.getAnnotation(LazyLoad.class);
            if (!lzLoad.value()) {
                System.out.println("Initialize on start");
                init();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @LazyLoad(true)
    private void init() {
        Database databaseAnnotation = this.getClass().getAnnotation(Database.class);
        User userAnnotation = this.getClass().getAnnotation(User.class);

        if (databaseAnnotation != null && userAnnotation != null) {
            pool.addConnection(createConnection(databaseAnnotation.server(), databaseAnnotation.port(), userAnnotation.name(), userAnnotation.password()));

            System.out.println("Server:"+databaseAnnotation.server());
            System.out.println("port:"+databaseAnnotation.port());
            System.out.println("username:"+userAnnotation.name());
            System.out.println("password:"+userAnnotation.password());
        }

        initialized = true;
    }

    public Connection getConnection() {
        if (!initialized) init();

        return pool.getConnection();
    }

    private Connection createConnection(String dbName, int port, String username, String password) {
        return null;
    }
}
