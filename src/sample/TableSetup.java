package sample;

import java.sql.*;

public abstract class TableSetup {
    protected Connection con;

   public void createTable(String sqlString) throws SQLException, ClassNotFoundException {
        Statement statement = con.createStatement();

        String sql = sqlString;

        statement.execute(sql);  // Om tabellen inte finns så skapas den
    }

    public abstract ResultSet getResult() throws SQLException, ClassNotFoundException ;

    //public abstract void FillTableFromFile(List csvLan) throws SQLException, ClassNotFoundException;

    public Connection getConnection(String databasename)throws SQLException
    {
        return DriverManager.getConnection(databasename);
    }
}
