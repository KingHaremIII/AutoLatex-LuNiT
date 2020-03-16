package com.takamagahara.service.impl;

import com.takamagahara.dao.OperatorEx;
import com.takamagahara.service.FileService;
import com.takamagahara.xmler.Operator;
import com.takamagahara.xmler.SectionNode;
import com.takamagahara.xmler.XMLer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.*;

import org.dom4j.io.SAXReader;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-16
 * Time: 下午12:58
 */
@Service("fileService")
public class FileServiceImpl implements FileService {
    @Autowired
    SAXReader saxReader;
    @Autowired
    Operator operator;
    @Autowired
    OperatorEx operatorEx;


    @Override
    public ModelAndView upload(HttpServletRequest request, MultipartFile upload) {
        System.out.println("uploading...");
        ModelAndView mv = new ModelAndView();

        /*
        use fileupload to upload
         */
        // upload path
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        System.out.println("============Upload Path: "+path);
        File file = new File(path);
        if(!file.exists()){
            // initialize uploads directory
            file.mkdirs();
        }

        // acquire the name of the file to be uploaded.
        String filename = upload.getOriginalFilename();
        if (filename.equals("Structure.xml")) {
            // modify file name
            String uuid = UUID.randomUUID().toString().replace("-", "");
            filename = uuid + "_" + filename;
            // transfer the file
            try {
                upload.transferTo(new File(path, filename));
            } catch (IOException e) {
                System.err.println("failed!===============");
                e.printStackTrace();
            }

            try {
                Element root = saxReader.read(new File(path+"/"+filename)).getRootElement();
                System.out.println("uploaded successfully, root name: "+root.getName());
                List<String> paths = new ArrayList<>();
                try {
                    XMLer.reader(path + "/" + filename, operatorEx,
                            OperatorEx.class.getMethod("pathRecorderExcludingOthers",
                                    SectionNode.class, List.class),
                            paths);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    System.err.println("Error in FileServiceImpl: XMLer.reader");
                    throw  new RuntimeException();
                }
                // save paths and full filename for success.jsp
                for (String s : paths) {
                    mv.addObject("paths", paths);
                }
                mv.addObject("filename", path+"/"+filename);
                mv.addObject("length", paths.size());
                mv.setViewName("success");
            } catch (DocumentException e) {
                System.err.println("Not found Structure.xml! ");
                throw  new RuntimeException();
            }
        } else {
            System.err.println("Can only accept Structure.xml");
        }
        return mv;
    }

    @Override
    public String Process() {
        // TODO get element from page


        return "result";
    }
}
