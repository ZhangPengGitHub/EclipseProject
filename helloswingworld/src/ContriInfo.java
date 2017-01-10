import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ContriInfo {
	static final int WIDTH = 800;
	static final int HEIGHT = 500;
	public static void main(String[] args){
		JFrame jf = new JFrame("读取贡献信息");
		JPanel jp = new JPanel();
		Box verBox = Box.createVerticalBox();
		jf.setSize(WIDTH,HEIGHT);
		jf.setContentPane(jp);
		JButton button1 = new JButton("读取贡献信息");
		JButton button2 = new JButton("读入期刊/会议");
		JButton button3 = new JButton("修改SAdd表");
		JButton button4 = new JButton("修改Help表");
		jp.add(verBox);
		verBox.add(button1);
		//verBox.add(verBox.createHorizontalGlue());
		verBox.add(button2);
		verBox.add(button3);
		verBox.add(button4);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
}