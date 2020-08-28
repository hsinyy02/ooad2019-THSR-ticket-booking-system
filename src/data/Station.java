package data;

import java.util.Arrays;

public class Station {
	public static final String CHI_NAME[] = 
		{"南港", "台北", "板橋", "桃園", "新竹", "苗栗", "台中", "彰化", "雲林", "嘉義", "台南", "左營"};
	
	public static final String ENG_NAME[] = {
		"Nangang", "Taipei", "Banciao", "Taoyuan", "Hsinchu", "Miaoli", "Taichung", "Changhua", "Yunlin", "Chiayi", "Tainan", "Zuoying"
	};
	
	public static String getEngName(String name) {
		return ENG_NAME[Arrays.asList(CHI_NAME).indexOf(name)];
	}
	
	public static void main(String[] args) {
		System.out.println(getEngName("桃園"));
	}
}
