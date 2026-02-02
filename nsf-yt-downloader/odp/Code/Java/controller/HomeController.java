package controller;

import jakarta.mvc.Controller;
import jakarta.mvc.View;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Controller
@Path("")
public class HomeController {
	@GET
	@View("home.jsp")
	public void home() {
		
	}
}
