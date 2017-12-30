import network.db.Mysql;

import java.sql.ResultSet;
import java.sql.Statement;

public class TestMysql {
    private static Mysql mysql = null;
    public static void main(String args[]) {
        try
        {
//            mysql = new Mysql();
            Statement stmt = mysql.getStatement();
            String query = "select * from t_user";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int uid = rs.getInt("uid");
                String name = rs.getString("name");
                String mobile = rs.getString("mobile");
                int age = rs.getInt("age");
                String idcard = rs.getString("idcard");
                System.out.println(uid +", " + name +", " + mobile +", " + age +", " + idcard);
            }
            Thread.sleep(3000);
            mysql.closeConnection();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
