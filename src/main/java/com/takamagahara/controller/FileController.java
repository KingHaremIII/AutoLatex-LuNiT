package com.takamagahara.controller;

import com.takamagahara.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
    FileService fileService;

    @RequestMapping("/upload")
    public ModelAndView upload(HttpServletRequest request, MultipartFile upload) {
        return fileService.upload(request, upload);
    }

    @RequestMapping("/process")
    public String Process() {// TODO parameters!!!
//        return fileService.Process();
        return "result";
    }
}
