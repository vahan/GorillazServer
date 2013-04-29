package server;

import game.Game;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.*;
import javax.servlet.http.*;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.AbstractHandler;


public class GorillasHandler extends AbstractHandler {
	
	private Handler _handler;
	private Game _game;
	
	public GorillasHandler(Game game) {
		_game = game;
	}
	
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) 
	        throws IOException, ServletException
    {
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        
        _handler = getHandler(request);
        if (_handler == null) {
        	response.getWriter().print("NO");
        	return;
        }
        /*if (!_handler.validate()) {
        	System.out.println("Validation ERROR!");
        	response.getWriter().println("NO");
        	response.getWriter().flush();
        	return;
        }*/
        
        String resp = _handler.handle();
        if (resp == null) return;
        System.out.println("response: " + resp);
        response.getWriter().println(resp);
        System.out.println("Response sent: " + Arrays.toString(response.getHeaderNames().toArray()));
        for (String header : response.getHeaderNames()) {
        	System.out.println(header + ": " + response.getHeader(header));
        }
    }
	
	
	
	private Handler getHandler(HttpServletRequest request) {
		String type = request.getParameter("request");
		if (type == null)
			return null;
		switch (type) {
			case "authenticate":	return new AuthenticationHandler(request, _game);
			case "angle":			return new AngleHandler(request, _game);
			case "mean":			return new MeanHandler(request, _game);
			default:				return null;
		}
	}
	
	
	
}
