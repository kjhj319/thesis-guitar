package login;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.Database;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		ArrayList<String> information = null;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Database store = new Database();
		ArrayList<String> informationName = new ArrayList<String>();
		
		ArrayList<String> nullInput = new ArrayList<String>();
		
		nullInput = checkNull(username, password);
		
		informationName.add("Username");
		informationName.add("Password");
		informationName.add("Email");
		
		if(nullInput.size() == 0){
			
		session.setAttribute("validation","");
			try {
				store.initialize();
				information = store.getLogin(username , password);
				
				for(int i = 0; i < information.size(); i++)
					System.out.println(information.get(i));
				if(information.size() > 0){
					session.setAttribute("information",information);
					session.setAttribute("informationName",informationName);
					session.setAttribute("error","Login Successful!");
					response.sendRedirect("index.jsp");
				}
				else{
					response.sendRedirect("login.jsp");
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.sendRedirect("ErrorPage.jsp");
			}
		}
		else{
			System.out.println("There is null input");
			session.setAttribute("validation",nullInput);
			response.sendRedirect("login.jsp");
		}
	}
	
	public ArrayList<String> checkNull(String username, String password){
		ArrayList<String> ErrorList = new ArrayList<String>();
	
	try{
		if(username.equals(""))
			ErrorList.add("Username field is Empty.");
		
		if(password.equals(""))
			ErrorList.add("Password field is Empty.");
	}catch(Exception e){
		ErrorList.add("You have invalid text field");
	}
	
	return ErrorList;
	}
}
