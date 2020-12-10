package cn.qq.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
	
	private static Connection conn ;
	private static Statement stat ;
	
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("-----获取连接数据库成功");
			e.printStackTrace();
		}
	}
	
	//获取连接
	public static Connection getConnect(String dburl) {
		dburl = "jdbc:sqlite:"+dburl;
		try {
			//return conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\HP\\Desktop\\20201209_01\\Immsg.db");
			return conn = DriverManager.getConnection(dburl);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//关流
	public static void close(ResultSet rs,Statement stat,Connection conn) {
    	try {
    		if(rs != null) {
    			rs.close();
    		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
    		if(stat != null) {
    			stat.close();
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
    	try {
    		if(conn != null) {
    			conn.close();
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
        
        
    }
	
	public static void main(String[] args) throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\hp\\Desktop\\aa\\Immsg.db");
        Statement stat = conn.createStatement();
        String sql = "select * from qim_Im_msg";
        ResultSet rs = stat.executeQuery(sql);
        while(rs.next()) {
        	String immsg = rs.getString("immsg");
        	System.out.println(immsg);
        }
        
        
        
    }
}
