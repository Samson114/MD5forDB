package dao;
//自己封装了一个简单的JDBC操作，如果需要事务在可以自己改写下把connection传出来。

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;






public class BaseConnection {
	private Connection conn;
	private PreparedStatement pstmt;
	private Statement st;
	private ResultSet rs;
	
	/**
	 * 使用静态模板来注册驱动
	 */
	static{
		//注册驱动
		try {
		     Class.forName(getPro().getProperty("driver"));
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	//取得连接
	private Connection getConn() {
		try {
			this.conn= DriverManager.getConnection(getPro().getProperty("url"),
					getPro().getProperty("user"), getPro()
					.getProperty("password"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  //取得数据链接
		return this.conn;

	}
	
	
	 /**
	  * ORMDBUtil中的方法
     * 根据传入的SQL语句返回一个对象的链表
     */
	/*public ArrayList select(String sql) throws Exception {
		ArrayList<calendar_select> result = new ArrayList<calendar_select>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				//查询的结果转化成Jobs对象
				
				calendar_select cs = new calendar_select();
				
				int id=Integer.parseInt(rs.getString("id"));
				
				int y0=Integer.parseInt(rs.getString("y0"));
				int m0=Integer.parseInt(rs.getString("m0"));
				int d0=Integer.parseInt(rs.getString("d0"));
				int y1=Integer.parseInt(rs.getString("y1"));
				int m1=Integer.parseInt(rs.getString("m1"));
				int d1=Integer.parseInt(rs.getString("d1"));
				
				
				cs.setId(id);
				cs.setTitle(rs.getString("title"));
				
				cs.setY0(y0);
				cs.setM0(m0);
				cs.setD0(d0);
				
				cs.setM1(m1);
				cs.setY1(y1);
				cs.setD1(d1);
			
				//把转换得到的对象加入ArrayList中
				result.add(cs);
			}
		} catch (SQLException sqle) {
			throw new SQLException("select data exception: "
					+ sqle.getMessage());
		} catch (Exception e) {
			throw new Exception("System e exception: " + e.getMessage());
		}finally{
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				throw new Exception("statement close exception: " + e.getMessage());
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				throw new Exception("statement close exception: " + e.getMessage());
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new Exception("connection close exception: " + e.getMessage());
			}		
		}
		return result;
	}*/
	/**
	 * prepareStatement 查询
	 * @param param
	 * @param sql
	 * @return
	 */
	public ResultSet queryForObject(final String []param,final String sql){
		
			try {
				this.pstmt = this.getConn().prepareStatement(sql);
				setParamValue(param);
				this.rs = this.pstmt.executeQuery();
			//	this.closeConn(conn, pstmt,null,rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		return this.rs;
	}
	/**
	 * statement查询
	 * 
	 */
	public ResultSet query(final String sql){
		try {
			this.st = this.getConn().createStatement();
			
			this.rs = this.st.executeQuery(sql);
//			this.closeConn(conn,null, st,rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.rs;
		
	}
	//列表查询

	public List<HashMap> queryForObjectList(final String []param,final String sql){
		  List<HashMap> list = new ArrayList<HashMap>();
	      HashMap<String, String> map = null;        
		
			try {
				this.pstmt = this.getConn().prepareStatement(sql);
				setParamValue(param);
				this.rs = this.pstmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				//rs.get
	            int cols = rsmd.getColumnCount();
	            while (rs.next()) {
	                map = new HashMap<String, String>();
	                // 将每个字段的key/value对保存到HashMap中
	                for (int i = 1; i <= cols; i++) {
	                    String field = rsmd.getColumnName(i);
	                    String value = rs.getString(i);
	                    map.put(field, value);
	                }
	                // 将当前记录添加到List中
	                list.add(map);
	            }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		return list;
	}
	//增,删,改
	public int operate(final String []param,final String sql){
		int result =0;
	
			try {
				this.pstmt =this.getConn().prepareStatement(sql);
				setParamValue(param);
				result =this.pstmt.executeUpdate();
				this.closeConn(conn, pstmt,null, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		return result;
	}
	//sql语句传参
	public void setParamValue(String []param)throws SQLException{
		if(param !=null){
			for(int i=0;i<param.length; i++){
					this.pstmt.setString(i+1, param[i]);
			}
		}
	}  
	//关闭连接,释放资源
		private void closeConn(Connection conn,PreparedStatement pstmt,Statement st,ResultSet rs) throws SQLException{
			if(rs != null){
				rs.close();
			}
			if(pstmt != null){
				pstmt.close();
			}
			if(st != null){
				st.close();
			}
			if(conn != null){
				conn.close();
			}
		}
	/**
	 * 读取配置文件jdbc.properties
	 * @return
	 */
	 public static Properties getPro(){
	   	  InputStream is =BaseConnection.class.getClassLoader().getResourceAsStream("jdbc.properties");
	   	  Properties pro = new Properties();
			   	  try {
						pro.load(is);
					} catch (IOException e) {
						e.printStackTrace();
					}finally{
							if(null!=is){
											try {
												is.close();
											} catch (IOException e) {
												e.printStackTrace();
											}
							}
					}	
			return pro;
	     
	}
}

