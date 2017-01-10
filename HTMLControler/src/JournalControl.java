/**
 * HTMLControler
 * 
 * JournalControl.java
 * @author Roc
 * @date 2016-7-6ионГ9:18:43
 * @Email zp0016@qq.com
 */
public class JournalControl {
	public static String jc(String journal){
		String[] temp0 = journal.split(",");
		String[] temp1 = temp0[0].split("'");
		String[] temp2 = temp1[0].split("\\)");
		String[] temp3 = temp2[0].split("\\(");
		return temp3[temp3.length-1].replace("also ", "").trim();
	}
}
