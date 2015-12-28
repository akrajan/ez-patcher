import com.pro.akr.ezPatcher.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.ParameterizedType;

public class HelloWorld implements com.pro.akr.ezPatcher.Comparator {
  public static enum Status{ INACTIVE, ACTIVE };

  Integer id;
  String myStr;
  Integer myInt;
  Float myFlt;
  Long myLong;
  Double myDbl;
  HelloWorld innerWorld;
  List<HelloWorld> others;
  Status status;

  public String getMyStr() {
    return myStr;
  }

  public Status getStatus() {
    return status;
  }

  public Integer getMyInt() {
    return myInt;
  }

  public Integer getId() {
    return id;
  }

  public List<HelloWorld> getOthers() {
    return others;
  }

  public Long getMyLong() {
    return myLong;
  }

  public Float getMyFlt() {
    return myFlt;
  }

  public Double getMyDbl() {
    return myDbl;
  }

  public void setMyStr(String x) {
    this.myStr = x;
  }

  public void setMyInt(Integer x) {
    this.myInt = x;
  }

  public void setMyLong(Long x) {
    this.myLong = x;
  }

  public void setMyFlt(Float x) {
    this.myFlt = x;
  }

  public void setMyDbl(Double x) {
    this.myDbl = x;
  }

  public void setId(Integer x) {
    this.id = x;
  }

  public void setInnerWorld(HelloWorld x) {
    this.innerWorld = x;
  }

  public void setOthers(List<HelloWorld> x) {
    this.others = x;
  }

  public void setStatus(Status s) {
    this.status = s;
  }

  public HelloWorld getInnerWorld() {
    return this.innerWorld;
  }

  public HelloWorld() {
  }

  public Boolean isSame(Object other) {
    if(other == null){
      return false;
    } else {
      HelloWorld x = (HelloWorld) other;
      return this.id == x.id;
    }
  }

  public void puts(String a) {
    puts(a, 0);
  }

  @Override
  public String toString() {
    return "id:  " + id + " myInt: " + myInt + " myLong: " + myLong + " myFlt: " + myFlt + " myDbl: " + myDbl + " status: " + status + " myStr: " + myStr;
  }

  public void puts(String a, Integer indentSize) {
    StringBuffer indent = new StringBuffer("");

    for (int i = 0; i < indentSize; i+= 1) {
      indent.append(" ");
    }

    System.out.println(indent + a + " => id:  " + id + " myInt: " + myInt + " myLong: " + myLong + " myFlt: " + myFlt + " myDbl: " + myDbl + " status: " + status + " myStr: " + myStr);

    if(innerWorld != null) {
      innerWorld.puts(a + "(InnerWorld)", indentSize + 2);
    }

    if (others != null) {
      int i = 0;
      for(HelloWorld o: others) {
        i += 1;
        o.puts(a + "(otherWorlds:" + i + ")", indentSize + 2);
      }
    }
  }

  public void chill(final List<?> aListWithSomeType) {
    // Here I'd like to get the Class-Object 'SpiderMan'
    System.out.println(aListWithSomeType.getClass().getGenericSuperclass());
    System.out.println(((ParameterizedType) aListWithSomeType
          .getClass()
          .getGenericSuperclass()).getActualTypeArguments()[0]);
  }

  public static void printAll(List<HelloWorld> all) {
    if(all != null){
      int i = 0;
      for(HelloWorld hw: all) {
        i+=1;
        hw.puts("Test" + i);
      }
    }
  }

  public static HelloWorld createTestWorld() {
    HelloWorld hw = new HelloWorld();
    hw.id = 0;
    hw.myStr = "";
    hw.myInt = 0;
    hw.myFlt = 0.0f;
    hw.myLong = 0L;
    hw.myDbl = 0.0;
    hw.status = Status.ACTIVE;
    hw.others = null;
    hw.innerWorld = null;
    return hw;
  }

  public static void assertOn(HelloWorld hw, String e) {
    System.out.println("Error '" + e + "' found for " + hw);
    throw new RuntimeException(e);
  }

  public static void assertId(HelloWorld w, Integer a) {
    if( w.id == null && a == null  ) {
      return;
    } else if ( w.id == null || a == null || w.id != a ) {
      assertOn(w, "Id doesn't match. Expected: " + a);
    }
  }

  public static void assertStr(HelloWorld w, String a) {
    if( w.myStr == null && a == null  ) {
      return;
    } else if ( w.myStr == null || a == null || !w.myStr.equals(a) ) {
      assertOn(w, "String doesn't match. Expected: " + a);
    }
  }

