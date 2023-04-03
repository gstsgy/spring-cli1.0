package com.gstsgy.base.utils;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
public class MathUtils {


    /**
     * 加法运算
     * @param x1
     * @param x2
     * @return
     */
    public static double add(double x1, double x2) {
        BigDecimal num1 =  BigDecimal.valueOf(x1);
        BigDecimal num2 = BigDecimal.valueOf(x2);
        BigDecimal sum = num1.add(num2);
        return sum.doubleValue();
    }

    /**
     * 减法运算
     * @param x1
     * @param x2
     * @return
     */
    public static double subtract(double x1, double x2) {
        BigDecimal num1 =  BigDecimal.valueOf(x1);
        BigDecimal num2 = BigDecimal.valueOf(x2);
        BigDecimal sum = num1.subtract(num2);
        return sum.doubleValue();
    }

    /**
     * 乘法运算
     * @param x1
     * @param x2
     * @return
     */
    public static double multiply(double x1, double x2) {
        BigDecimal num1 =  BigDecimal.valueOf(x1);
        BigDecimal num2 = BigDecimal.valueOf(x2);
        BigDecimal sum = num1.multiply(num2);
        return sum.doubleValue();
    }

    /**
     * 除法运算
     * @param x1
     * @param x2
     * @return
     */
    public static double divide(double x1, double x2) {
        BigDecimal num1 =  BigDecimal.valueOf(x1);
        BigDecimal num2 = BigDecimal.valueOf(x2);
        BigDecimal sum = num1.divide(num2);
        return sum.doubleValue();
    }

