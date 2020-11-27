package core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class SQLHelper {
    /**
     * 이 클래스는 SQL 데이터 바인딩 헬퍼 클래스로 정적 멤버 함수를 가지고 있습니다.
     * 
     * ResultSet의 데이터 값의 타입을 확인한 후,
     * 관련 데이터 객체의 setter 메서드를 자동 호출하여 데이터를 바인딩하게 됩니다.
     * 
     * 코드는 자바 리플렉션으로 되어있으므로 해석하기 복잡합니다.
     * 
     * 그러나 별도의 프레임워크 없이도 반복되는 코드를 획기적으로 줄일 수 있게 합니다.
     * 
     * 출처는 중국 사이트에서 크롤링된 데이터로 보여지는데 자세히 파악되진 않습니다.
     * 
     * @link https://programming.vip/docs/java-jdbc-resultset-results-are-assigned-to-java-objects-through-java-reflection.html
     * 
     * 다만 규칙에 따라 테이블에 정의한 대로 컬럼명을 적어야 오류가 생기지 않게 됩니다.
     * 
     * @link http://commons.apache.org/proper/commons-dbutils/examples.html
     * 
     * 또한 아파치에서 지원하는 유틸도 있습니다.
     * 
     * @param <T>
     * @param rs
     *            ResultSet
     * @param obj
     *            java Class of class
     * @return
     */
    public static <T> ArrayList<T> putResult(ResultSet rs, Class<T> obj) {
        try {
            ArrayList<T> arrayList = new ArrayList<T>();
            ResultSetMetaData metaData = rs.getMetaData();
            /**
             * 컬럼을 갯수를 구합니다.
             */
            int count = metaData.getColumnCount();
            while (rs.next()) {
                /**
                 * 새로운 VO 인스턴스를 생성합니다.
                 */
                T newInstance = obj.newInstance();
                for (int i = 1; i <= count; i++) {
                    /**
                     * 메타데이터에서 컬럼명을 가지고 온 후 카멜 케이스로 변경합니다.
                     * CTM_ID면 ctmId가 됩니다.
                     */
                    String name = metaData.getColumnName(i).toLowerCase();
                    
                    // getter & setter와 연결하기 위해 첫 글자를 대문자로 변경합니다.
                    name = toJavaField(name); 
                    
                    String substring = name.substring(0, 1);// title case
                    String replace = name.replaceFirst(substring, substring.toUpperCase());
                    Class<?> type = null;
                    try {
                        type = obj.getDeclaredField(name).getType();// Get field type
                    } catch (NoSuchFieldException e) { // Class When the field is not defined by the object,skip
                        continue;
                    }

                    Method method = obj.getMethod("set" + replace, type);
                    /**
                     * 타입에 맞는 메서드를 실행합니다.
                     * 즉, 변수명이 ctmid면 setCtmid와 바인딩됩니다.
                     */
                    if (type.isAssignableFrom(String.class)) {
                        method.invoke(newInstance, rs.getString(i));
                    } else if (type.isAssignableFrom(byte.class) || type.isAssignableFrom(Byte.class)) {
                        method.invoke(newInstance, rs.getByte(i));// byte The data type is an 8-bit signed integer represented by a binary complement
                    } else if (type.isAssignableFrom(short.class) || type.isAssignableFrom(Short.class)) {
                        method.invoke(newInstance, rs.getShort(i));// short The data type is a 16 bit signed integer represented by a binary complement
                    } else if (type.isAssignableFrom(int.class) || type.isAssignableFrom(Integer.class)) {
                        method.invoke(newInstance, rs.getInt(i));// int The data type is a 32-bit signed integer represented by a binary complement
                    } else if (type.isAssignableFrom(long.class) || type.isAssignableFrom(Long.class)) {
                        method.invoke(newInstance, rs.getLong(i));// long The data type is a 64 bit signed integer represented by a binary complement
                    } else if (type.isAssignableFrom(float.class) || type.isAssignableFrom(Float.class)) {
                        method.invoke(newInstance, rs.getFloat(i));// float Data type is single precision, 32-bit, compliant IEEE 754 Standard floating point number
                    } else if (type.isAssignableFrom(double.class) || type.isAssignableFrom(Double.class)) {
                        method.invoke(newInstance, rs.getDouble(i));// double Data type is double, 64 bit, compliant IEEE 754 Standard floating point number
                    } else if (type.isAssignableFrom(BigDecimal.class)) {
                        method.invoke(newInstance, rs.getBigDecimal(i));
                    } else if (type.isAssignableFrom(boolean.class) || type.isAssignableFrom(Boolean.class)) {
                        method.invoke(newInstance, rs.getBoolean(i));// boolean Data type represents one bit of information
                    } else if (type.isAssignableFrom(Date.class)) {
                        method.invoke(newInstance, rs.getDate(i));
                    }
                }
                arrayList.add(newInstance);
            }
            return arrayList;

        } catch (InstantiationException | IllegalAccessException | SQLException | SecurityException | NoSuchMethodException | IllegalArgumentException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Database naming format to java Naming format
     * 
     * @param str
     *            Database field name
     * @return java Field name
     */
    public static String toJavaField(String str) {

        String[] split = str.split("_");
        StringBuilder builder = new StringBuilder();
        builder.append(split[0]);// Concatenate first character

        // If the array has more than one word
        if (split.length > 1) {
            for (int i = 1; i < split.length; i++) {
                // Remove underscores and capitalize initial
                String string = split[i];
                String substring = string.substring(0, 1);
                split[i] = string.replaceFirst(substring, substring.toUpperCase());
                builder.append(split[i]);
            }
        }

        return builder.toString();
    }
}
