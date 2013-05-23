package gorillas.server;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.AbstractHandler;

import gorillas.Game;

/**
 * Custom handler for jetty server
 * @author vahan
 *
 */
public class GorillasHandler extends AbstractHandler {
	
	private Handler handler;
	private Game game;
	
	public GorillasHandler(Game game) {
		this.game = game;
	}
	
	/**
	 * Receives the user requests, chooses the appropriate handler, 
	 * passer the request to it and returns the resulting response to the user
	 */
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) 
	        throws IOException, ServletException
    {
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        
        handler = getHandler(request);
        if (handler == null) {
        	response.getWriter().print("ERROR: Wrong request!");
        	return;
        }
        
        String resp = handler.handle();
        if (resp == null) return;
        response.getWriter().println(resp);
    }
	
	/**
	 * Gets the appropriate handler based on the request type
	 * @param request
	 * @return
	 */
	private Handler getHandler(HttpServletRequest request) {
		String paramId = request.getParameter("id");
		if (paramId != null && !paramId.isEmpty()) {
			int id = Integer.parseInt(paramId.trim());
			if (!game.hasActivePlayer(id)) {
				return null;
			}
		}
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
