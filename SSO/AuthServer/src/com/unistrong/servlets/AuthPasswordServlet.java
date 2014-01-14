package com.unistrong.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.unistrong.common.*;

//import net.sf.json.JSONObject;

/**
 * Servlet implementation class AuthServlet
 */
public class AuthPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthPasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// userid=admin&password=123456
		String userid = request.getParameter(Constants.AUTH_PARAM_UNAME);
		String password = request.getParameter(Constants.AUTH_PARAM_PASSWORD);
		
		response.setCharacterEncoding("utf-8");   
		response.setContentType("text/plain; charset=utf-8");
		if (userid.equals("admin") && password.equals("123456")){
			response.setHeader(Constants.AUTH_PARAM_CODE, String.valueOf(0));
			response.setHeader(Constants.AUTH_PARAM_DATA, HttpCodec.encodeHttp(RandomUtils.generateToken(userid)));
			response.setHeader(Constants.AUTH_PARAM_MESSAGE, HttpCodec.encodeHttp("登陆成功"));
		}else{
			response.setHeader(Constants.AUTH_PARAM_CODE, String.valueOf(-1));
			response.setHeader(Constants.AUTH_PARAM_MESSAGE,  HttpCodec.encodeHttp("用户名密码错误"));
		}
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
