package com.takamagahara.dao;

import com.takamagahara.xmler.SectionNode;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-16
 * Time: 下午3:01
 */
public class OperatorEx {
    public void addUnitTest(SectionNode sectionNode, Map<String, Boolean> paths) {
        if (paths.get(sectionNode.getPath())) {
            sectionNode.getElement().addAttribute("unitTest", "true");
        }
    }

    public void pathRecorderExcludingOthers(SectionNode sectionNode, List<String> paths) {
        if (sectionNode.getElement().getName().equals("section")) {
            paths.add(sectionNode.getPath());
        }
    }

    public void addUnitTest(SectionNode sectionNode, List<String> list) {
        if (list.contains(sectionNode.getPath())) {
            sectionNode.getElement().addAttribute("unitTest", "true");
        }
    }
}
