package com.example.codecheckagiletest.dmain.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.util.List;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.codecheckagiletest.dmain.model.RecipesEntity;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.operation.Operation;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipesServiceTest {

  @Autowired
  RecipesService testTarget;


  public static final Operation DELETE_ALL = Operations.deleteAllFrom("recipes");

  public static final Operation INSERT =
      Operations.insertInto("recipes")
              .columns("id", "title","making_time","serves","ingredients","cost","created_at","updated_at")
              .values(1,"チキンカレー","45分","4人","玉ねぎ,肉,スパイス",1000,"2016-01-10 12:10:12","2016-01-10 12:10:12")
              .values(2,"オムライス","30分","2人","玉ねぎ,卵,スパイス,醤油",700,"2016-01-11 13:10:12","2016-01-11 13:10:12")
              .build();

  @Autowired
  private DataSource dataSource;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {}

  @AfterClass
  public static void tearDownAfterClass() throws Exception {}

  @Before
  public void setUp() throws Exception {
    Destination dest = new DataSourceDestination(dataSource);
    Operation ops = Operations.sequenceOf(DELETE_ALL, INSERT);
    DbSetup dbSetup = new DbSetup(dest, ops);
    dbSetup.launch();
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void test() {
    List<RecipesEntity> actual = testTarget.findAll();
    assertThat(actual.get(0).getId(), is(1));
  }

}
