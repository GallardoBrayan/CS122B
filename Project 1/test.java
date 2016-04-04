
import java.util.*;

public class test
{

	public static void main(String[] args) throws Exception
	{
		String testStr = "Hey this is ";
		String [] strs = new String[]{"Hi", "There", "Tommy"};
		testStr += Arrays.toString(strs);
		System.out.println(testStr);

	}
}