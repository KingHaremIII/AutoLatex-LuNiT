package com.takamagahara.dao;

import com.takamagahara.domain.Counter;
import com.takamagahara.xmler.SectionNode;
import org.springframework.beans.factory.annotation.Autowired;

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
//    public void addUnitTest(SectionNode sectionNode, Map<String, Boolean> paths) {
//        if (paths.get(sectionNode.getPath())) {
//            sectionNode.getElement().addAttribute("unitTest", "true");
//        }
//    }

    public void pathRecorderExcludingOthers(SectionNode sectionNode, List<String> paths) {
        if (sectionNode.getElement().getName().equals("section")) {
            paths.add(sectionNode.getPath());
        }
    }

    public void addUnitTest(SectionNode sectionNode, List<String> list, Counter counter) {
        System.out.println("############Number: "+counter.toString());
        if (list.contains(counter.toString())) {
            sectionNode.getElement().addAttribute("unitTest", "true");
        }
        counter.increment();
    }
}
