package org.example.test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("All Library Tests Suite")
@SelectClasses({
        BookTest.class,
        MemberTest.class,
        LibraryTest.class
})
public class AllTestsSuite {
}