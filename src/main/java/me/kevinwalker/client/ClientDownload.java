package me.kevinwalker.client;

public class ClientDownload {
    public static enum DownloadParam {
        /**
         * 下载Forge
         */
        FORGE,
        /**
         * 使用 v(String 版本)
         */
        MINECRAFT,
        /**
         * 下载LiteLoader
         */
        LITELOADER;
        String v;

        public void v(String version) {
            this.v = v;
        }
    }

    public static void download(DownloadParam... type) {

    }

}
