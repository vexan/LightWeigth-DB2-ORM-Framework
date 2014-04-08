package ru.btconsulting.orm.db2.classes;

import ru.btconsulting.orm.db2.annotations.Database;
import ru.btconsulting.orm.db2.annotations.Server;
import ru.btconsulting.orm.db2.annotations.LazyLoad;
import ru.btconsulting.orm.db2.annotations.User;

import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * Created by apple on 08.04.14.
 */
@Server(server = "127.0.0.1", port = 50000)
@Database(name = "DBUsers")
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
        Server serverAnnotation = this.getClass().getAnnotation(Server.class);
        Database databaseAnnotation = this.getClass().getAnnotation(Database.class);
        User userAnnotation = this.getClass().getAnnotation(User.class);

        if (serverAnnotation != null && databaseAnnotation!=null && userAnnotation != null) {
            pool.addConnection(createConnection(serverAnnotation.server(), serverAnnotation.port(), databaseAnnotation.name(), userAnnotation.name(), userAnnotation.password()));

            System.out.println("Server:"+serverAnnotation.server());
            System.out.println("port:" + serverAnnotation.port());
            System.out.println("DBName:" + databaseAnnotation.name());
            System.out.println("username:"+userAnnotation.name());
            System.out.println("password:"+userAnnotation.password());
        }

        initialized = true;
    }

    public Connection getConnection() {
        if (!initialized) init();

        return pool.getConnection();
    }

    private Connection createConnection(String server, int port, String dbName, String username, String password) {
        return null;
    }
}
