import java.sql.*;

public class DBConnection {
    private static final String DB_URL = "jdbc:sqlite:src/dict_hh.db";

    public static Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection(DB_URL);
        return con;
    }

    public static void readAllData() throws SQLException, ClassNotFoundException {
        Connection con = connect();
        String sql = "SELECT * FROM av";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String word_target = rs.getString("word");
            String word_explain = rs.getString("html");
            Dictionary.dic.put(word_target, word_explain);
        }
        rs.close();
        ps.close();
        con.close();
    }
}
