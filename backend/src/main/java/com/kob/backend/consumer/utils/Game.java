package com.kob.backend.consumer.utils;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.consumer.WebSocketServer;

import java.util.ArrayList;
import java.util.Date;
import com.kob.backend.pojo.Record;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Game extends Thread {
    private final Integer rows;
    private final Integer cols;
    private final Integer inner_walls_count;
    private final static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    private final Player playerA, playerB;
    private Integer nextStepA = null;
    private Integer nextstepB = null;
    private ReentrantLock lock = new ReentrantLock();
    private final int [][] g;
    private String status = "Playing"; // playing -> finished
    private String loser = ""; // ALl:平局 A: A输 B: B输

    public Game(Integer rows, Integer cols, Integer inner_walls_count, Integer idA, Integer idB){
        this.cols = cols;
        this.rows = rows;
        this.inner_walls_count = inner_walls_count;
        this.g = new int[rows][cols];
        this.playerA = new Player(idA, rows - 2, 1, new ArrayList<>());
        this.playerB = new Player(idB, 1, cols - 2, new ArrayList<>());
    }

    public Player getPlayerA(){
        return playerA;
    }

    public Player getPlayerB(){
        return playerB;
    }

    public void SetNextStepA(Integer nextStep){
        lock.lock();
        try{
            this.nextStepA = nextStep;
        }finally {
            lock.unlock();
        }
    }

    public void SetNextStepB(Integer nextStep){
        lock.lock();
        try{
            this.nextstepB = nextStep;
        } finally {
            lock.unlock();
        }
    }

    public int[][] getG(){
        return g;
    }

    private boolean check_connectivity(int sx, int sy, int tx, int ty) {
        if (sx == tx && sy == ty) return true;
        g[sx][sy] = 1;

        for (int i = 0; i < 4; i ++ ) {
            int x = sx + dx[i], y = sy + dy[i];
            if (x >= 0 && x < this.rows && y >= 0 && y < this.cols && g[x][y] == 0) {
                if (check_connectivity(x, y, tx, ty)) {
                    g[sx][sy] = 0;
                    return true;
                }
            }
        }

        g[sx][sy] = 0;
        return false;
    }

    private boolean draw() {  // 画地图
        for (int i = 0; i < this.rows; i ++ ) {
            for (int j = 0; j < this.cols; j ++ ) {
                g[i][j] = 0;
            }
        }

        for (int r = 0; r < this.rows; r ++ ) {
            g[r][0] = g[r][this.cols - 1] = 1;
        }
        for (int c = 0; c < this.cols; c ++ ) {
            g[0][c] = g[this.rows - 1][c] = 1;
        }

        Random random = new Random();
        for (int i = 0; i < this.inner_walls_count / 2; i ++ ) {
            for (int j = 0; j < 1000; j ++ ) {
                int r = random.nextInt(this.rows);
                int c = random.nextInt(this.cols);

                if (g[r][c] == 1 || g[this.rows - 1 - r][this.cols - 1 - c] == 1)
                    continue;
                if (r == this.rows - 2 && c == 1 || r == 1 && c == this.cols - 2)
                    continue;

                g[r][c] = g[this.rows - 1 - r][this.cols - 1 - c] = 1;
                break;
            }
        }

        return check_connectivity(this.rows - 2, 1, 1, this.cols - 2);
    }

    public void createMap() {
        for (int i = 0; i < 1000; i ++ ) {
            if (draw())
                break;
        }
    }

    private String getMapString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                sb.append(g[i][j]);
            }
        }
        return sb.toString();
    }

    private boolean nextStep(){
        //前端每200ms才能渲染一步
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //加锁，nextstep可能存在读写冲突
        for(int i = 1; i <= 50; i++){
            try {
                Thread.sleep(100);
                lock.lock();
                try{
                    if(nextStepA != null && nextstepB != null){
                        System.out.println("nextStepA: " + nextStepA + " nextStepB: " + nextstepB);
                        System.out.println("playerA: " + playerA.getId() + " playerB: " + playerB.getId());
                        playerA.getSteps().add(nextStepA);
                        playerB.getSteps().add(nextstepB);
                        return true;
                    }
                }finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean check_cell_valid(List<Cell> cellsA, List<Cell> cellsB){
        int n = cellsA.size();
        Cell cell = cellsA.get(n - 1); // A蛇头
        if(g[cell.x][cell.y] == 1) return false;
        for(int i = 0; i < n - 1; i++){
            if(cellsA.get(i).x == cell.x && cellsA.get(i).y == cell.y) return false;
        }
        for(int i = 0; i < n - 1; i++){
            if(cellsB.get(i).x == cell.x && cellsB.get(i).y == cell.y) return false;
        }
        return true;
    }

    private void judge(){ //判断两个玩家的操作是否合法
        List<Cell> cellsA = playerA.getCells();
        List<Cell> cellsB = playerB.getCells();
        boolean valid_a = check_cell_valid(cellsA, cellsB);
        boolean valid_b = check_cell_valid(cellsB, cellsA);
        if(!valid_a || !valid_b){
            status = "Finished";
            if(!valid_a && !valid_b) {
                loser = "All";
            }else if(!valid_a){
                loser = "A";
            }else{
                loser = "B";
            }
        }
    }

    private void sendAllMessage(String message){
        WebSocketServer.users.get(playerA.getId()).sendMessage(message);
        WebSocketServer.users.get(playerB.getId()).sendMessage(message);
    }

    private void sendMove(){ // 向玩家传送信息
        lock.lock();
        try {
            JSONObject resp = new JSONObject();
            resp.put("event", "move");
            resp.put("a_direction", nextStepA);
            resp.put("b_direction", nextstepB);
            sendAllMessage(resp.toJSONString());
            nextStepA = nextstepB = null;
        }finally {
            lock.unlock();
        }
    }

    private void SaveToDatabase(){
        Record record = new Record(
                null,
                playerA.getId(),
                playerA.getSx(),
                playerA.getSy(),
                playerB.getId(),
                playerB.getSx(),
                playerB.getSy(),
                playerA.getStepsString(),
                playerB.getStepsString(),
                getMapString(),
                loser,
                new Date()
        );
        WebSocketServer.recordMapper.insert(record);
    }

    private void SendResult(){ // 向两个玩家公布结果
        JSONObject resp = new JSONObject();
        resp.put("event", "result");
        resp.put("loser", loser);
        sendAllMessage(resp.toJSONString());
        SaveToDatabase();
    }


    @Override
    public void run() {
        for(int i = 0; i < 1000; i++){ // 循环一千步
            if(nextStep()){
                judge();
                if(status.equals("Playing")){
                    sendMove();
                } else{
                    SendResult();
                    break;
                }
            }else{
                status = "Finished";
                lock.lock();
                try{
                    if(nextStepA == null && nextstepB == null){
                        loser = "All";
                    }else if (nextStepA == null){
                        loser = "A";
                    }else{
                        loser = "B";
                    }
                }finally {
                    lock.unlock();
                }
                SendResult();
                break;
            }
        }
    }

}
