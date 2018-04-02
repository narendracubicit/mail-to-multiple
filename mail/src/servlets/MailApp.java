package servlets;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/MailApp")
public class MailApp extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    
    {
    	Connection con=null;
    	PreparedStatement pst=null;
    	ResultSet rs=null;
    	
    	
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
 
		 try {
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","narendra","narendra");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
 
		 try {
			pst=con.prepareStatement("select * from mail");
			/**
			 *  CREATE TABLE  "MAIL" (	"MAILID" VARCHAR2(4000) )
			 *  insert some mail ids 
			 */
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs=pst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<String> mails=new ArrayList<String>();
   	 try {
		while(rs.next())
		 {
			 mails.add(rs.getString("mailid"));
		 }
	} 
   	 catch (SQLException e) 
   	 {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        //String to = request.getParameter("to");
        String subject = request.getParameter("subject");
        String message =  request.getParameter("message");
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        
        SendMail.send(mails,subject, message, user, pass);
        out.println("Mail send successfully");
    }   
}
