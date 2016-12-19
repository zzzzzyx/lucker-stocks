package cn.edu.nju.luckers.webserver.main.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.luckers.database_getter_interface.common.RMIService;
import cn.edu.nju.luckers.database_getter_interface.service.login.RegisterAndLoginService;
import cn.edu.nju.luckers.database_getter_interface.service.login.po.UserRegisterInfo;
import net.sf.json.JSONObject;

public class ResigterServlet extends HttpServlet {

	/**
	 * 注册验证的servlet
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=gbk");
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();

		String name = req.getParameter("userName");
		String email = req.getParameter("userEmail");
		String password1 = req.getParameter("password1");
		String password2 = req.getParameter("password2");

		System.out.println(name);
		System.out.println(email);
		System.out.println(password1);
		System.out.println(password2);

		boolean isSame = false;
		if (password1.equals(password2)) {
			isSame = true;
		}

		System.out.println(isSame);
		if (isSame) {
			RegisterAndLoginService service = null;
			try {
				service = (RegisterAndLoginService) RMIService.getInstance(RegisterAndLoginService.class);

				if (service.register(new UserRegisterInfo(name, password1, email))) {
					json.put("state", "true");
					json.put("remind", "success");
				} else {
					json.put("state", "false");
					json.put("remind", "账户已存在");
				}

			} catch (Exception e) {

			}
		} else {
			json.put("state", "false");
			json.put("remind", "两次密码不一致");
		}
		out.println(json);
		out.flush();
		out.close();
	}

}
