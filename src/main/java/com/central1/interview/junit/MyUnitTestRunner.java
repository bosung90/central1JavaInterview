package com.central1.interview.junit;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Created by Eric on 2015-05-30.
 */
public class MyUnitTestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(MyUnitTestRunner.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }
}