  public static void assertInt(HelloWorld w, Integer a) {
    if( w.myInt == null && a == null  ) {
      return;
    } else if ( w.myInt == null || a == null || !w.myInt.equals(a) ) {
      assertOn(w, "Integer doesn't match. Expected: " + a);
    }
  }

  public static void assertFloat(HelloWorld w, Float a) {
    if( w.myFlt == null && a == null  ) {
      return;
    } else if ( w.myFlt == null || a == null || !w.myFlt.equals(a) ) {
      assertOn(w, "Float doesn't match. Expected: " + a + " Found: " + w.myFlt);
    }
  }

  public static void assertLong(HelloWorld w, Long a) {
    if( w.myLong == null && a == null  ) {
      return;
    } else if ( w.myLong == null || a == null || !w.myLong.equals(a) ) {
      assertOn(w, "Long doesn't match. Expected: " + a);
    }
  }

  public static void assertDouble(HelloWorld w, Double a) {
    if( w.myDbl == null && a == null  ) {
      return;
    } else if ( w.myDbl == null || a == null || !w.myDbl.equals(a) ) {
      assertOn(w, "Double doesn't match. Expected: " + a);
    }
  }

  public static void assertStatus(HelloWorld w, Status a) {
    if( w.status == null && a == null  ) {
      return;
    } else if ( w.status == null || a == null || w.status != a) {
      assertOn(w, "Long doesn't match. Expected: " + a);
    }
  }

  public static void assertEmptyInner(HelloWorld w) {
    if(w.innerWorld != null) {
      assertOn(w, "Inner is expected to be null");
     }
  }

  public static void assertEmptyOthers(HelloWorld w) {
    if(w.others != null) {
      assertOn(w, "Others is expected to be null");
     }
  }

  public static void assertOthersSize(HelloWorld w, Integer size) {
    if(w.others.size() != size) {
      assertOn(w, "Others size mismatch. Expected: " + size + " Found" + w.others.size());
     }
  }

  public static void testEmpty(Patcher x) {
    HelloWorld hw = createTestWorld();
    x.patch(hw, "{}");
    assertId(hw, 0);
    assertStr(hw, "");
    assertInt(hw, 0);
    assertFloat(hw, 0.0f);
    assertLong(hw, 0L);
    assertDouble(hw, 0.0);
    assertStatus(hw, Status.ACTIVE);
    assertEmptyInner(hw);
    assertEmptyOthers(hw);
    System.out.println("testEmpty passed");
  }


  public static void testInteger(Patcher x) {
    HelloWorld hw = createTestWorld();
    hw.myInt = 10;
    String json = "{}";
    x.patch(hw, json);
    assertInt(hw, 10);

    x.patch(hw, "{\"myInt\": 50}");
    assertInt(hw, 50);

    x.patch(hw, "{\"myInt\": null}");
    assertInt(hw, null);
    System.out.println("testInteger passed");
  }

  public static void testLong(Patcher x) {
    HelloWorld hw = createTestWorld();
    hw.myLong = 10L;
    String json = "{}";
    x.patch(hw, json);
    assertLong(hw, 10L);

    x.patch(hw, "{\"myLong\": 50}");
    assertLong(hw, 50L);

    x.patch(hw, "{\"myLong\": null}");
    assertLong(hw, null);
    System.out.println("testLong passed");
  }

  public static void testFloat(Patcher x) {
    HelloWorld hw = createTestWorld();
    hw.myFlt = 10.0f;
    String json = "{}";
    x.patch(hw, json);
    assertFloat(hw, 10.0f);

    x.patch(hw, "{\"myFlt\": 50.0}");
    assertFloat(hw, 50.0f);

    x.patch(hw, "{\"myFlt\": null}");
    assertFloat(hw, null);
    System.out.println("testFloat passed");
  }

  public static void testDouble(Patcher x) {
    HelloWorld hw = createTestWorld();
    hw.myDbl = 10.0;
    String json = "{}";
    x.patch(hw, json);
    assertDouble(hw, 10.0);

    x.patch(hw, "{\"myDbl\": 50.0}");
    assertDouble(hw, 50.0);

    x.patch(hw, "{\"myDbl\": null}");
    assertDouble(hw, null);
    System.out.println("testDouble passed");
  }

  public static void testString(Patcher x) {
    HelloWorld hw = createTestWorld();
    hw.myStr = "Hello";
    String json = "{}";
    x.patch(hw, json);
    assertStr(hw, "Hello");

    x.patch(hw, "{\"myStr\": \"World\"}");
    assertStr(hw, "World");

    x.patch(hw, "{\"myStr\": null}");
    assertStr(hw, null);
    System.out.println("testString passed");
  }

