package gorillas.server;

import javax.servlet.http.HttpServletRequest;

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
		double[] means = game.getAllMeanAngles();
		String mean = "";
		for (double m : means) {
			mean += m + " ";
		}
		mean = mean.trim();
		GameView.LOGGER.log("Player " + id + "requested the mean for stage " + stage + " and round " + round + "; sending: " + mean);
		return mean;
	}

}