    /**
     * 取[minValue,maxValue]之间的size个随机值,包含边界
     * <p><br/>
     * 注意 ：
     * <p><br/>
     * minValue < maxValue;
     * <p><br/>
     * size < maxValue-minValue+1
     *
     * @param minValue
     * @param maxValue
     * @param size
     * @return
     */
    public static int[] getNoRePeatRandom(Integer minValue, Integer maxValue, Integer size) {

        int[] result = new int[size];

        int[] nums = new int[maxValue - minValue + 1];

        Random random = new Random();
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i + minValue;//生成从最小值到最大值范围内的连续值集合
        }
        for (int i = 0; i < size; i++) {
            int r = random.nextInt(nums.length - i + 1 - 1) + i;//生成从下个索引开始的随机索引值，将当前索引值与随机索引值交换
            if (r != i) {//交换索引
                nums[i] = nums[i] ^ nums[r];
                nums[r] = nums[i] ^ nums[r];
                nums[i] = nums[i] ^ nums[r];
            }
            result[i] = nums[i];
            if (i == size) {//只取需要的前面的值
                break;
            }

        }
        return result;
    }

    /**
     * 取[minValue,maxValue]之间的size个随机值,包含边界
     * <p><br/>
     * 注意 ：
     * <p><br/>
     * minValue < maxValue;
     * <p><br/>
     * size < maxValue-minValue+1
     *
     * @param minValue
     * @param maxValue
     * @param size
     * @return Set<Integer, Integer>
     */
    public static Set<Integer> getNoRePeatRandomSet(Integer minValue, Integer maxValue, Integer size) {
        Set<Integer> integers = new HashSet<>();
        int[] a = getNoRePeatRandom(minValue, maxValue, size);
        for (int i = 0; i < a.length; i++) {
            integers.add(a[i]);
        }
        return integers;
    }

    /**
     * 从0到maxValue中等距获取size个值
     * <p><br/>
     * 值范围:[0,maxValue)
     * <p><br/>
     * size <= maxValue
     *
     * @Author zpj
     * @Date 2020/07/20 10:12
     * @Description
     **/
    public static Set<Integer> getEquidistantNumeral(Integer maxValue, Integer size) {
        Set<Integer> integers = new HashSet<>();

        if (maxValue == null) {
            return null;
        }
        if (size == null) {
            return null;
        }
        if (maxValue.compareTo(size) < 0) {
            return null;
        }

        Integer startPoint = 0;
        Integer addNum = 1;

        if (size.compareTo(maxValue) == 0) {//相等
            startPoint = 0;
            addNum = 1;
        } else {
            if (maxValue % size == 0) {
                startPoint = 0;
                addNum = maxValue / size;
            } else {
                startPoint = maxValue % size;
                addNum = (maxValue - startPoint) / size;
            }

        }
        for (int i = startPoint; i < maxValue; i = i + addNum) {
            integers.add(i);
        }
        return integers;
    }

    /**
     * 计算取样件数
     *
     * @Author zpj
     * @Date 2020/07/17 14:08
     * @Description
     **/
    public static Integer getSampleNum(Integer num) {
        if (num <= 3) {
            return num;
        } else if (3 < num && num <= 300) {
            return Double.valueOf(Math.round(Math.sqrt(num))).intValue() + 1;
        } else {
            return Double.valueOf(Math.round(Math.sqrt(num) / 2)).intValue() + 1;
        }
    }

    /**
     * 字符串转数字   例："47.05698" -> 47.056
     *                  "58.124gggg" -> 58.124
     *                  "48" -> 48
     *                  "00000051" ->51
     * @param str
     * @return
     */
    public static double Str2Num(String str){
        if(ObjectUtils.isEmpty(str)){
            return 0;
        }



        double integers = 0;
        double decimals = 0;
        // 是否小数
        boolean isFloat = false;

        // 小数位数
        long decimalCount = 1;


        char[] chars = str.toCharArray();

        for(char c : chars){
            if(c<=57&&c>=48&&!isFloat){
                integers = integers*10+c-48;
            }
            else if(c==46&&!isFloat){
                isFloat = true;
            }else if(c<=57&&c>=48&&isFloat){
                decimals = decimals*10+c-48;
                decimalCount*=10;
            }
            else  {
                if(integers==0&&decimals==0)continue;
                break;
            }
        }
        return integers+decimals/decimalCount;
    }


    public static int parseInt(Integer integer){
       return integer==null?0:integer;
    }


    public static double parseDouble(Double number){
        return number==null?0:number;
    }

    public static double getPositiveNumber(double number){
        if(number>0){
            return number;
        }

        return 0;
    }
    public static int getPositiveNumber(int number){
        if(number>0) {
            return number;
        }
        return 0;
    }
    public static long getPositiveNumber(long number){
        if(number>0) {
            return number;
        }
        return 0;
    }

    /**
     * 48  ---> 0
     * 57  ---> 9
     * 97  ---> a
     * 122  ---> z
     * 65  ---> A
     * 90  ---> Z
     * @param number
     * @return
     */
    public static String intConversion36Binary(int number){
        if(number==0){
            return "0";
        }
        StringBuilder str36Binary = new StringBuilder();


        while(number != 0){
            int oneNumber = number%36;
            int charNum = oneNumber+48;
            if(charNum>57){
                charNum = oneNumber+55;
            }
            str36Binary.append((char) charNum);
            number = number/36;
        }
        return str36Binary.reverse().toString();
    }

    public static String intConversion62Binary(int number){
        if(number==0){
            return "0";
        }
        StringBuilder str52Binary = new StringBuilder();


        while(number != 0){
            int oneNumber = number%62;
            int charNum = oneNumber+48;
            if(charNum>57){
                charNum = oneNumber+55;
            }
            if(charNum>90){
                charNum = oneNumber+61;
            }
            str52Binary.append((char) charNum);
            number = number/62;
        }
        return str52Binary.reverse().toString();
    }

    public static int binary36ToDecimal(String binaryNumber){
        if(!StringUtils.hasLength(binaryNumber)){
            return 0;
        }
        char[] chars = binaryNumber.toCharArray();
        int decimal = 0;
        for(int i = chars.length;i>0;i--){
            int c = chars[i-1];
            int tmp = c- 48;
            if(tmp>9){
                tmp = c- 55;
            }
            decimal += tmp *Math.pow(36, chars.length-i);
        }
        return decimal;
    }

    public static int binary62ToDecimal(String binaryNumber){
        if(!StringUtils.hasLength(binaryNumber)){
            return 0;
        }
        char[] chars = binaryNumber.toCharArray();
        int decimal = 0;
        for(int i = chars.length;i>0;i--){
            int c = chars[i-1];
            int tmp = c- 48;
            if(tmp>9){
                tmp = c- 55;
            }
            if(tmp>35){
                tmp = c- 61;
            }
            decimal += tmp *Math.pow(62, chars.length-i);
        }
        return decimal;
    }

    public static void main(String[] args) {
        System.out.println(add(10,5));
        System.out.println(subtract(10,5));
        System.out.println(multiply(10,5));
        System.out.println(divide(10,5));
    }
}
