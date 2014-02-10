package com.unistrong.authserver.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unistrong.authserver.common.Constants;
import com.unistrong.authserver.common.HttpCodec;
import com.unistrong.authserver.common.RandomUtils;
import com.unistrong.authserver.db.UserDao;

//import net.sf.json.JSONObject;

/**
 * Servlet implementation class AuthServlet
 */
public class AuthTicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthTicketServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// userid=admin&ticket=123456
		String userid = request.getParameter(Constants.AUTH_PARAM_UNAME);
		String ticket = request.getParameter(Constants.AUTH_PARAM_TICKET);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain; charset=utf-8");
		
		UserDao dao = new UserDao();
		java.util.Date ticketTime = dao.checkTicket(userid, ticket);
		if (ticketTime != null){
			java.util.Date expireTime = new java.util.Date(ticketTime.getTime() +604800000/* 7*24*3600*1000*/);
			java.util.Date curTime = new java.util.Date();
			if (curTime.after(expireTime)){
				response.setHeader(Constants.AUTH_PARAM_CODE, String.valueOf(-1));
				response.setHeader(Constants.AUTH_PARAM_MESSAGE, HttpCodec.encodeHttp("票据已过期"));
			}else{
				response.setHeader(Constants.AUTH_PARAM_CODE, String.valueOf(0));
				response.setHeader(Constants.AUTH_PARAM_MESSAGE, HttpCodec.encodeHttp("票据验证成功"));
			}
		}else{
			response.setHeader(Constants.AUTH_PARAM_CODE, String.valueOf(-1));
			response.setHeader(Constants.AUTH_PARAM_MESSAGE,  HttpCodec.encodeHttp("票据错误"));
		}
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();

//		JSONObject json = new JSONObject();
//		PrintWriter out = response.getWriter();
//		
//		if (userid.equals("admin") && ticket.equals("123456")){
//			json.put("code", 0);
//			json.put("msg", "登陆成功");
//		}else{
//			json.put("code", -1);
//			json.put("msg", "用户名密码错误");
//		}
//		out.print(json.toString());
//		out.flush();
//		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
