import static org.junit.Assert.*;
import org.junit.*;
import java.util.Arrays;
import java.util.List;

class IsMoon implements StringChecker {
  public boolean checkString(String s) {
    return s.equalsIgnoreCase("moon");
  }
}

class CompareString implements StringChecker {
	String str;
	public CompareString(String str) {
		this.str = str;
	}

	public boolean checkString(String s) {
		if (s == null) throw new NullPointerException();
		return s.contains(str);
	}
}

public class TestListExamples {
  @Test
  public void TestFilter() {
      String[] strArr = {"hello", "good morning", "hello", "hey", "howdy", "hello"};
      List<String> strList = Arrays.asList(strArr);
      StringChecker cs = new CompareString("he");
      List<String> expected = Arrays.asList("hello", "hello", "hey", "hello");
      List<String> actual = ListExamples.filter(strList, cs);
      assertEquals(expected, actual);
  }

  @Test
  public void TestMerge() {
      List<String> list1 =  Arrays.asList("arr", "brr", "crr");
      List<String> list2 = Arrays.asList("drr", "err", "frr");
      List<String> expected = Arrays.asList("arr", "brr", "crr",
                                          "drr", "err", "frr");
      List<String> actual = ListExamples.merge(list1, list2);
      assertEquals(expected, actual);
  }

  @Test(timeout = 500)
  public void testMergeRightEnd() {
    List<String> left = Arrays.asList("a", "b", "c");
    List<String> right = Arrays.asList("a", "d");
    List<String> merged = ListExamples.merge(left, right);
    List<String> expected = Arrays.asList("a", "a", "b", "c", "d");
    assertEquals(expected, merged);
  }
}
