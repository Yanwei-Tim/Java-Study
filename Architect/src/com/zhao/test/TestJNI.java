package com.zhao.test;
public class TestJNI {
	static {
		System.loadLibrary("E:\\Works\\Git-Space\\Java-Study\\Architect\\src\\JDll.dll");
	}

	public native static void showVoidContent();

	public native static String showReturnContent();

	public native static String getContent(String bookName, int bookId,
			long bookTime, double bookPrice);

	public native static String getOneContent(String one, String two);

	public native static double getTwoDouble(double one, double two,
			String three);

	public static void main(String[] args) {
		//TestJNI tt = new TestJNI();
		String str = TestJNI.getOneContent("¸ã·É»ú", "¸ã´óÂé");
		System.out.println(str);

	}
}
