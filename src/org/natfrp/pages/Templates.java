package org.natfrp.pages;

public class Templates {
    
    public static String Error(int code) {
        String html;
        switch(code) {
            case 403:
                html = "<html lang=\"en\">\n" +
                        "	<head>\n" +
                        "		<title>403 Forbidden</title>\n" +
                        "		<style type=\"text/css\">\n" +
                        "			body {\n" +
                        "				background: #F1F1F1;\n" +
                        "				font-weight: 100 ! important;\n" +
                        "				padding: 32px;\n" +
                        "			}\n" +
                        "			h1 {\n" +
                        "				font-weight: 100 ! important;\n" +
                        "			}\n" +
                        "			logo {\n" +
                        "				font-size: 100px;\n" +
                        "			}\n" +
                        "		</style>\n" +
                        "	</head>\n" +
                        "	<body>\n" +
                        "		<logo>:(</logo>\n" +
                        "		<h1>403 Forbidden</h1>\n" +
                        "		<p><b>Error:</b> Permission denied</p>\n" +
                        "		<p><em>Powered by Sakura Frp</em></p>\n" +
                        "	</body>\n" +
                        "</html>";
                break;
            case 404:
                html = "<html lang=\"en\">\n" +
                        "	<head>\n" +
                        "		<title>404 Not Found</title>\n" +
                        "		<style type=\"text/css\">\n" +
                        "			body {\n" +
                        "				background: #F1F1F1;\n" +
                        "				font-weight: 100 ! important;\n" +
                        "				padding: 32px;\n" +
                        "			}\n" +
                        "			h1 {\n" +
                        "				font-weight: 100 ! important;\n" +
                        "			}\n" +
                        "			logo {\n" +
                        "				font-size: 100px;\n" +
                        "			}\n" +
                        "		</style>\n" +
                        "	</head>\n" +
                        "	<body>\n" +
                        "		<logo>:(</logo>\n" +
                        "		<h1>404 Not Found</h1>\n" +
                        "		<p><b>Error:</b> File or directory not found</p>\n" +
                        "		<p><em>Powered by Sakura Frp</em></p>\n" +
                        "	</body>\n" +
                        "</html>";
                break;
            default:
                html = "";
        }
        return html;
    }
}
