package com.aaronmegs.helloweblogic.config;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * weblogic 中 需要将controller放到 context config 配置的的目录下才能识别到  - weblogic 12c.2.1.1.0 生产模式下
 * @author aaronmegs
 * @create 2021/10/26
 */
@RestController
public class TestController {

    @GetMapping("/ping")
    public String ping() {
        return "Hello Weblogic!";
    }

    @RequestMapping("/welcome")
    public String welcome(@RequestParam(name = "username") String username) {
        return "welcome:" + username;
    }

    @PostMapping("/oracle")
    public Object getOracle(@RequestBody OracleModel oracleModel) {
        String sid = StringUtils.isEmpty(oracleModel.getSid()) ? "xe" : oracleModel.getSid();
        String oracleUrl = "jdbc:oracle:thin:@" + oracleModel.getUrl() + ":" + oracleModel.getPort() + ":" + sid;
        Connection connection = null;
        String sql = "SELECT * FROM (\n" +
                "         SELECT t.*, ROWID\n" +
                "         FROM SYS.USER$ t\n" +
                "     ) WHERE ROWNUM <= 501";
        List<String> userLis = new ArrayList<>();
        ResultSet resultSet = null;
        PreparedStatement ps = null;
        try {
            connection = OperateOracle.getConnection(oracleUrl, oracleModel.getUsername(), oracleModel.getPassword());
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                userLis.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "执行查询数据库用户异常: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "执行查询数据库用户异常: " + e.getMessage();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return userLis;
    }

//    public static void main(String[] args) {
//        Connection connection = OperateOracle.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "system", "oracle");
////        String sql = "select count(*) from USER_TABLES";
//        String sql = "SELECT * FROM (\n" +
//                "         SELECT t.*, ROWID\n" +
//                "         FROM SYS.USER$ t\n" +
//                "     ) WHERE ROWNUM <= 501";
//        try {
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ResultSet resultSet = ps.executeQuery();
//            while (resultSet.next()) {
//                String name = resultSet.getString("NAME");
//                String password = resultSet.getString("PASSWORD");
//                System.out.println(name + " : " + password);
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }

}