  public static void testStatus(Patcher x) {
    HelloWorld hw = createTestWorld();
    hw.status = Status.ACTIVE;
    String json = "{}";
    x.patch(hw, json);
    assertStatus(hw, Status.ACTIVE);

    x.patch(hw, "{\"status\": \"INACTIVE\"}");
    assertStatus(hw, Status.INACTIVE);

    x.patch(hw, "{\"status\": null}");
    assertStatus(hw, null);
    System.out.println("testStatus passed");
  }

  public static void testComponent(Patcher x) {
    HelloWorld hw = createTestWorld();
    HelloWorld innerWorld;

    x.patch(hw, "{\"innerWorld\": {\"myStr\": null, \"myInt\": 50, \"myDbl\": 42.2}}");
    innerWorld = hw.innerWorld;
    assertStr(innerWorld, null);
    assertInt(innerWorld, 50);
    assertDouble(innerWorld, 42.2);


    x.patch(hw, "{\"innerWorld\": {\"myStr\": \"I Exist\", \"myInt\": 55, \"myDbl\": null}}");
    assertStr(innerWorld, "I Exist");
    assertInt(innerWorld, 55);
    assertDouble(innerWorld, null);
  }

  public static void testInnerArray(Patcher x) {
    HelloWorld hw = createTestWorld();
    HelloWorld other;

    x.patch(hw, "{\"others\": [{\"myStr\": null, \"myInt\": 50, \"myDbl\": 42.2}]}");

    assertOthersSize(hw, 1);
    other = hw.others.get(0);
    assertStr(other, null);
    assertInt(other, 50);
    assertDouble(other, 42.2);

    x.patch(hw, "{\"others\": []}");
    if(hw.others.size() != 0){
      throw new RuntimeException("Inner Array size doesn't match");
    }

    x.patch(hw, "{\"others\": [{\"myStr\": \"First\"}, {\"myStr\": \"Second\"}]}");

    if(hw.others.size() != 2){
      throw new RuntimeException("Inner Array size doesn't match");
    }
    other = hw.others.get(0);
    assertStr(other, "First");

    other = hw.others.get(1);
    assertStr(other, "Second");

    System.out.println("Inner Array test passed");
  }

  public static void testComparator(Patcher x) {
    HelloWorld hw = createTestWorld();
    HelloWorld other;
    x.patch(hw, "{\"others\": [{\"id\": 1, \"myStr\": \"First\", \"myInt\": 1}, {\"id\": 2, \"myStr\": \"Second\"}]}");

    x.patch(hw, "{\"others\": [{\"id\": 1, \"myStr\": \"Updated First\"}, {\"id\": 3, \"myStr\": \"Third\"}]}");
    assertOthersSize(hw, 2);
    other = hw.others.get(0);
    assertId(other, 1);
    assertStr(other, "Updated First");
    assertInt(other, 1);

    other = hw.others.get(1);
    assertId(other, 3);
    assertStr(other, "Third");

    System.out.println("Comparator test passed");
  }

  public static void testSecondaryNesting(Patcher x) {
    HelloWorld hw = createTestWorld();
    HelloWorld other;

    x.patch(hw, "{\"others\": [{\"others\":[{\"myStr\": \"Two Levels Deep\"}]} ]}");

    assertOthersSize(hw, 1);
    other = hw.others.get(0).others.get(0);
    assertStr(other, "Two Levels Deep");

    System.out.println("Secondary nesting test passed");
  }

  public static void testArrayDecode(Patcher x) {
    String json = "[{\"myStr\": \"Final String\", \"myFlt\": 2.0, \"innerWorld\": {\"myStr\": \"Inner World String\"}}, {\"myStr\": \"JsonArray2\", \"status\": \"INACTIVE\"}]";
    List<HelloWorld> allWorlds = (List<HelloWorld>) x.toArray(json, HelloWorld.class);

    if(allWorlds.size() != 2){
      throw new RuntimeException("Array decode size error");
    }

    HelloWorld world;
    world = allWorlds.get(0);
    assertStr(world, "Final String");
    assertFloat(world, 2.0f);

    world = world.innerWorld;
    assertStr(world, "Inner World String");

    world = allWorlds.get(1);
    assertStr(world, "JsonArray2");
    assertStatus(world, Status.INACTIVE);

    System.out.println("Array decode test passed");
  }

  public static void main(String[] args) throws Exception {
    Patcher x = new Patcher();
    testEmpty(x);
    testInteger(x);
    testLong(x);
    testFloat(x);
    testDouble(x);
    testString(x);
    testStatus(x);
    testComponent(x);
    testInnerArray(x);
    testComparator(x);
    testSecondaryNesting(x);
    testArrayDecode(x);
  }
}

