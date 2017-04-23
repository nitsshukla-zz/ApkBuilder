package com.amazon.apkbuilder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.amazon.apkbuilder.util.FileUtil;
import com.oreilly.servlet.MultipartRequest;

public class UploadFileController extends HttpServlet{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
//        final String path = request.getParameter("destination");
//        final Part filePart = request.getPart("fname");
//        final String fileName = FileUtil.getFileName(filePart);
//        File apkFile = new File(path + File.separator
//            + fileName);
//        FileUtil.uploadFile(apkFile, filePart);
        System.out.println("yoyo: " );
        response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  
        
        MultipartRequest m=new MultipartRequest(request,"/tmp/");
        Enumeration files = m.getFileNames();
        while(files.hasMoreElements())
            System.out.println(m.getFile(files.nextElement().toString()));
        out.print("successfully uploaded");
    }

}
