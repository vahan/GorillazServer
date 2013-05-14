package gorillas.server;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import gorillas.Game;
import gorillas.views.GameView;

public class MeanHandler extends Handler {

	public MeanHandler(HttpServletRequest request, Game game) {
		super(request, game);
	}

	@Override
	public String handle() {
		int stage = Integer.parseInt(request.getParameter("stage"));
		int round = Integer.parseInt(request.getParameter("round"));
		int id = Integer.parseInt(request.getParameter("id").trim());
		String mean = StringUtils.join(game.getAllMeanAngles(), " ");
		GameView.LOGGER.log("Player " + id + "requested the mean for stage " + stage + " round " + round + " and recieved " + mean);
		return mean;
	}

}
