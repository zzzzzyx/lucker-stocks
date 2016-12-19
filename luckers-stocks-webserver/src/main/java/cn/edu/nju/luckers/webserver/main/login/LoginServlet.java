package cn.edu.nju.luckers.webserver.main.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.luckers.database_getter_interface.common.RMIService;
import cn.edu.nju.luckers.database_getter_interface.service.login.RegisterAndLoginService;
import cn.edu.nju.luckers.database_getter_interface.service.login.po.UserIdentity;
import net.sf.json.JSONObject;

public class LoginServlet extends HttpServlet {

	/**
	 * 登陆验证servlet
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String username = req.getParameter("loginname");
		String password = req.getParameter("loginpassword");
 
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();

		// 获得 RMI的远程调用对象
		RegisterAndLoginService connectionService = null;
		try {
			connectionService = (RegisterAndLoginService) RMIService.getInstance(RegisterAndLoginService.class);
			if (connectionService.verify(new UserIdentity(username, password))) {
				json.put("remind", "true");
			} else {
				json.put("remind", "false");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		out.println(json);
		out.flush();
		out.close();

	}

}
