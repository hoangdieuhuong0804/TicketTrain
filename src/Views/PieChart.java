package Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
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
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import DB_Connection.StatementMySQL;


public class PieChart extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox cbb_thongke;
	static Socket socketTrain;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PieChart dialog = new PieChart(socketTrain);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PieChart(Socket socketTrain) {
		this.socketTrain = socketTrain;
		setTitle("Thống Kê");
		setLocationRelativeTo(null);
		URL url = PieChart.class.getResource("/Icon/thongketr.png");
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
						PieChart.this.setVisible(false);
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
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						DefaultPieDataset dataset = new DefaultPieDataset();
						int a= Integer.parseInt((String) cbb_thongke.getItemAt(cbb_thongke.getSelectedIndex()));
						
//						dataset.setValue("Tháng 1",new Integer(rs.getInt("TongTien")));
						dataset.setValue("Tháng 1",getDataMonth(1));
						dataset.setValue("Tháng 2",getDataMonth(2));
						dataset.setValue("Tháng 3",getDataMonth(3));
						dataset.setValue("Tháng 4",getDataMonth(4));
						dataset.setValue("Tháng 5",getDataMonth(5));
						dataset.setValue("Tháng 6",getDataMonth(6));
						dataset.setValue("Tháng 7",getDataMonth(7));
						dataset.setValue("Tháng 8",getDataMonth(8));
						dataset.setValue("Tháng 9",getDataMonth(9));
						dataset.setValue("Tháng 10",getDataMonth(10));
						dataset.setValue("Tháng 11",getDataMonth(11));
						dataset.setValue("Tháng 12",getDataMonth(12));
						
						
						JFreeChart jChart = ChartFactory.createPieChart("Biểu Đồ Số Lượng Khách Trong Năm", dataset,true,true,false);
					    PiePlot p = (PiePlot) jChart.getPlot();
//			        	CategoryPlot plot = jChart.getCategoryPlot();
//			        	plot.setRangeGridlinePaint(Color.black);
			        	ChartFrame chartFrame = new ChartFrame("Biểu Đồ Số Lượng Khách Trong Tháng", jChart,true);
			        	chartFrame.setVisible(true);
			        	chartFrame.setSize(1200,800);
			        	chartFrame.setLocationRelativeTo(null);
					}
					
				});
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
