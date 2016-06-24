package Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.Database;

/**
 * Servlet implementation class AllTabsServlet
 */
@WebServlet("/AllTabsServlet")
public class AllTabsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllTabsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Database store = new Database();
		ArrayList<HashMap<String, String>> tabs = new ArrayList<>();
		String order = "asc";
		String change;
		
		try{
			order = session.getAttribute("order").toString();
			change = request.getParameter("change");
			if(change != null){
				if(order.equals("asc")){
					order = "desc";
				}
				else if(order.equals("desc")){
					order = "asc";
				}
			}
		}catch(Exception e){
			order = "asc";
		}
		
		try {
			store.initialize();
			
			tabs = store.getAllTabs(order);
			session.setAttribute("tabs", tabs);
			session.setAttribute("order", order);
			session.setAttribute("pages", Math.ceil(Float.valueOf(tabs.size()/10)));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect("all.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
