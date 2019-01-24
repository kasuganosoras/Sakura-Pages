package org.natfrp.pages;

import org.acraft.NicoHttp.Event.Exchange;
import org.acraft.NicoHttp.Object.Http.Handler;
import org.acraft.NicoHttp.Object.Http.Server;

import java.io.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static int Port;
    private static String token, Root, mimelist, defaultPage;
    private static Map<String, String> argslist;

    public static void main(String[] args) {
        Logger.info("Sakura Pages 1.0 by Akkariin");
        try {
            Port = Integer.parseInt(args[0]);
            token = args[1];
            Root = args[2];
            defaultPage = args[3];
            Logger.info("Running on port: " + Port);
            Logger.info("The root path: " + Root);
            long size = new FileSize().getTotalSizeOfFile(Root);
            Logger.info("Data folder size: " + size);
            loadMime();
            Server.create(Port).setHandler(new Handler() {
                @Override
                public void onRequest(Exchange event) throws IOException {
                    event.setHeader("X-Powered-By", "SakuraPages/10.0");
                    event.setHeader("Server", "SakuraFrp");
                    //event.setHeader("Access-Control-Allow-Origin", "*");
                    try {
                        String domain = event.getURL().getHost();
                        if (new File(Root + "/" + domain).exists()) {
                            NormalRequest(event, Root + "/" + domain, defaultPage);
                        } else {
                            event.setHeader("Content-type", "text/html;charset=utf8");
                            event.setHeader("Location", "https://www.natfrp.org/");
                            event.sendHeader(302, 0);
                            event.write("Sakura Pages");
                        }
                    } catch (Exception ex) {
                        event.setHeader("Location", "https://www.natfrp.org/");
                        event.sendHeader(302, 0);
                        event.write("Sakura Pages");
                    }
                }
            });
        } catch (Exception ex) {
            Logger.info("Args: Port Token Root defaultPage");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    public static String $_GET(String key) {
        try {
            return argslist.get(key);
        } catch (Exception ex) {
            return "";
        }
    }

    public static void NormalRequest(Exchange event, String Root, String defaultPage) throws IOException {
        event.setHeader("X-Powered-By", "SakuraPages/10.0");
        event.setHeader("Server", "SakuraFrp");
        //event.setHeader("Access-Control-Allow-Origin", "*");
        String dir = new File(Root + event.getURL().getPath()).getCanonicalPath();
        String rtd = new File(Root).getCanonicalPath();
        if (!dir.contains(rtd)) {
            event.sendHeader(403, 0);
            event.writeln(Templates.Error(403));
        } else {
            if (!new File(dir).exists()) {
                event.setHeader("Content-type", "text/html;charset=utf8");
                event.sendHeader(404, 0);
                event.write(Templates.Error(404));
            } else if (!new File(dir).isFile()) {
                if (new File(dir + "/" + defaultPage).exists()) {
                    dir = new File(dir + "/" + defaultPage).getCanonicalPath();
                    String[] explode = defaultPage.split("\\.");
                    String attr = "";
                    if (explode.length > 1) {
                        attr = explode[explode.length - 1];
                    }
                    event.setHeader("Content-type", getMime(attr));
                    event.sendHeader(200, 0);

                    try (FileInputStream inputStream = new FileInputStream(dir)) {
                        byte[] bs = new byte[1024];
                        for (int i; (i = inputStream.read(bs)) != -1;) {
                            event.getExchange().getResponseBody().write(bs, 0, i);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    event.setHeader("Content-type", "text/html;charset=utf8");
                    event.sendHeader(403, 0);
                    event.write(Templates.Error(403));
                }
            } else {
                String[] explode = new File(dir).getName().split("\\.");
                String attr = "";
                if (explode.length > 1) {
                    attr = explode[explode.length - 1];
                }
                event.setHeader("Content-type", getMime(attr));
                event.sendHeader(200, 0);

                try (FileInputStream inputStream = new FileInputStream(dir)) {
                    byte[] bs = new byte[1024];
                    for (int i; (i = inputStream.read(bs)) != -1;) {
                        event.getExchange().getResponseBody().write(bs, 0, i);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }

    public static void loadMime() {
        try {
            InputStreamReader isr = new InputStreamReader(Main.class.getClassLoader().getResourceAsStream("mime.conf"), "utf-8");
            try (BufferedReader br = new BufferedReader(isr)) {
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    mimelist += lineTxt;
                }
            }
        } catch (IOException e) {
        }
    }

    public static String getMime(String Ex) {
        String[] getlist = mimelist.split(";");
        for (String list : getlist) {
            String[] gettar = list.split("=");
            String tag1 = gettar[0];
            if (tag1.contains(Ex)) {
                return gettar[1];
            }
        }
        return "";
    }
}
