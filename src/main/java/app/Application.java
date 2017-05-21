package app;

import app.controller.ServiceController;
import app.db.MySQLAdapter;
import app.service.Service;
import org.sql2o.Sql2o;

import static spark.Spark.staticFileLocation;

/**
 * Created by winnerawan
 * on 5/18/17.
 */
public class Application {


    public static void init(MySQLAdapter mySQLAdapter) {

        Sql2o mysql = mySQLAdapter.getMysql();

        staticFileLocation("/public");

        Service service = new Service(mysql);

        new ServiceController(service);

    }

    public static void main(MySQLAdapter _mySQLAdapter) {
        init(_mySQLAdapter);
    }

    public static void main(String[] args) {
        init(new MySQLAdapter());
    }
}
