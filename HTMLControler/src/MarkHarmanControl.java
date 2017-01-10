import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import jxl.read.biff.BiffException;

/**
 * HTMLControler
 * 
 * MarkHarmanControl.java
 * @author Roc
 * @date 2016-7-6обнГ10:07:29
 * @Email zp0016@qq.com
 */
public class MarkHarmanControl {
	public static void main(String[] args){
		Article[] a = new Article[0];
		HashMap<String,Integer> result = new HashMap<String,Integer>();
		try {
			a = EC.readExcel();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<a.length;i++){
			if(a[i].getAuthor().toLowerCase().startsWith("mark harman")){
				//System.out.print(a[i].getTitle()+"    ");
				//System.out.print(a[i].getAuthor()+"    ");
				//System.out.println(a[i].getInstution());
				//System.out.println(a[i].getCountry());
			}
			if(a[i].getInstution().toLowerCase().contains("university of york")){
				String[] tempString = a[i].getAuthor().split("\\|");
				for(int j=0;j<tempString.length;j++){
					if(result.containsKey(tempString[j].trim())){
						int num = result.get(tempString[j].trim())+1;
						result.remove(tempString[j].trim());
						result.put(tempString[j].trim(), num);
					}
					else{
						result.put(tempString[j].trim(), 1);
					}
				}
			}
		}
		for(String str : result.keySet()){
			System.out.println(str + "  " + result.get(str));
		}
 	}
}
