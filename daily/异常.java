package daily;

public class 异常 {
    public static void main(String[] args) {
        System.out.println("开始");
        int i = 9;
        String str = null;
        int[] arr = new int[]{1, 2, 3};
        try {
            System.out.println(i / 0);
            System.out.println(str.length());
            System.out.println(arr[3]);
        } catch (ArithmeticException e) {
            System.out.println("除数不能为0");
        } catch (NullPointerException e) {
            System.out.println("空指针异常");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("数组下标越界");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
        } finally {
            System.out.println("finally");
        }
        try{
            test();
        }catch (Exception e) {
            System.out.println("异常被捕获");
        }
    }
    public static int test() throws Exception {
        int a=2;
        int b=0;
        int c=a/b;
        return c;
    }
}
//异常处理：try-catch-finally
//try{
//    //可能会出现异常的代码
//} catch (Exception e) {
//    //处理异常的代码
//} finally {
//    //释放资源的代码