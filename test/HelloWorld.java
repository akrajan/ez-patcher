import com.pro.akr.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.ParameterizedType;

public class HelloWorld implements com.pro.akr.Comparator {
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
    if(w.id != a){
      assertOn(w, "Id doesn't match. Expected: " + a);
    }
  }

  public static void assertStr(HelloWorld w, String a) {
    if(! w.myStr.equals(a)){
      assertOn(w, "String doesn't match. Expected: " + a);
    }
  }

  public static void assertInt(HelloWorld w, Integer a) {
    if(w.myInt != a){
      assertOn(w, "Integer doesn't match. Expected: " + a);
    }
  }

  public static void assertFloat(HelloWorld w, Float a) {
    if(! w.myFlt.equals(a)){
      assertOn(w, "Float doesn't match. Expected: " + a + " Found: " + w.myFlt);
    }
  }

  public static void assertLong(HelloWorld w, Long a) {
    if(w.myLong != a){
      assertOn(w, "Long doesn't match. Expected: " + a);
    }
  }

  //public static void assertDouble(HelloWorld w, Double a) {
    //if(!w.myDbl.equals(a)){
      //assertOn(w, "Double doesn't match. Expected: " + a);
    //}
  //}

  public static void assertStatus(HelloWorld w, Status a) {
    if(w.status != a){
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

  public static void assertDouble(HelloWorld w, Double a) {
    if(!w.myDbl.equals(a)){
      assertOn(w, "Double doesn't match. Expected: " + a);
    }
  }

  public static void testEmpty(UpdateOnly x) {
    HelloWorld hw = createTestWorld();
    x.updateWith(hw, "{}");
    assertId(hw, 0);
    assertStr(hw, "");
    assertInt(hw, 0);
    hw.puts("");
    assertFloat(hw, 0.0f);
    assertLong(hw, 0L);
    assertDouble(hw, 0.0);
    assertStatus(hw, Status.ACTIVE);
    assertEmptyInner(hw);
    assertEmptyOthers(hw);
    System.out.println("testEmpty passed");
  }


  public static void testInteger(UpdateOnly x) {
    HelloWorld hw = createTestWorld();
    hw.myInt = 10;
    String json = "{}";
    x.updateWith(hw, json);
    assertInt(hw, 10);

    x.updateWith(hw, "{\"myInt\": 50}");
    assertInt(hw, 50);

    x.updateWith(hw, "{\"myInt\": null}");
    assertInt(hw, null);
    System.out.println("testInteger passed");
  }

  public static void testLong(UpdateOnly x) {
    HelloWorld hw = createTestWorld();
    hw.myLong = 10L;
    String json = "{}";
    x.updateWith(hw, json);
    assertLong(hw, 10L);

    x.updateWith(hw, "{\"myLong\": 50}");
    assertLong(hw, 50L);

    x.updateWith(hw, "{\"myLong\": null}");
    assertLong(hw, null);
    System.out.println("testLong passed");
  }

  public static void testFloat(UpdateOnly x) {
    HelloWorld hw = createTestWorld();
    hw.myFlt = 10.0f;
    String json = "{}";
    x.updateWith(hw, json);
    assertFloat(hw, 10.0f);

    x.updateWith(hw, "{\"myFlt\": 50.0}");
    assertFloat(hw, 50.0f);

    x.updateWith(hw, "{\"myFlt\": null}");
    assertFloat(hw, null);
    System.out.println("testFloat passed");
  }

  public static void testDouble(UpdateOnly x) {
    HelloWorld hw = createTestWorld();
    hw.myDbl = 10.0;
    String json = "{}";
    x.updateWith(hw, json);
    assertDouble(hw, 10.0);

    x.updateWith(hw, "{\"myDbl\": 50.0}");
    assertDouble(hw, 50.0);

    //x.updateWith(hw, "{\"myDbl\": null}");
    //assertDouble(hw, null);
    System.out.println("testDouble passed");
  }

  public static void testString(UpdateOnly x) {
    HelloWorld hw = createTestWorld();
    hw.myStr = "Hello";
    String json = "{}";
    x.updateWith(hw, json);
    assertStr(hw, "Hello");

    x.updateWith(hw, "{\"myStr\": \"World\"}");
    assertStr(hw, "World");

    x.updateWith(hw, "{\"myStr\": null}");
    assertStr(hw, null);
    System.out.println("testString passed");
  }

  //public static void testComponent() {
  //}

  //public static void testInnerArray() {
  //}

  //public static void testArrayDecode() {
  //}

  public static void main(String[] args) throws Exception {
    UpdateOnly x = new UpdateOnly();
    //System.out.println("Forname = " + Class.forName("HelloWorld$Status"));
    //String json = "[{\"myStr\": \"Final String\", \"myFlt\": 2.0, \"innerWorld\": {\"myStr\": \"Inner World Str\"}}, {\"myStr\": \"JsonArray2\", \"status\": \"INACTIVE\"}]";
    //List<HelloWorld> allWorlds = (List<HelloWorld>) x.toArray(json, HelloWorld.class);
    //printAll(allWorlds);

    testEmpty(x);
    testInteger(x);
    testLong(x);
    testFloat(x);
    testDouble(x);
    testString(x);
  }
}

