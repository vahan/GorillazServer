package server;

import game.Game;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.AbstractHandler;


public class GorillasHandler extends AbstractHandler {
	
	private Handler handler;
	private Game game;
	
	public GorillasHandler(Game game) {
		this.game = game;
	}
	
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) 
	        throws IOException, ServletException
    {
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        
        handler = getHandler(request);
        if (handler == null) {
        	response.getWriter().print("ERROR: handler is null!");
        	return;
        }
        /*if (!_handler.validate()) {
        	System.out.println("Validation ERROR!");
        	response.getWriter().println("NO");
        	response.getWriter().flush();
        	return;
        }*/
        
        String resp = handler.handle();
        if (resp == null) return;
        System.out.println("response: " + resp);
        response.getWriter().println(resp);
        /*System.out.println("Response sent: " + Arrays.toString(response.getHeaderNames().toArray()));
        for (String header : response.getHeaderNames()) {
        	System.out.println(header + ": " + response.getHeader(header));
        }*/
    }
	
	
	private Handler getHandler(HttpServletRequest request) {
		String type = request.getParameter("request");
		if (type == null)
			return null;
		switch (type) {
			case "authenticate":	return new AuthenticationHandler(request, game);
			case "angle":			return new AngleHandler(request, game);
			case "mean":			return new MeanHandler(request, game);
			case "next":			return new NextHandler(request, game);
			default:				return null;
		}
	}
	
	
	
}
