package com.example.codecheckagiletest;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.codecheckagiletest.dao.RecipesEntity;
import com.example.codecheckagiletest.dmain.service.RecipesService;

@RestController
@SpringBootApplication
public class DemoApplication {

  @Autowired
  RecipesService recipesService;

  @RequestMapping("/")
  public String home() {

    List<RecipesEntity> hoge = recipesService.findAllRecipes();

    return hoge.get(0).getTitle();
  }

  @RequestMapping(value = "/test", method=RequestMethod.GET)
  public TestData1 home2() {

    TestData1 testData1 = new TestData1();
    testData1.setHoge("hogeStr");

    List<TestData2> testData2List = new ArrayList<>();
    TestData2 testData2 = new TestData2();
    testData2.setField1(1);
    testData2.setField2("aaa");
    testData2List.add(testData2);

    testData2 = new TestData2();
    testData2.setField1(2);
    testData2.setField2("bbb");
    testData2List.add(testData2);

    testData1.setTestData2(testData2List);

    return testData1;
  }


  @RequestMapping(value = "/test", method=RequestMethod.POST)
  public TestData1 home3(@RequestBody TestData1 testData1) {
    return testData1;
  }

  @RequestMapping(value = "/{id}", method=RequestMethod.GET)
  public int home4(@PathVariable int id) {
    return id;
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}
