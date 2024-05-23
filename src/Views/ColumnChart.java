package Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import DB_Connection.StatementMySQL;





public class ColumnChart<Image>  extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox cbb_thongke;
	static Socket socketTrain;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ColumnChart dialog = new ColumnChart(socketTrain);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @param socketTrain 
	 */
	public ColumnChart(Socket socketTrain) {
		this.socketTrain = socketTrain;
		setTitle("Thống Kê");
		this.setLocationRelativeTo(null);
		URL url = ColumnChart.class.getResource("/Icon/thongke.png");
		Image img = (Image) Toolkit.getDefaultToolkit().createImage(url);
		this.setIconImage((java.awt.Image) img);
		
		setBounds(600, 300, 403, 185);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lb_thongke = new JLabel("Chọn Năm Cần Thống Kê:");
		lb_thongke.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lb_thongke.setBounds(30, 37, 249, 25);
		contentPanel.add(lb_thongke);
		
		cbb_thongke = new JComboBox();
		for(int i=2018; i<=yearnow();i++){
			cbb_thongke.addItem(i+"");
		}
		cbb_thongke.setBackground(Color.WHITE);
		cbb_thongke.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cbb_thongke.setBounds(287, 37, 80, 27);
		contentPanel.add(cbb_thongke);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton bt_quaylai = new JButton("Quay Lại");
				bt_quaylai.setBackground(Color.GRAY);
				bt_quaylai.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						ColumnChart.this.setVisible(false);
					}
					
				});
				bt_quaylai.setFont(new Font("Tahoma", Font.PLAIN, 18));
				bt_quaylai.setActionCommand("OK");
				buttonPane.add(bt_quaylai);
				getRootPane().setDefaultButton(bt_quaylai);
			}
			{
				JButton bt_thongke = new JButton("Thống Kê");
				bt_thongke.setBackground(Color.GRAY);
				bt_thongke.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						DefaultCategoryDataset bdcot = new DefaultCategoryDataset();
						int a= Integer.parseInt((String) cbb_thongke.getItemAt(cbb_thongke.getSelectedIndex()));
						bdcot.setValue(getDataMonth(1), "Số Lượng Khách", "Tháng 1");
						bdcot.setValue(getDataMonth(2), "Số Lượng Khách", "Tháng 2");
						bdcot.setValue(getDataMonth(3), "Số Lượng Khách", "Tháng 3");
						bdcot.setValue(getDataMonth(4), "Số Lượng Khách", "Tháng 4");
						bdcot.setValue(getDataMonth(5), "Số Lượng Khách", "Tháng 5");
						bdcot.setValue(getDataMonth(6), "Số Lượng Khách", "Tháng 6");
						bdcot.setValue(getDataMonth(7), "Số Lượng Khách", "Tháng 7");
						bdcot.setValue(getDataMonth(8), "Số Lượng Khách", "Tháng 8");
						bdcot.setValue(getDataMonth((9)), "Số Lượng Khách", "Tháng 9");
						bdcot.setValue(getDataMonth(10), "Số Lượng Khách", "Tháng 10");
						bdcot.setValue(getDataMonth(11), "Số Lượng Khách", "Tháng 11");
						bdcot.setValue(getDataMonth(12), "Số Lượng Khách", "Tháng 12");
		
						
						
						
						
						JFreeChart jChart = ChartFactory.createBarChart("Biểu Đồ Lượng Khách Trong Năm","Theo Các Tháng Trong Năm", "Tổng Lượng Khách Hằng Tháng (Đơn Vị: Người)", bdcot, PlotOrientation.VERTICAL,true,true,false);
			        	CategoryPlot plot = jChart.getCategoryPlot();
//			        	plot.setRangeGridlinePaint(Color.black);
			        	ChartFrame chartFrame = new ChartFrame("Biểu Đồ Cột Số Lượng Khách Trong Tháng", jChart,true);
			        	chartFrame.setVisible(true);
			        	chartFrame.setSize(1200,800);
			        	chartFrame.setLocationRelativeTo(null);
					}
				}
					);
				bt_thongke.setFont(new Font("Tahoma", Font.PLAIN, 18));
				bt_thongke.setActionCommand("Cancel");
				buttonPane.add(bt_thongke);
			}
		}
		setVisible(true);
	}
	public int yearnow() {
		   Calendar cal = new GregorianCalendar();
		   return cal.get(Calendar.YEAR);
	}
	public int getDataMonth(int month) {
		int value = 0;
		StatementMySQL statementMySQL = new StatementMySQL();
		value = statementMySQL.getDataMonth(month);
		return value;
	}
}
