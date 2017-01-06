package JourneyThroughEurope.bean;

public class GameHistory {
    Player[] players;
    int playerNum;
    int currentNum;
    int remainStep;
    int currentThrown;
    int isNewGame;
    /**
     * @return the isNewGame
     */
    public int getIsNewGame() {
        return isNewGame;
    }
    /**
     * @param isNewGame the isNewGame to set
     */
    public void setIsNewGame(int isNewGame) {
        this.isNewGame = isNewGame;
    }
    public Player getCurrentPlayer() {
        return this.players[this.currentNum - 1];
    }
    public City getCurrentCity() {
        return this.players[this.currentNum - 1].getCurrent();
    }
    public void setCurrentPlayerCurrentCity(City c) {
       this.players[this.currentNum - 1].setCurrent(c);;
    }
    /**
     * @return the currentThrown
     */
    public int getCurrentThrown() {
        return currentThrown;
    }
    /**
     * @param currentThrown the currentThrown to set
     */
    public void setCurrentThrown(int currentThrown) {
        this.currentThrown = currentThrown;
    }
    /**
     * @return the remainStep
     */
    public int getRemainStep() {
        return remainStep;
    }
    /**
     * @param remainStep the remainStep to set
     */
    public void setRemainStep(int remainStep) {
        this.remainStep = remainStep;
    }
    private static GameHistory singleton = null;

    private GameHistory() {
	this.currentNum = 1;
    }
    /**
     * @return the players
     */
    public Player[] getPlayers() {
        return players;
    }
    /**
     * @param players the players to set
     */
    public void setPlayers(Player[] players) {
        this.players = players;
    }
    /**
     * @return the playerNum
     */
    public int getPlayerNum() {
        return playerNum;
    }
    /**
     * @param playerNum the playerNum to set
     */
    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
    /**
     * @return the currentNum
     */
    public int getCurrentNum() {
        return currentNum;
    }
    /**
     * @param currentNum the currentNum to set
     */
    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }
    public static GameHistory getGameHistory() {
	if (singleton == null) {
	    singleton = new GameHistory();
	}
	return singleton;
    }

}
