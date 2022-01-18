package Callcenter;

import java.sql.*;


import java.util.*;




public class CallCenter{
	//db connection
	static Connection connection =null;
	static String databaseName= "callcenter";
	static String url= "jdbc:mysql://localhost:3306/"+databaseName;
	
	static String username ="root";
	static String password= "sql357";
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			connection = DriverManager.getConnection(url,username, password);
			System.out.println("    Connected   ");
			System.out.println();
			System.out.println("1.Hour of the day when the call volume is highest.");
			System.out.println("2.Hour of the day when the calls are longest.");
			System.out.println("3.Day of the week when the call volume is highest.");
			System.out.println("4.Day of the week when the calls are longest.");
			while(true) {
			Scanner sc = new Scanner(System.in);
			System.out.println();
			System.out.print("choose the number : ");
			int a= sc.nextInt();
			System.out.println();
			if(a==1) {
				Statement stmt = null; 
				stmt = connection.createStatement();
				String sql = "select hour from (select date_format(Start_Time,'%H') as hour from calldatabase) calldatabase \r\n"
						+ "group by hour \r\n"
						+ "order by count(hour) desc limit 1;";
				ResultSet rs = stmt.executeQuery(sql); 
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				 while (rs.next()) {
				       for (int i = 1; i <= columnsNumber; i++) {
				           if (i > 1) System.out.print(",  ");
				           String columnValue = rs.getString(i);
				           System.out.print(columnValue + " " + rsmd.getColumnName(i));
				       }
				       System.out.println("");
				   }
			}
			else if (a==2) {
				Statement stmt = null; 
				stmt = connection.createStatement();
				String sql = "select hour from (select date_format(Start_Time,'%H') as hour,avg(Duration)  from calldatabase group by hour) calldatabase limit 1";
				ResultSet rs = stmt.executeQuery(sql); 
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				 while (rs.next()) {
				       for (int i = 1; i <= columnsNumber; i++) {
				           if (i > 1) System.out.print(",  ");
				           String columnValue = rs.getString(i);
				           System.out.print(columnValue + " " + rsmd.getColumnName(i));
				       }
				       System.out.println("");
				   }
			}
			else if (a==3) {
				Statement stmt = null; 
				stmt = connection.createStatement();
				String sql = "select week(date1) as Week,date1 from\r\n"
						+ "( SELECT week (date1),date1,count(date1)FROM \r\n"
						+ "( select date_format(Start_Time,'%Y-%m-%d') as date1 from calldatabase ) \r\n"
						+ "calldatabase GROUP BY date1 ORDER BY count(date1) desc) calldatabase GROUP BY week(date1) ORDER BY count(date1) desc";
				ResultSet rs = stmt.executeQuery(sql); 
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				 while (rs.next()) {
				       for (int i = 1; i <= columnsNumber; i++) {
				           if (i > 1) System.out.print(",  ");
				           String columnValue = rs.getString(i);
				           System.out.print(columnValue + " " + rsmd.getColumnName(i));
				       }
				       System.out.println("");
				   }
			}
			else if (a==4) {
				Statement stmt = null; 
				stmt = connection.createStatement();
				String sql = "select week (date1) as Week, date1,max(dur) as Avgduration from \r\n"
						+ "(select week(date1),date1,avg(Duration) as dur from (select date_format(Start_Time,'%Y-%m-%d') as date1,Duration from calldatabase) calldatabase group by date1) \r\n"
						+ "calldatabase group by week(date1) order by dur desc";
				ResultSet rs = stmt.executeQuery(sql); 
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				 while (rs.next()) {
				       for (int i = 1; i <= columnsNumber; i++) {
				           if (i > 1) System.out.print(",  ");
				           String columnValue = rs.getString(i);
				           System.out.print(columnValue + " " + rsmd.getColumnName(i));
				       }
				       System.out.println("");
				   }
			}
			else {
				System.out.println("enter only Above given numbers.");
			}
			}
			}
		catch (Exception e) {
			System.out.println(e);
		}
	
	} 
}

