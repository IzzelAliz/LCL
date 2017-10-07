package me.kevinwalker.utils;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AsyncDownload {
    private static List<List<Thread>> tasks = new ArrayList<>();
    private String path;
    private String targetFilePath;
    private int threadCount;
    private List<DownloadThread> threads;
    private HashMap<String, Object> map;
    private long length = 0;
    private Callback callback;

    private AsyncDownload(String path, String targetFilePath, int threadCount, HashMap<String, Object> map, Callback callback) {
        this.path = path;
        this.targetFilePath = targetFilePath;
        this.threadCount = threadCount;
        this.map = map;
        threads = new ArrayList<>();
        this.callback = callback;
    }

    public long getFileLength() {
        return length;
    }

    public List<DownloadThread> download() throws Exception {
        callback.log("開始下載 " + path);
        callback.log("存储至 " + targetFilePath);
        URL url = new URL(path);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);

        int code = connection.getResponseCode();
        if (code == 200) {
            long connectionLength = connection.getContentLengthLong();
            length = connectionLength;
            map.put("length", connection.getContentLengthLong());
            callback.log("文件大小： " + getSize(connection.getContentLengthLong()));
            RandomAccessFile randomAccessFile = new RandomAccessFile(new File(targetFilePath, getFileName(url)), "rw");
            randomAccessFile.setLength(connectionLength);
            long blockSize = connectionLength / threadCount;
            for (int threadId = 0; threadId < threadCount; threadId++) {
                long startIndex = threadId * blockSize;
                long endIndex = (threadId + 1) * blockSize - 1;
                if (threadId == (threadCount - 1)) {
                    endIndex = connectionLength - 1;
                }
                DownloadThread dl = new DownloadThread(threadId, startIndex, endIndex, callback);
                dl.start();
                threads.add(dl);
            }
            List<Thread> list = new ArrayList<>(threads);
            list.add(Thread.currentThread());
            tasks.add(list);
            randomAccessFile.close();
        }
        return threads;
    }

    public void adjustThread(long size, int thread) {
        if ((size / Integer.MAX_VALUE) > thread) {

        }
    }

    class DownloadThread extends Thread {
        private int time = 3;
        private boolean complete = false, running = true;

        private int threadId;
        private long startIndex;
        private long endIndex;
        private long last_ = 0, total_ = 0;
        private boolean update = false;
        private Callback callback;

        public DownloadThread(int threadId, long startIndex, long endIndex, Callback callback) {
            this.threadId = threadId;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.callback = callback;
        }

        public String getTName() {
            return "线程 " + threadId + " ";
        }

        @Override
        public void run() {
            running = true;
            while (time-- > 0 && (!complete)) {
                callback.log("线程 " + threadId + " 開始下載。");
                try {
                    URL url = new URL(path);
                    File downThreadFile = new File(targetFilePath,
                            "cache/" + getFileName(url) + "_" + threadId + ".tmp");
                    RandomAccessFile downThreadStream = null;
                    if (downThreadFile.exists()) {
                        downThreadStream = new RandomAccessFile(downThreadFile, "rwd");
                        String startIndex_str = downThreadStream.readLine();
                        if (null == startIndex_str || "".equals(startIndex_str)) {
                            this.startIndex = startIndex;
                        } else {
                            this.startIndex = Integer.parseInt(startIndex_str) - 1;
                        }
                    } else {
                        downThreadStream = new RandomAccessFile(downThreadFile, "rwd");
                    }

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(10000);
                    connection.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);

                    callback.log("线程 " + threadId + " 下载内容 " + startIndex + " -> " + endIndex);

                    if (connection.getResponseCode() == 206) {
                        InputStream inputStream = connection.getInputStream();
                        RandomAccessFile randomAccessFile = new RandomAccessFile(
                                new File(targetFilePath, getFileName(url)), "rw");
                        randomAccessFile.seek(startIndex);

                        byte[] buffer = new byte[1024];
                        int length = -1;
                        long total = 0;
                        while ((length = inputStream.read(buffer)) > 0) {
                            randomAccessFile.write(buffer, 0, length);
                            total += length;
                            update = true;
                            total_ = total;
                            downThreadStream.seek(0);
                            downThreadStream.write((startIndex + total + "").getBytes("UTF-8"));
                        }

                        downThreadStream.close();
                        inputStream.close();
                        randomAccessFile.close();
                        cleanTemp(downThreadFile);
                        callback.log("线程 " + threadId + " 完成下载。");
                        complete = true;
                    } else {
                        callback.log("响应码 " + connection.getResponseCode() + "，服务器不支持多线程下载。");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.log("线程 " + threadId + " 下载出错，重试第 " + (3 - time) + " 次，剩余 " + time + " 次。");
                }
            }
            running = false;
        }

        public long currentSpeed() {
            if (update) {
                long ans = total_ - last_;
                last_ = total_;
                update = false;
                return ans;
            } else
                return 0;
        }

        public boolean isComplete() {
            return complete;
        }

        public boolean isRunning() {
            return running;
        }
    }

    private synchronized void cleanTemp(File file) {
        file.delete();
    }

    private String getFileName(URL url) {
        String filename = url.getFile();
        return filename.substring(filename.lastIndexOf("/") + 1);
    }

    public static List<List<Thread>> getTasks() {
        return tasks;
    }

    public static void download(String url, File target, int thread, Callback callback) {
        new Thread(() -> {
            try {
                HashMap<String, Object> map = new HashMap<>();
                AsyncDownload ad = new AsyncDownload(url, target.getAbsolutePath(), thread, map, callback);
                List<DownloadThread> list = ad.download();
                boolean complete = true, running = true;
                while (running) {
                    Thread.sleep(1000);
                    StringBuilder sb = new StringBuilder();
                    long length = 0, total = 0;
                    running = false;
                    complete = true;
                    for (DownloadThread dl : list) {
                        long spd = dl.currentSpeed();
                        if (dl.isComplete() && (!dl.isRunning()))
                            sb.append(dl.getTName() + "完成\n");
                        else
                            sb.append(dl.getTName() + getFormatSize((double) spd) + "/s\n");
                        length += spd;
                        total += dl.total_;
                        complete = complete && dl.isComplete();
                        running = running || dl.isRunning();
                    }
                    callback.setProgress(new BigDecimal(String.valueOf(total) + ".0")
                            .divide(new BigDecimal(String.valueOf(ad.getFileLength()) + ".0"), 4,
                                    BigDecimal.ROUND_HALF_DOWN)
                            .doubleValue());
                    callback.setSpeed(getFormatSize((double) length));
                    callback.setTooltip(sb.toString());
                }
                map.put("file", new File(target, getFileName1(url)));
                if (complete)
                    callback.success(map);
                else
                    callback.fail(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static String getFormatSize(double size) {
        DecimalFormat f = new DecimalFormat("#.00");
        if (size >= 1099511627776L)
            return f.format(size / 1099511627776.0F) + " TiB";
        if (size >= 1073741824L)
            return f.format(size / 1073741824.0F) + " GiB";
        if (size >= 1048576L)
            return f.format(size / 1048576.0F) + " MiB";
        if (size >= 1024)
            return f.format(size / 1024.0F) + " KiB";
        return size + " B";
    }

    public static String getSize(long size) {
        if (size >= 1099511627776L)
            return (float) size / 1099511627776.0F + " TiB";
        if (size >= 1073741824L)
            return (float) size / 1073741824.0F + " GiB";
        if (size >= 1048576L)
            return (float) size / 1048576.0F + " MiB";
        if (size >= 1024)
            return (float) size / 1024.0F + " KiB";
        return size + " B";
    }

    private static String getFileName1(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public static interface Callback {
        public void log(String msg);

        public void setProgress(double progress);

        public void setTooltip(String tooltip);

        public void setSpeed(String speed);

        public void success(HashMap<String, Object> obj);

        public void fail(HashMap<String, Object> obj);
    }
}
