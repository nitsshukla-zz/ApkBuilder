package com.amazon.apkbuilder.util;

import java.io.File;
import java.io.IOException;

public class ApkBuilder {
    Runtime process;
    public ApkBuilder() { process = Runtime.getRuntime();   }
    public String unzip(File apkFile) throws Exception {
        String folderPath = apkFile.getAbsolutePath().replaceAll(".apk", "");
        String unzipCommand = "sh /usr/local/bin/apktool d -s "+apkFile.getAbsolutePath()+" -o "+folderPath;
        exec(unzipCommand);
        String javaFolder = folderPath+"/java";
        makeDirectory(javaFolder);
        exec("/Users/shunitin/Downloads/dex2jar-2.0/d2j-dex2jar.sh "+folderPath+"/classes.dex -o "+folderPath+"/classes-dex2jar.jar");
        String unzipJarCommand = "unzip "+folderPath+"/classes-dex2jar.jar -d "+javaFolder+"/";
        exec(unzipJarCommand);
        String json =  FileUtil.getJsonTree(new File(folderPath)).toJSONString();
        System.out.println("Printing output: " + json);
        return json;
    }
    private void makeDirectory(String filePath) {
        File f = new File(filePath);
        f.mkdir();
    }
    private void exec(String command) throws IOException, InterruptedException {
        System.out.println("Command firing: "+command);
        long sec = System.currentTimeMillis();
        Process p = process.exec(command);
        p.waitFor();
        System.out.printf("Command completed after %d ms\n" , System.currentTimeMillis()-sec);
    }
    
    
}
