import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/see_grades")
public class Show_grades extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Show_grades() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public String calculate(double grade_points){
    	String grade_letter = "";
    	double gpa  = 0;
    	
    	if( grade_points > 90){
    		grade_letter = "A";
    		gpa = 4;
    	}else if( grade_points > 80){
    		grade_letter = "B";
    		gpa = 3;
    	}else if( grade_points > 70){
    		grade_letter = "C";
    		gpa = 2;
    	}else if( grade_points > 60){
    		grade_letter = "D";
    		gpa = 1;
    	}else {
    		grade_letter = "F";
    		gpa = 0;
    	}
    	return gpa + "" + grade_letter;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("text/html");
		
		String url = "jdbc:oracle:thin:testuser/password@localhost"; 
		//properties for creating connection to Oracle database
		Properties props = new Properties();
		
		props.setProperty("user", "testdb");
		props.setProperty("password", "password");
		
		//creating connection to Oracle database using JDBC
		Connection conn;
		
		PreparedStatement preStatement1,preStatement2;
		String sql_command2;
		String sql_command = "select distinct class from STRONGHEIM_TABLE";

		String Store_table = "";
		
		
		Store_table += "<div class=\"container-fluid\"> <table class=\"table table-striped table-bordered\">";
		Store_table += "<br><br><br><br><br><br><br><br><br>"
				+ "<h3>"  + "Grades for Student ID:" 
				+ request.getParameter("studentID") + "</h3>";
		Store_table += "<thread>";
		Store_table += "<tr>"; 
		Store_table += "<th> Course </th>";   
		Store_table += "<th> Grade </th>"; 
		Store_table += "</tr>";
		Store_table += "</thread>";
		Store_table +=  "<tbody>" ;
		
		double sum = 0,max = 0,min = 100, average = 0, gpa = 0,sum_gpa = 0;
		double cumGPA = 0;
		int count = 0;
		
	
		try{
				conn = DriverManager.getConnection(url,props);
				preStatement1 = conn.prepareStatement(sql_command);
				ResultSet result1 = preStatement1.executeQuery();
				ResultSet result2 ;
				
				System.out.println(sql_command);
			    while(result1.next()){
			    	  sum = 0;
			    	  count = 0;
			    	  
			    	 sql_command2 = "select  * from STRONGHEIM_TABLE where id = " +  request.getParameter("studentID") 
			    	 + " and class = '" + result1.getString("class") + "'";
			    	 System.out.println(sql_command2);
			    	preStatement2 = conn.prepareStatement(sql_command2);
					result2 = preStatement2.executeQuery();
					
					while(result2.next()){
						//Count number of results for determining GPA
				    	count++;
				    	sum += result2.getDouble("Grade_points");
				    	sum_gpa += result2.getDouble("GPA");
					}
					average = (count == 0) ? 0: sum/count;
				    
				    int size = calculate(average).length();
					double grade_points = average;
					String grade_letter = calculate(grade_points).substring(size - 1);
					
				    gpa += (count == 0) ? 0: sum_gpa/count;
				    
				    
				    System.out.println(gpa);
				    if( count != 0){
				    	Store_table +=  "<tr>";

						Store_table += "<td>" + result1.getString("class") +"</td>"; 
						Store_table += "<td>" + grade_letter +"</td>"; 
						
						Store_table +=  "</tr> <br/>";
				    }else{
				    	Store_table +=  "<tr>";

						Store_table += "<td>" + result1.getString("class") +"</td>"; 
						Store_table += "<td>" + "W" +"</td>"; 
						
						Store_table +=  "</tr> <br/>";
				    }
				    
			    	
			    }
			    
//			    preStatement = conn.prepareStatement(sql_command2);
//				result = preStatement.executeQuery();
//				 while(result.next()){
//			    	if(result.getDouble("Grade") > max){
//			    		max = result.getDouble("Grade");
//			    	}
//			    	
//			    	if(result.getDouble("Grade") < min){
//			    		min = result.getDouble("Grade");
//			    	}
					Store_table +=  "<tr>";

//					Store_table += "<td>" + result.getString("class") +"</td>"; 
//					Store_table += "<td>" + grade_letter +"</td>"; 
//					
//					Store_table +=  "</tr> <br/>";
//			    }
			    //average = sum/count;
			    
			    Store_table += "</tbody> </table> </div>";
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
		
		request.setAttribute("grades",Store_table);
		request.setAttribute("GPA",(" " + gpa).format("%.2f", gpa) );
		getServletContext().getRequestDispatcher("/Student_Grades.jsp").forward(request, response);
	

	  } 
		
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
