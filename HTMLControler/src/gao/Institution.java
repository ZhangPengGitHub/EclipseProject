/**
 * file_name: Institution.java
 * package_name: pers.guhun.parser
 * project_name: sbse_analysis
 * data: 2016年7月4日下午9:41:08
 * email: 1534759775@qq.com
 *
 */
package pers.guhun.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.soap.AddressingFeature;

import org.apache.commons.io.FileUtils;

/**
 * @author Gao
 *
 */
public class Institution {
	public static void main(String[] args) throws IOException {
//		getInstitution();
		removeDuplicate();
	}
	
	/**
	 * 去除机构重复信息，合并结果
	 * @throws IOException 
	 */
	public static void removeDuplicate() throws IOException
	{
		//机构以及个数
		List<String> list=FileUtils.readLines(new File("institutionsCount.txt"));
		
		List<String> institutions=new ArrayList<>();
		List<String> counts=new ArrayList<>();
		
		System.out.println(list.size());
		for(int i=0;i<list.size();i++)
		{
			String[] split=list.get(i).split("\t");
			institutions.add(split[0]);
			counts.add(split[1]);
		}
		
		for(int i=0;i<institutions.size();i++)
		{
			String institution=getFullNmae(institutions.get(i));
			FileUtils.writeStringToFile(new File("instCount.txt"), institution+"\t"+counts.get(i)+"\r\n", true);
		}
	}

	/**
	 * 得到机构信息的全称
	 * @param string
	 * @return
	 * @throws IOException 
	 */
	public static String getFullNmae(String string) throws IOException
	{
		String fullName=string;
		List<String> jianxie=FileUtils.readLines(new File("jianxie.txt"));
		for(int i=0;i<jianxie.size();i++)
		{
			String[] split=jianxie.get(i).split("\t");
			if (string.contains(split[0])) {
				fullName=fullName.replaceAll(split[0], split[1]);
			}
		}
		return fullName;
	}
	/**
	 * 从完整的机构信息中去除国家等其他信息获得机构
	 * 
	 * @throws IOException
	 */
	public static void getInstitution() throws IOException {
		// 原始机构信息
		List<String> list = FileUtils.readLines(new File("institutions.txt"));
		// 处理后得到的机构信息
		List<String> institutions = new ArrayList<>();

		List<String> ids = new ArrayList<>();
		System.out.println(list.size());
		
		for (int i = 0; i < list.size(); i++) {

			String institution = list.get(i).split("\t")[1];
			String id = list.get(i).split("\t")[0];
			String[] split = institution.split(",");
			String institutionName = getUniversity(institution);
			if (institutionName.equals("")) {
				institutionName=split[0];
				if (!ifRight(institutionName)) {
					// 若不是机构信息，则换成第二段
					institutionName = split[1];
				}
				
			}
			institutions.add(institutionName.trim());
			ids.add(id);
		}
		for (int i = 0; i < institutions.size(); i++) {
			FileUtils.writeStringToFile(new File("institutionResult.txt"),
					ids.get(i) + "\t" + institutions.get(i) + "\r\n", true);
		}

	}

	/**
	 * 判断机构信息中第一段是否为机构 department ofcomputer science等不是
	 * 
	 * @param institution
	 * @return
	 */
	public static Boolean ifRight(String institution) {
		boolean b = true;
		if (!institution.contains("University") && (institution.startsWith("Sch")
				|| institution.startsWith("Department") || institution.startsWith("Dept.")
				|| institution.startsWith("Dep.") || institution.startsWith("Comput.")
				|| institution.startsWith("Computer Science") || institution.startsWith("Computing Science"))) {
			return false;
		}
		String[] strings = {};
		for (int i = 0; i < strings.length; i++) {
			if (institution.equals(strings[i])) {
				b = false;
				break;
			}
		}
		return b;

	}
	
	/**
	 * 找到包含有university和college的机构信息
	 * @param string
	 * @return
	 */
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

}
