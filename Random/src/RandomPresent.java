import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;


public class RandomPresent {

	public static void main(String[] args) {
		int num = -1;
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(System.in));
			System.out.println("请输入随机数的最大值。");
			String str = br.readLine();
			num = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Random r = new Random(System.currentTimeMillis());
		System.out.println(Math.abs(r.nextInt())%num);

	}

}
