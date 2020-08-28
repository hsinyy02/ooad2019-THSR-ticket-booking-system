package data;

import java.util.Arrays;

public class Station {
	public static final String CHI_NAME[] = 
		{"�n��", "�x�_", "�O��", "���", "�s��", "�]��", "�x��", "����", "���L", "�Ÿq", "�x�n", "����"};
	
	public static final String ENG_NAME[] = {
		"Nangang", "Taipei", "Banciao", "Taoyuan", "Hsinchu", "Miaoli", "Taichung", "Changhua", "Yunlin", "Chiayi", "Tainan", "Zuoying"
	};
	
	public static String getEngName(String name) {
		return ENG_NAME[Arrays.asList(CHI_NAME).indexOf(name)];
	}
	
	public static void main(String[] args) {
		System.out.println(getEngName("���"));
	}
}
