import com.takamagahara.domain.Counter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-18
 * Time: 上午10:43
 */
public class CounterTest {

    @Test
    public void test() {
        Counter counter = new Counter();
        counter.setCount(5);
        System.out.println(counter.toString());
    }

    @Test
    public void stringTest() {
        String string = "2/4/960a301570864303aa6babffcda8bbaa_Structure.xml";
        String[] tmp = string.split("/");
        for(String t : tmp) {
            System.out.println(t);
        }
    }
}
