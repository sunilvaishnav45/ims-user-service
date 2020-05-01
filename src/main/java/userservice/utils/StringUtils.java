package userservice.utils;

import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StringUtils {

    private static final Logger LOGGER = Logger.getLogger(StringUtils.class);

    public static Optional<List<Integer>> convertCommaSepratedIntoList(String s){
        if(s==null || s.isEmpty())
          return Optional.ofNullable(null);
        List<Integer> integerList = new ArrayList<Integer>();
        String[] stringList = s.split(",");
        for (String str : stringList){
            try {
                Integer integer = Integer.valueOf(str);
                integerList.add(integer);
            }catch (NumberFormatException e){
                throw new NumberFormatException(e.getMessage());
            }
        }
        return integerList!=null && !integerList.isEmpty() ? Optional.ofNullable(integerList) : Optional.ofNullable(null);
    }
}
