package ru.netology.web.data;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestJava {

    @Value
    public static class DataBaseConn {
        String url;
        String user;
        String password;
    }

    public static DataBaseConn getDataBaseConn() {
        return new DataBaseConn("jdbc:mysql://192.168.99.100:3306/app", "app", "123");
    }

    public static BigDecimal getSalaryForDepartment(String department) {
        BigDecimal sum = null;
        try {
            val salarySumSQL = "SELECT SUM(SALARY) FROM EMPLOYEES WHERE DEPARTMENT='" + department + "'";
            val runner = new QueryRunner();
            val conn = DriverManager.getConnection(
                    getDataBaseConn().url, getDataBaseConn().user, getDataBaseConn().password
            );
            val sumForDepartment = runner.query(conn, salarySumSQL, new ScalarHandler<>());
            sum = (BigDecimal) sumForDepartment;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println("Development: " + getSalaryForDepartment("Development"));
        System.out.println("Sales: " + getSalaryForDepartment("Sales"));
        System.out.println("Management: " + getSalaryForDepartment("Management"));

    }

}
