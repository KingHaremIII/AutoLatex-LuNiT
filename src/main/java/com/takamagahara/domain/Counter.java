package com.takamagahara.domain;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-18
 * Time: 上午10:38
 */
public class Counter {
    private Integer count;

    public Counter() {
        count = 0;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increment() {
        count = count + 1;
    }

    @Override
    public String toString() {
        return ""+count;
    }
}
