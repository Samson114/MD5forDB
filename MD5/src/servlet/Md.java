package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Md5Encrypt;

import dao.BaseConnection;

/**
 * Servlet implementation class md
 */
public class Md extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Md() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("md");
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		System.out.println("username="+username);
		System.out.println("password="+password);
		
		String md5_password=null;
//		将从前台取到的数据加密  以后跟数据库中的数据比较    
		Md5Encrypt md = new Md5Encrypt();
		md5_password = md.Encrypt(password);
		System.out.println("md5="+md5_password);
		
		
		BaseConnection bc=new BaseConnection();
		
		ResultSet rs=null;
		String param[]={username,md5_password};
		//String sql="select * from test where username="+username+" and password='"+md5_password+"'";
		String sql="select * from information where username=? and password=?";

		rs=bc.queryForObject(param, sql);
		
		String url=null;

		
		try {
			if(rs.next()){				
				url="success.jsp";
			}
			else{
				url="error.jsp";
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect(url);
	
		System.out.println("结束md");
	}

}
