
public class Article {
	private int num = 0;
	private String title = "";
	private String digest = "";
	private String journal = "";
	private String year = "2016";
	private int cite = -1;
	private String keyword = "";
	private String author = "";
	private String instution = "";
	private String country = "";
	private String url = "";
	private String citeUrl = "";
	private String ec = "";
	private int[] number = new int[0];
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int[] getNumber() {
		return number;
	}
	public void setNumber(int[] number) {
		this.number = number;
	}
	public String getEc() {
		return ec;
	}
	public void setEc(String ec) {
		this.ec = ec;
	}
	public String getCiteUrl() {
		return citeUrl;
	}
	public void setCiteUrl(String citeUrl) {
		this.citeUrl = citeUrl;
	}
	private boolean isArticle = false;
	public boolean isArticle() {
		return isArticle;
	}
	public void setArticle(boolean isArticle) {
		this.isArticle = isArticle;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getJournal() {
		return journal;
	}
	public void setJournal(String journal) {
		this.journal = journal;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public int getCite() {
		return cite;
	}
	public void setCite(int cite) {
		this.cite = cite;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getInstution() {
		return instution;
	}
	public void setInstution(String instution) {
		this.instution = instution;
	}
}
