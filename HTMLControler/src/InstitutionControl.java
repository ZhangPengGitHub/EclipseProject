import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * HTMLControler
 * 
 * InstitutionControl.java
 * @author Roc
 * @date 2016-7-6上午9:36:28
 * @Email zp0016@qq.com
 */
public class InstitutionControl {
	public static String getUniversity(String string)
	{
		String institution="";
		string=string.toLowerCase();
		String[] split=string.split(",");
		for(int i=0;i<split.length;i++)
		{
			if(split[i].contains("Universi".toLowerCase())||split[i].contains("Univ.".toLowerCase()))
			{
				institution=split[i];
				break;
			}
		}
		return institution;
	}
	public static Boolean ifRight(String institution) {
		if (!institution.contains("University") && (institution.startsWith("Sch")
				|| institution.startsWith("Department") || institution.startsWith("Dept.")
				|| institution.startsWith("Dep.") || institution.startsWith("Comput.")
				|| institution.startsWith("Computer Science") || institution.startsWith("Computing Science"))) {
			return false;
		}
		return true;

	}
	public static String getFullName(String string) throws IOException
	{
		String fullName=string;
		List<String> jianxie=FileUtils.readLines(new File("C:/Users/Roc/Desktop/jianxie.txt"));
		for(int i=0;i<jianxie.size();i++)
		{
			String[] split=jianxie.get(i).split("\t");
			if (string.contains(split[0])) {
				fullName=fullName.replaceAll(split[0], split[1]);
			}
		}
		return fullName;
	}
	public static String ic(String institution) throws IOException{
//		System.out.println(institution);
		String[] result = institution.split("\\|");
//		System.out.println(result[0]);
		String institutionName = getUniversity(result[0]);
		if (institutionName.equals("")) {
			institutionName=result[0].split(",")[0];
			if (!ifRight(institutionName)) {
				// 若不是机构信息，则换成第二段
				institutionName = result[0].split(",")[1];
			}
		}
//		System.out.println(institutionName);
		return getFullName(institutionName).trim();
	}
	public static void main(String[] args) throws IOException{
		List<String> jianxie=FileUtils.readLines(new File("txt/jianxie.txt"));
	}
}
