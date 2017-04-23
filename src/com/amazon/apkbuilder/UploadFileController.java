package com.amazon.apkbuilder;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.amazon.apkbuilder.util.FileUtil;

public class UploadFileController extends HttpServlet{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("yoyo");
        final String path = request.getParameter("destination");
        final Part filePart = request.getPart("file");
        final String fileName = FileUtil.getFileName(filePart);
        File apkFile = new File(path + File.separator
            + fileName);
        FileUtil.uploadFile(apkFile, filePart);
    }

}
