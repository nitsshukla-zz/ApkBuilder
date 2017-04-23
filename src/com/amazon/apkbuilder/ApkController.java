package com.amazon.apkbuilder;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.amazon.apkbuilder.util.ApkBuilder;
import com.amazon.apkbuilder.util.FileUtil;
@WebServlet("/upload")
@MultipartConfig
public class ApkController extends HttpServlet{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = 
        Logger.getLogger(ApkController.class.getCanonicalName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("yay");
        String absoluteDiskPath = getServletContext().getRealPath("/");
        System.out.println(absoluteDiskPath);
        String out = processRequest(req, resp);
        if(out!=null){
            //req.getSession().setAttribute("json", out);
            FileUtil.write(absoluteDiskPath+"/flare.json",out);
            resp.sendRedirect("ApkAnalyser.jsp");
            //RequestDispatcher reqDispatcher = getServletContext().getRequestDispatcher("/ApkAnalyser.jsp");
            //reqDispatcher.forward(req,resp);
            //req.setAttribute("json", out);
        }
    }

    protected String processRequest(HttpServletRequest request,
        
        HttpServletResponse response)
            throws ServletException, IOException {
        String output=null;
        //response.setContentType("text/html;charset=UTF-8");
        ApkBuilder apkbuilder = new ApkBuilder();
        // Create path components to save the file
        final String path = request.getParameter("destination");
        final Part filePart = request.getPart("file");
        final String fileName = FileUtil.getFileName(filePart);
        if(!fileName.endsWith(".apk")){
            
            response.getWriter().println("<font color='red'>Error!!</font><br/>Please upload apk file");
            return null;
        }
       //final PrintWriter writer = response.getWriter();

        try {
            File apkFile = new File(path + File.separator
                + fileName);
            FileUtil.uploadFile(apkFile, filePart);
            
            output=apkbuilder.unzip(apkFile);
            LOGGER.log(Level.INFO, "File{0} has been successfully unzipped", 
                new Object[]{fileName});
        } catch (FileNotFoundException fne) {
//            writer.println("You either did not specify a file to upload or are "
//                + "trying to upload a file to a protected or nonexistent "
//                + "location.");
//            writer.println("<br/> ERROR: " + fne.getMessage());

            
        } catch(Exception e){
            LOGGER.log(Level.SEVERE, "Problems during file unzipping. Error: {0}", 
                new Object[]{e.getMessage()});
            e.printStackTrace();
        }
        return output;
    }
}
