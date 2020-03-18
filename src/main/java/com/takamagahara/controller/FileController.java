package com.takamagahara.controller;

import com.takamagahara.dao.OperatorEx;
import com.takamagahara.domain.Counter;
import com.takamagahara.service.FileService;
import com.takamagahara.xmler.Operator;
import com.takamagahara.xmler.SectionNode;
import com.takamagahara.xmler.XMLer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-16
 * Time: 下午12:56
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    Operator operator;
    @Autowired
    OperatorEx operatorEx;
    @Autowired
    FileService fileService;
    @Autowired
    SAXReader saxReader;
    @Autowired
    Counter counter;

    @RequestMapping("/upload")
    public ModelAndView upload(HttpServletRequest request, MultipartFile upload) {
        return fileService.upload(request, upload);
    }

    @RequestMapping("/jump")
    public String jump() {
        return "redirect:/result.jsp";
    }

    @RequestMapping("/process")
    public String Process() {// TODO parameters!!!
//        return fileService.Process();
        return "result";
    }

    @RequestMapping("/tmp")
    public String test(HttpServletRequest request) {
        System.out.println("=============="+request.getParameter("result"));
        return "result";
    }

    @RequestMapping("/ajaxTest")
    public @ResponseBody
    List<String> testAjax(@RequestBody String ajaxPathsList){
        System.out.println("testAjax方法执行了...");
        // 客户端发送ajax的请求，传的是json字符串，后端把json字符串封装到user对象中
//        System.out.println(ajaxPathsList);

        String uuid = UUID.randomUUID().toString().replace("-", "");
        String uFilename = uuid+"_Structure.xml";
        File txt=new File(uFilename);
        try {
            if (!txt.exists()) {
                txt.createNewFile();
            }
            byte bytes[] = new byte[512];
            bytes = ajaxPathsList.getBytes();
            FileOutputStream fos = new FileOutputStream(txt);
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element root = null;
        try {
            root = saxReader.read(new File(uFilename)).getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        List<String> list = pathsFromXML(root);

        list.add(uFilename);

        System.out.println("******************candidates:");
        for (String s : list) {
            System.out.println(s);
        }

        return list;
    }

    @RequestMapping("/ajaxTest2")
    public @ResponseBody
    String testAjax2(@RequestBody String ajaxPathsList){
        System.out.println("testAjax2方法执行了...");
        // 客户端发送ajax的请求，传的是json字符串，后端把json字符串封装到user对象中
        System.out.println(ajaxPathsList);
        String[] unitPaths = ajaxPathsList.split("/");
        List<String> selected = new ArrayList<>();
        int i = 0;
        for (;i<unitPaths.length-1;i++) {
            selected.add(unitPaths[i]);
            System.out.println("received......"+unitPaths[i]);
        }

        // acquire all nodes.
        Element root = null;
        Document document = null;
        try {
            document = saxReader.read(new File(unitPaths[i]));
            root = document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        List<String> paths = pathsFromXML(root);

        // add unitTest attribute to selected nodes.
        try {
            XMLer.reader((new SectionNode(root, "Documents")), operatorEx,
                    OperatorEx.class.getMethod("addUnitTest", SectionNode.class, List.class, Counter.class),
                    paths, counter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        XMLer.writer(document, unitPaths[i]);


        return readToString(unitPaths[i]);
//        return "test last";
    }

    private List<String> pathsFromXML(Element root) {

        List<String> paths = new ArrayList<>();
        try {
            XMLer.reader((new SectionNode(root, "Documents")), operator,
                    Operator.class.getMethod("pathRecorder", SectionNode.class, List.class),
                    paths);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return paths;
    }

    private String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

//    @RequestMapping("/ajaxTest")
//    public @ResponseBody
//    List<String> testAjax(@RequestBody String ajaxPathsList){
//        System.out.println("testAjax方法执行了...");
//        // 客户端发送ajax的请求，传的是json字符串，后端把json字符串封装到user对象中
//        System.out.println(ajaxPathsList);
//        List<String> list = new ArrayList<>();
//        list.add("Documents/I");
//        list.add("Documents/I/A");
//        return list;
//    }
//
//    @RequestMapping("/ajaxTest2")
//    public @ResponseBody
//    String testAjax2(@RequestBody String ajaxPathsList){
//        System.out.println("testAjax方法执行了...");
//        // 客户端发送ajax的请求，传的是json字符串，后端把json字符串封装到user对象中
//        System.out.println(ajaxPathsList);
//        return "<sections>\n\t<section>\n\t\n\t</section>\n</sections>";
//    }

    /**
     * 使用关键字的方式进行转发或者重定向
     * @return
     */
    @RequestMapping("/testForwardOrRedirect")
    public String testForwardOrRedirect(){
        System.out.println("testForwardOrRedirect方法执行了...");

        // 请求的转发
        // return "forward:/WEB-INF/pages/success.jsp";

        // 重定向
        return "redirect:/result.jsp";
    }
}
