package com.example.codecheckagiletest;

import java.util.List;
import lombok.Data;

@Data
public class TestData1 {
  private String hoge;
  private List<TestData2> testData2;
}
