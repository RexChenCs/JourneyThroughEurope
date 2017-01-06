package JourneyThroughEurope.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import JourneyThroughEurope.bean.GameHistory;
import JourneyThroughEurope.bean.Player;

public class SaveLoad {
    public static HashMap<String,String> townInfo;
    public static void saveGame() throws IOException {
	GameHistory game = GameHistory.getGameHistory();
	String gameStr = game.getCurrentNum() + ";" + game.getPlayerNum() + ";"
		+ game.getRemainStep() + ";" + game.getCurrentThrown() + "#";
	FileWriter writer;
	writer = new FileWriter("data/load.txt");
	writer.write(gameStr);
	for (int i = 0; i < game.getPlayerNum(); i++) {
	    String add = "#";
	    if (i == game.getPlayerNum() - 1) {
		add = "";
	    }
	    writer.write(game.getPlayers()[i].toString() + add);
	}
	writer.flush();
	writer.close();

    }

    public static boolean LoadGame() throws IOException {
	String encoding = "UTF-8";
	File file = new File("data/load.txt");
	String gameStr = "";
	if (file.isFile() && file.exists()) {
	    InputStreamReader read = new InputStreamReader(new FileInputStream(
		    file), encoding);
	    BufferedReader bufferedReader = new BufferedReader(read);
	    String lineTxt = null;
	    while ((lineTxt = bufferedReader.readLine()) != null) {
		gameStr += lineTxt;
	    }
	    read.close();
	    String gameArr[] = gameStr.split("#");
	    String gameInfo[] = gameArr[0].split(";");
	    int curNum = Integer.parseInt(gameInfo[0]);
	    int playerNum = Integer.parseInt(gameInfo[1]);
	    int remainStep = Integer.parseInt(gameInfo[2]);
	    int curThrown = Integer.parseInt(gameInfo[3]);
	    GameHistory game = GameHistory.getGameHistory();
	    game.setCurrentNum(curNum);
	    game.setIsNewGame(0);
	    game.setPlayerNum(playerNum);
	    game.setRemainStep(remainStep);
	    game.setCurrentThrown(curThrown);

	    Player[] players = new Player[playerNum];
	    for (int i = 0; i < playerNum; i++) {
		players[i] = new Player(gameArr[i + 1]);
	    }
	    game.setPlayers(players);
	} else {
	    return false;
	}
	return true;
    }

    public static void addHistory(String his) throws IOException {
	File file = new File("data/history.txt");
	if (!file.exists()) {
	    file.createNewFile();
	}
	try {
	    FileOutputStream f = new FileOutputStream("data/history.txt", true);
	    f.write((his + "\r\n").getBytes());
	    f.close();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public static List<String> getHistory() throws IOException,
	    FileNotFoundException {
	List<String> list = new ArrayList<String>();
	String encoding = "UTF-8";
	File file = new File("data/history.txt");
	if (file.isFile() && file.exists()) {
	    InputStreamReader read = new InputStreamReader(new FileInputStream(
		    file), encoding);
	    BufferedReader bufferedReader = new BufferedReader(read);
	    String lineTxt = null;
	    while ((lineTxt = bufferedReader.readLine()) != null) {
		list.add(lineTxt);
	    }
	    read.close();
	} else {
	    return null;
	}
	return list;
    }

    public static void clearHistoy() {
	File f = new File("data/load.txt");
	if (f.exists())
	    f.delete();

    }

    public static void clearLoad() {
	File f = new File("data/history.txt");
	if (f.exists())
	    f.delete();
    }
    public static String getInfo(String townName){
	for(String name : townInfo.keySet())
	{
	    if(name.equals(townName)){
		return townInfo.get(townName);
	    }
	}
	return null;
	
    }

    public static void getTownInfo() throws IOException,
	    FileNotFoundException {
	townInfo= new HashMap<String,String>();
	String encoding = "UTF-8";
	File file = new File("data/visitedtown.txt");
	if (file.isFile() && file.exists()) {
	    InputStreamReader read = new InputStreamReader(new FileInputStream(
		    file), encoding);
	    BufferedReader bufferedReader = new BufferedReader(read);
	    String lineTxt = null;
	    while ((lineTxt = bufferedReader.readLine()) != null) {
		String name = lineTxt.trim().toUpperCase();
		String info = bufferedReader.readLine().trim();
		bufferedReader.readLine();
		townInfo.put(name, info);
	    }
	    read.close();
	} 
    }
}
