package com.nami.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartRedisServer {

	private final static String redisRootPath = "D:\\anzhuang\\Redis-x64-3.2.100";
	
	public static void main(String[] args) {
        List<String> cmds = new ArrayList<String>();
        String cmdRedis6379 = "cmd /k start redis-server.exe 6379\\6379.conf ";//redis-server.exe redis.conf
        String cmdRedis6380 = "cmd /k start redis-server.exe 6380\\6380.conf ";//redis-server.exe redis.conf
        String cmdRedis6381 = "cmd /k start redis-server.exe 6381\\6381.conf ";//redis-server.exe redis.conf

        cmds.add(cmdRedis6379);
        cmds.add(cmdRedis6380);
        cmds.add(cmdRedis6381);

        String cmdRedis26379 = "cmd /k start redis-server.exe 6379\\sentinel.conf --sentinel";//redis-server.exe sentinel26479.conf --sentinel
        String cmdRedis26479 = "cmd /k start redis-server.exe 6380\\sentinel.conf --sentinel";//redis-server.exe sentinel26479.conf --sentinel
        String cmdRedis26579 = "cmd /k start redis-server.exe 6381\\sentinel.conf --sentinel";//redis-server.exe sentinel26479.conf --sentinel

        cmds.add(cmdRedis26379);
        cmds.add(cmdRedis26479);
        cmds.add(cmdRedis26579);

        initRedisServer(cmds);
    }

    public static void initRedisServer(List<String> cmdStr){
        if(cmdStr != null && cmdStr.size() > 0){
            for (String cmd:cmdStr
                 ) {
                try {
                    Process exec = Runtime.getRuntime().exec(cmd, null, new File(redisRootPath));
                    Thread.sleep(1*1000);
                }catch (InterruptedException e) {
                    System.out.println("线程中断异常" + e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("cmd command error" + e.getMessage());
                    e.printStackTrace();
                }

            }
        }

    }
}
