package com.javarush.task.task35.task3513;

import java.util.*;

public class Model {
    private static final int FIELD_WIDTH = 4;
    public Tile[][] gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
    protected int score;
    protected int maxTile;
    private Stack<Tile[][]> previousStates = new Stack<>();
    private Stack<Integer> previousScores = new Stack<>();
    private boolean isSaveNeeded = true;


    public Model() {
        resetGameTiles();
        this.score = 0;
        this.maxTile = 0;

    }

    private void addTile() {
        List<Tile> list = getEmptyTiles();
        if (list.size() > 0) {
            list.get((int) (Math.random() * list.size())).value = Math.random() < 0.9 ? 2 : 4;
        }

    }

    private void saveState(Tile[][] tiles) {
        Tile[][] tiles1 = new Tile[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles1[i][j] = new Tile(tiles[i][j].value);
            }
        }
        previousStates.push(tiles1);
        previousScores.push(score);
        isSaveNeeded = false;
    }

    public void rollback() {
        if (!previousScores.isEmpty() && !previousStates.isEmpty()) {
            gameTiles = previousStates.pop();
            score = previousScores.pop();
        }
    }

    private List<Tile> getEmptyTiles() {
        ArrayList<Tile> emptyList = new ArrayList<>();
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles[i].length; j++) {
                if (gameTiles[i][j].isEmpty()) emptyList.add(gameTiles[i][j]);
            }
        }
        return emptyList;
    }

    public void resetGameTiles() {
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    public boolean canMove() {

        for (int i = 0; i < gameTiles.length; i++) {
            if (compressTiles(gameTiles[i])) return true;
            if (mergeTiles(gameTiles[i])) return true;

        }

        Tile[] tiles = new Tile[gameTiles.length];
        for (int i = 0; i < gameTiles[0].length; i++) {
            for (int j = 0; j < gameTiles.length; j++) {
                if (gameTiles[j][i].isEmpty()) return true;
                tiles[j] = gameTiles[j][i];
            }
            if (compressTiles(tiles)) return true;
            if (mergeTiles(tiles)) return true;

        }
        return false;
    }

    private boolean compressTiles(Tile[] tiles) {
        boolean flag = false;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length - 1; j++) {
                if (tiles[j].isEmpty() & !tiles[j + 1].isEmpty()) {
                    tiles[j].value = tiles[j + 1].value;
                    tiles[j + 1].value = 0;
                    flag = true;


                }
            }
        }

        return flag;

    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean flag = false;


        for (int i = 0; i < tiles.length; i++) {
            if (i + 1 < tiles.length) {
                if (tiles[i].value == tiles[i + 1].value) {
                    if (tiles[i].value != 0) flag = true;
                    tiles[i].value = tiles[i].value * 2;
                    tiles[i + 1].value = 0;
                    if (tiles[i].value > maxTile) maxTile = tiles[i].value;
                    score += tiles[i].value;
                }
            }
        }
        compressTiles(tiles);
        return flag;

    }
    public boolean hasBoardChanged(){
        int gameTilesWeight=0;
        int saveWeight=0;
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles[0].length; j++) {
                gameTilesWeight += gameTiles[i][j].value;
            }

        }
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles[0].length; j++) {
                saveWeight += previousStates.peek()[i][j].value;
            }

        }
        return gameTilesWeight != saveWeight;
    }

    public MoveEfficiency getMoveEfficiency(Move move){
        move.move();
        if (!hasBoardChanged()){
            return new MoveEfficiency(-1,0,move);
        }
        rollback();
        return new MoveEfficiency(getEmptyTiles().size(),score ,move);

    }

    public void autoMove(){
        PriorityQueue <MoveEfficiency> queue = new PriorityQueue<>(4, Collections.reverseOrder());
        queue.offer(getMoveEfficiency(this::left));
        queue.offer(getMoveEfficiency(this::right));
        queue.offer(getMoveEfficiency(this::up));
        queue.offer(getMoveEfficiency(this::down));
        queue.peek().getMove().move();
    }

    public void randomMove(){
        int randomNum = ((int) (Math.random() * 100)) % 4;
        switch (randomNum){
            case 0 : left();
            case 1 : right();
            case 2 : up();
            case 3 : down();
        }
    }

    public void rotate() {
        Tile[][] temp = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles.length; j++) {
                temp[i][j] = gameTiles[FIELD_WIDTH - j - 1][i];
            }
        }
        gameTiles = temp.clone();
    }

    private boolean move() {
        boolean triggerChanged = false;
        for (int i = 0; i < gameTiles.length; i++) {
            if (compressTiles(gameTiles[i])) {
                triggerChanged = true;
            }
            if (mergeTiles(gameTiles[i])) {
                triggerChanged = true;
            }
        }
        return triggerChanged;
    }

    public void left() {
        if (isSaveNeeded)saveState(gameTiles);
        if (move()) {
            addTile();
            isSaveNeeded = true;
        }
    }

    public void right() {
        saveState(gameTiles);
        rotate();
        rotate();
        left();
        rotate();
        rotate();

    }

    public void up() {
        saveState(gameTiles);
        rotate();
        rotate();
        rotate();
        left();
        rotate();

    }

    public void down() {
        saveState(gameTiles);
        rotate();
        left();
        rotate();
        rotate();
        rotate();

    }
}
