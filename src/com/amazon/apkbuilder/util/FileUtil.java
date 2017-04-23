package com.amazon.apkbuilder.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Part;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.amazon.apkbuilder.ApkController;

public class FileUtil {
    private final static Logger LOGGER = Logger.getLogger(FileUtil.class.getCanonicalName());

    public static JSONObject getJsonTree(File f) {
        JSONObject obj = new JSONObject();
        obj.put("text", f.getName());
        if (f.isFile()) {
            return obj;
        }
        JSONArray array = new JSONArray();
        LOGGER.log(Level.INFO, "Creating tree for : " + f.getAbsolutePath());
        for (File ff : f.listFiles()) {
            if (ff.isDirectory())
                array.add(getJsonTree(ff));
            else {
                JSONObject objF = new JSONObject();
                objF.put("text", ff.getName());
                array.add(objF);
            }
        }
        if (array.size() != 0)
            obj.put("children", array);
        return obj;
    }

    public static void write(String fileName, String output) throws IOException {
        File file = new File(fileName);
        file.delete();
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.write(output);
        writer.close();
        fileWriter.close();

    }

    public static String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public static void uploadFile(File apkFile, Part filePart) {
        OutputStream out = null;
        InputStream filecontent = null;
        try {
            out = new FileOutputStream(apkFile);

            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            // writer.println("New file " + fileName + " created at " + path);
            LOGGER.log(Level.INFO, "File{0}being uploaded to {1}",
                new Object[] { apkFile.getName(), apkFile.getAbsolutePath() });
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}", new Object[] { e.getMessage() });
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (filecontent != null) {
                    filecontent.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
