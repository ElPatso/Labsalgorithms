import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by OstapPK on 28.11.2016.
 */
public class test {
    public static void main(String[] args) {
        Map<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(1,100);
        hashMap.put(2,200);
        hashMap.put(3,300);
        int min = hashMap.get(1);
        for (int i:hashMap.values()){
            if (min>i) min = i;
        }
        System.out.println(min);
    }
}
