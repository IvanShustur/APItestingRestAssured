package com.cucumber.junit.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = "com.cucumber.junit",
        features = "src/test/java/resources/Api.feature"
)
public class CucumberTestRunner {

}
