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
        hw.puts("OuterArray " + i);
      }
    }
  }

  public static void main(String[] args) throws Exception {
    UpdateOnly x = new UpdateOnly();
    HelloWorld hw = new HelloWorld();
    //System.out.println("Forname = " + Class.forName("HelloWorld$Status"));
    String json = "[{\"myStr\": \"Final String\", \"myFlt\": 2.0, \"innerWorld\": {\"myStr\": \"Inner World Str\"}}, {\"myStr\": \"JsonArray2\", \"status\": \"INACTIVE\"}]";
    List<HelloWorld> allWorlds = (List<HelloWorld>) x.toArray(json, HelloWorld.class);
    printAll(allWorlds);
  }
}

