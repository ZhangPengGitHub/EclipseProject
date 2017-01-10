/**
 * file_name: PublisherCount.java
 * package_name: pers.guhun.parser
 * project_name: sbse_analysis
 * data: 2016年7月4日下午4:54:54
 * email: 1534759775@qq.com
 *
 */
package pers.guhun.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import pers.guhun.crawlerutil.Information;

/**
 * @author Gao
 *
 */
public class PublisherCount {
	
	public static void main(String[] args) throws IOException {
		countPublisher();
	}


	public static void countPublisher() throws IOException {

		List<Information> informations = getAllInfo();
		// 所有会议期刊名
		List<String> publisherName = FileUtils.readLines(new File("publishers.txt"));
		//每个会议期刊对应的论文数
		List<Integer> publishersCount = new ArrayList<>();
		for(int i=0;i<publisherName.size();i++)
		{
			publishersCount.add(0);
		}

		for (int i = 0; i < informations.size(); i++) {
			String publisher = informations.get(i).getPublisher();

			String[] temp1=publisher.split("'");
			String[] temp2=temp1[0].split("\\(");
			String temp=temp2[temp2.length-1];
			System.out.println(temp);
		}


	}
	
	/**
	 * 从文件中读取所有信息
	 * 
	 * @throws IOException
	 */
	public static List<Information> getAllInfo() throws IOException {
		// 所有信息列表
		List<Information> informations = new ArrayList<>();
		// 该文件所有行信息
		List<String> listInfo = FileUtils.readLines(new File("sbse_information_full.txt"));

		for (int i = 0; i < listInfo.size(); i++) {
			Information information = new Information();
			information.setId(listInfo.get(i++));
			information.setTitle(listInfo.get(i++));
			information.setAbstractStr(listInfo.get(i++));
			information.setPublisher(listInfo.get(i++));
			information.setYear(listInfo.get(i++));
			information.setCiteTime(listInfo.get(i++));
			information.setKeywords(listInfo.get(i++));
			information.setAuthor(listInfo.get(i++));
			information.setInstitution(listInfo.get(i));

			informations.add(information);
		}
		return informations;
	}

}
