package gorillas;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;

public class TextSaver {
	
	private Game game;
	
	public TextSaver(Game game) {
		this.game = game;
	}
	
	public boolean save() {
		String header = "id\tip";
		
		for (int stage = 0; stage < Game.STAGE_COUNT; ++stage) {
			for (int round = 0; round <= Game.ROUND_COUNT; ++round) {
				header += "\t" + stage + ":" + round;
			}
		}
		
		String angles = header + "\n";
		String times = header + "\n";

		SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
		for (Player player : game.getAllPlayers()) {
			for (int stage = 0; stage < Game.STAGE_COUNT; ++stage) {
				for (int round = 0; round <= Game.ROUND_COUNT; ++round) {
					angles += player.getAngle(stage, round);
					times += player.getResponseReceivedTime(stage, round) != null
									? df.format(player.getResponseReceivedTime(stage, round))
									: "N/A";
					if (stage < Game.STAGE_COUNT - 1 || round < Game.ROUND_COUNT) {
						angles += "\t";
						times += "\t";
					}
				}
			}
			angles += "\n";
			times += "\n";
		}
		
		try {
			FileUtils.writeStringToFile(new File("angles.txt"), angles);
			FileUtils.writeStringToFile(new File("times.txt"), times);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
