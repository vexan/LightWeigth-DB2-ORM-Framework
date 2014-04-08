package ru.btconsulting.orm.db2.classes;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by apple on 08.04.14.
 */
public class ConnectionPool {
    private static ArrayList<Connection> pool = new ArrayList<Connection>();
    private static ConnectionPool instance = null;

    private ConnectionPool() {

    }

    public static ConnectionPool getInstance() {
        if (instance == null) instance = new ConnectionPool();
        return instance;
    }

    public void addConnection(Connection connection) {
        pool.add(connection);
    }

    public void remove(Connection connection) {
        pool.remove(connection);
    }

    public Connection getConnection() {
        return pool.get(0);
    }

    public boolean isEmpty() {
        return pool.isEmpty();
    }
}
