package io.frame;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 打印数据库表
 * 
 * @author fury
 *
 */
public class MybatisTablesUtils {
	/**
	 *
	 * 这里是MySQL连接方法
	 */
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://120.78.138.125:3306/happytrip?useUnicode=yes&characterEncoding=UTF8";
	private static final String userName = "root";
	private static final String passwd = "root123";
	private static Connection connection = null;

	// 可拿到新增后主键的表
	static List<String> tableNameList = new ArrayList<String>();
	static {
		tableNameList.add("s_sys_user");// 后台管理用户表
		tableNameList.add("s_sys_role");// 后台管理用户表
		tableNameList.add("u_user");// 用户表
	}

	public static void main(String[] args) {
		connection = getConnections();
		try {
			DatabaseMetaData dbmd = connection.getMetaData();
			ResultSet resultSet = dbmd.getTables(null, "%", "%", new String[] { "TABLE" });
			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				String primaryKey = "";
				// 获取主键
				ResultSet pkRSet = dbmd.getPrimaryKeys(null, null, tableName);
				while (pkRSet.next()) {
					primaryKey = pkRSet.getString("COLUMN_NAME");
				}

				String str = "<table tableName=\"" + tableName + "\" domainObjectName=\"" + transfer(tableName)
						+ "\" ><property name=\"useActualColumnNames\" value=\"true\"/><generatedKey column=\""
						+ primaryKey + "\" sqlStatement=\"MySql\"  identity=\"true\" type=\"post\"/></table>";
				String str2 = "<table tableName=\"" + tableName + "\" domainObjectName=\"" + transfer(tableName)
						+ "\" ><property name=\"useActualColumnNames\" value=\"true\"/></table>";
				ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
				String result = str2;
				while (rs.next()) {
					if (tableNameList.contains(tableName)) {
						result = str;
						continue;
					}
				}
				System.out.println(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnections() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, userName, passwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static String transfer(String str) {
		StringBuffer sb = new StringBuffer("");
		String[] arr = str.split("_");
		for (int i = 1; i < arr.length; i++) {
			String s = arr[i];
			String n = s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
			sb.append(n);
		}
		return sb.toString();
	}
}
