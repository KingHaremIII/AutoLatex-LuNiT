package com.takamagahara.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-16
 * Time: 下午12:58
 */
public interface FileService {
    /**
     * upload Structure.xml to server and save sections to generate selection page--success.jsp
     * @param request
     * @param upload
     * @return
     */
    ModelAndView upload(HttpServletRequest request, MultipartFile upload);

    /**
     * collect selections in success.jsp, add unitTest attribute to sections and show the result for StructureTmp.xml
     * @return
     */
    String Process();
}
