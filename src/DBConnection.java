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

    public static void insertData(String word_target, String word_explain) throws SQLException, ClassNotFoundException {
        Connection con = connect();
        String sql = "INSERT INTO av VALUES(?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, word_target);
        ps.setString(2, word_explain);
        ps.execute();
        ps.close();
        con.close();
    }

    public static void deleteData(String word_target) throws SQLException, ClassNotFoundException {
        Connection con = connect();
        String sql = "DELETE FROM av WHERE word == ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, word_target);
        ps.execute();
        ps.close();
        con.close();
    }

    public static void updateData(String word_target, String word_explain) throws SQLException, ClassNotFoundException {
        Connection con = connect();
        String sql = "UPDATE av SET html = ? WHERE word == ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, word_explain);
        ps.setString(2, word_target);
        ps.execute();
        ps.close();
        con.close();
    }
}
