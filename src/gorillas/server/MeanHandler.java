package gorillas.server;

import javax.servlet.http.HttpServletRequest;

import gorillas.Game;
import gorillas.views.GameView;

/**
 * Handles users' requests to get the mean angle of the current round
 * @author vahan
 *
 */
public class MeanHandler extends Handler {

	public MeanHandler(HttpServletRequest request, Game game) {
		super(request, game);
	}

	/**
	 * Returns the mean angles of up to now rounds separated by a white space
	 */
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
