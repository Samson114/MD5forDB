package servlet;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Md5Encrypt;
import dao.BaseConnection;

/**
 * Servlet implementation class Add
 */
public class Add extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Add() {
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
		System.out.println("Add");
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		System.out.println("username="+username);
		System.out.println("password="+password);
		
		String md5_password=null;
//		将密码加密后 存到数据库中
		Md5Encrypt md = new Md5Encrypt();
		md5_password = md.Encrypt(password);
		System.out.println("md5="+md5_password);
		
		
		BaseConnection bc=new BaseConnection();
		
		ResultSet rs=null;
		String param[]={username,md5_password};
		//String sql="select * from test where username="+username+" and password='"+md5_password+"'";
		String sql="insert into information (username,password) values (?,?) ";

		bc.operate(param, sql);
	}

}
