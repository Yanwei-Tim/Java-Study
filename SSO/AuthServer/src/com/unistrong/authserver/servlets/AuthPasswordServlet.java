package com.unistrong.authserver.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unistrong.authserver.common.*;
import com.unistrong.authserver.db.DBConnector;
import com.unistrong.authserver.db.UserDao;

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
		
		UserDao dao = new UserDao();
		boolean ret =  dao.checkLogin(userid,  RandomUtils.MD5(password));
		if (ret){
			response.setHeader(Constants.AUTH_PARAM_CODE, String.valueOf(0));
			String ticket = HttpCodec.encodeHttp(RandomUtils.generateToken(userid));
			// д�����ݿ�
			

			response.setHeader(Constants.AUTH_PARAM_DATA, ticket);
			response.setHeader(Constants.AUTH_PARAM_MESSAGE, HttpCodec.encodeHttp("��½�ɹ�"));
		}else{
			response.setHeader(Constants.AUTH_PARAM_CODE, String.valueOf(-1));
			response.setHeader(Constants.AUTH_PARAM_MESSAGE,  HttpCodec.encodeHttp("�û����������"));
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
