package com.jxc.core.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import org.jfree.util.Rotation;

public class JfreeChart {
		private static String path = "F:/workspace-sts-3.6.1.RELEASE/JXC/src/main/webapp/media/";
		public static void main(String[] args) {
		String classDir = JfreeChart.class.getResource("/").getPath() ;
		System.out.println(classDir);
		/*DefaultCategoryDataset dataset = new DefaultCategoryDataset();//9:2014/11/06
		dataset.addValue(2760.00, "收入", "2014/11/06");
		dataset.addValue(2060.00, "支出", "2014/11/06");
		dataset.addValue(4120, "收入", "2014/11/07");
		dataset.addValue(6100,"收入","2014/11/08");
		dataset.addValue(2100,"收入","2014/11/09");
		dataset.addValue(3000, "收入", "2014/11/010");
		createColumnChart3D(dataset);*/
		DefaultPieDataset dataset  =  new DefaultPieDataset();
		dataset.setValue("1", 250);
		dataset.setValue("2", 250);
		dataset.setValue("3", 250);
		dataset.setValue("4", 250);
		createpieChart3D(dataset);
	}
	/**
	 * 3D饼状图
	 */
	public static void createpieChart3D(DefaultPieDataset dataset){
		JFreeChart chart = ChartFactory.createPieChart3D("订单数量图", dataset,true,false,false);
		// 设置标题文字  
        ChartFrame frame = new ChartFrame("订单数量图 ",chart, true); 
        //取得饼图对象
		PiePlot3D plot = (PiePlot3D)chart.getPlot();
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:({1},{2})"));
		// 图形边框颜色  
        plot.setBaseSectionOutlinePaint(Color.BLACK); 
        // 图形边框粗细  
        plot.setBaseSectionOutlineStroke(new BasicStroke(1.0f));  
        // 指定显示的饼图上圆形(false)还椭圆形(true)  
        plot.setCircular(true);  
		//设置开始角度
		plot.setStartAngle(1500);
		// 设置鼠标悬停提示  
        plot.setToolTipGenerator(new StandardPieToolTipGenerator());
        // 设置突出显示的数据块  
        //plot.setExplodePercent("One", 0.1D);
        // 设置饼图各部分标签字体  
        plot.setLabelFont(new Font("宋体", Font.ITALIC, 20));
		//设置方向为”顺时针方向“
		plot.setDirection(Rotation.CLOCKWISE);
		// 设置图例说明Legend上的文字  
        chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 30));
		//设置透明度，0.5F为半透明，1F为不透明，0F为全透明
		plot.setForegroundAlpha(0.5F);
		//没有数据的时候显示的内容
		plot.setNoDataMessage("无数据显示");
		// 定义字体格式  
        Font font = new java.awt.Font("黑体", java.awt.Font.CENTER_BASELINE,22);  
        TextTitle title = new TextTitle("订单数量图");  
        title.setFont(font);
        // 设置字体,非常关键不然会出现乱码的,下方的字体  
        chart.setTitle(title);
		//背景颜色设置
		plot.setBackgroundPaint(ChartColor.WHITE);
		//prcessChart(chart);
		plot.setOutlinePaint(Color.WHITE); // 设置绘图面板外边的填充颜色
		plot.setShadowPaint(Color.WHITE); // 设置绘图面板阴影的填充颜色
		frame.pack();  
        frame.setVisible(true);
		try{
			ChartUtilities.saveChartAsPNG(new File("PieChart3D.png"), chart, 800, 500);
		}catch(IOException e){
			e.printStackTrace();
		}
        /*try {
        	OutputStream os = new FileOutputStream("src/main/webapp/media/image/ColumnChart.png");
			ChartUtilities.writeChartAsPNG(os, chart, 600, 400);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	/**
	 * 柱状图
	 */
	public static void createColumnChart(DefaultCategoryDataset dataset){
		/*DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(100, "北京", "苹果");
		dataset.addValue(120, "上海", "苹果");
		dataset.addValue(160,"广州","苹果");
		dataset.addValue(210,"北京","梨子");
		dataset.addValue(220, "上海", "梨子");
		dataset.addValue(230, "广州", "梨子");
		dataset.addValue(330, "北京", "葡萄");
		dataset.addValue(340, "上海", "葡萄");
		dataset.addValue(340, "广州", "葡萄");
		dataset.addValue(420, "北京", "香蕉");
		dataset.addValue(430, "上海", "香蕉");
		dataset.addValue(440, "广州", "香蕉");
		dataset.addValue(510, "北京", "荔枝");
		dataset.addValue(530, "上海", "荔枝");
		dataset.addValue(510, "广州", "荔枝");*/
		JFreeChart chart = ChartFactory.createBarChart("水果产量图", "水量", "产量", dataset,PlotOrientation.VERTICAL,true,true,true);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(ChartColor.WHITE);//背景色设置
		plot.setRangeGridlinePaint(ChartColor.GRAY);//网格线设置
		//设置网格竖线颜色  
		plot.setDomainGridlinePaint(Color.pink);  
		//设置网格横线颜色  
		plot.setRangeGridlinePaint(Color.pink);
		//显示每个柱的数值，并修改该数值的字体属性  
		BarRenderer3D renderer = new BarRenderer3D();  
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());  
		renderer.setBaseItemLabelsVisible(true);  
		//默认的数字显示在柱子中，通过如下两句可调整数字的显示  
		//注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题  
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));  
		renderer.setItemLabelAnchorOffset(10D);
		//设置每个地区所包含的平行柱的之间距离  
		renderer.setItemMargin(0.4);  
		plot.setRenderer(renderer);  
		
		
		ChartFrame frame = new ChartFrame("水果产量图",chart,true);
		chart.getLegend().setVisible(false);
		frame.pack();
		frame.setVisible(true);
		prcessChart(chart);
		try{
			ChartUtilities.saveChartAsPNG(new File("src/main/webapp/media/image/ColumnChart.png"), chart, 800, 500);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * 3D柱状图
	 */
	public static void createColumnChart3D(DefaultCategoryDataset dataset){
		StandardChartTheme standardChartTheme=new StandardChartTheme("CN");
		//设置轴向的字体  
        standardChartTheme.setLargeFont(new Font("黑体",Font.PLAIN,20));  
        //应用主题样式  
        ChartFactory.setChartTheme(standardChartTheme);
		JFreeChart chart = ChartFactory.createBarChart3D("收支图","日期","元",dataset,PlotOrientation.VERTICAL,true,true,false);
		//设置图例的字体显示，防止中文乱码
		chart.getLegend().setItemFont(new Font("黑体", 0, 20));
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(ChartColor.WHITE);//背景色设置
		plot.setRangeGridlinePaint(ChartColor.GRAY);//网格线设置
		
		ChartFrame frame = new ChartFrame("收支图",chart,true);
		//chart.getLegend().setVisible(false);
		TextTitle textTitle = new TextTitle("收支图");
	    textTitle.setFont(new Font("宋体", Font.BOLD, 22));
	    chart.setTitle(textTitle);
	    
	    plot.setBackgroundPaint(ChartColor.WHITE);//背景色设置
		plot.setRangeGridlinePaint(ChartColor.GRAY);//网格线设置
		//设置网格竖线颜色  
		plot.setDomainGridlinePaint(Color.pink);  
		//设置网格横线颜色  
		plot.setRangeGridlinePaint(Color.pink);
		//显示每个柱的数值，并修改该数值的字体属性  
		BarRenderer3D renderer = new BarRenderer3D();  
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());  
		renderer.setBaseItemLabelsVisible(true);  
		//默认的数字显示在柱子中，通过如下两句可调整数字的显示  
		//注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题  
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));  
		renderer.setItemLabelAnchorOffset(10D);
		//设置每个地区所包含的平行柱的之间距离  
		//renderer.setItemMargin(0.4);  
		plot.setRenderer(renderer);  
		 //设置横轴的字体，防止中文乱码
        plot.getDomainAxis().setTickLabelFont(new Font("黑体", 0, 10));
        //设置竖轴的字体，防止中文乱码
        plot.getRangeAxis().setLabelFont(new Font("黑体", 0, 20));
		
		frame.pack();
		frame.setVisible(true);
		try{
			ChartUtilities.saveChartAsPNG(new File(path+"ColumnChart3D.png"), chart, 800, 500);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * 折线图
	 */
	public static void createLineChart(DefaultCategoryDataset dataset){
		/*DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(100, "北京", "苹果");
		dataset.addValue(120, "上海", "苹果");
		dataset.addValue(160,"广州","苹果");
		dataset.addValue(210,"北京","梨子");
		dataset.addValue(220, "上海", "梨子");
		dataset.addValue(230, "广州", "梨子");
		dataset.addValue(330, "北京", "葡萄");
		dataset.addValue(340, "上海", "葡萄");
		dataset.addValue(340, "广州", "葡萄");
		dataset.addValue(420, "北京", "香蕉");
		dataset.addValue(430, "上海", "香蕉");
		dataset.addValue(440, "广州", "香蕉");
		dataset.addValue(510, "北京", "荔枝");
		dataset.addValue(530, "上海", "荔枝");
		dataset.addValue(510, "广州", "荔枝");*/
		JFreeChart chart = ChartFactory.createLineChart("日销量","日期", "数量", dataset,PlotOrientation.VERTICAL,true,true,true);
		//设置图例的字体显示，防止中文乱码
		chart.getLegend().setItemFont(new Font("黑体", 0, 20));
	    //设置标题并且设置其字体，防止中文乱码
	    TextTitle textTitle = new TextTitle("日销量图");
	    textTitle.setFont(new Font("宋体", Font.BOLD, 22));
	    chart.setTitle(textTitle);
	    
	    chart.getLegend().setVisible(false);
	    //设置图表子标题
	    chart.addSubtitle(new TextTitle("按天"));
	    //创建一个标题对象，用于放置产生图形日前
	    TextTitle tt = new TextTitle("日期:"+new Date());
	    //设置该标题的字体，防止中文乱码
	    tt.setFont(new Font("黑体", 0, 20));
	    //设置该标题的位置为产生的图形下面
	    tt.setPosition(RectangleEdge.BOTTOM);
	    //设置图片为右对齐
	    tt.setHorizontalAlignment(HorizontalAlignment.RIGHT);
	    //将该标题添加到图表
	    chart.addSubtitle(tt);
	    //设置整个图表的背景色
	    chart.setBackgroundPaint(Color.white);
	    //获取图表区域对象
	    CategoryPlot cp = chart.getCategoryPlot();
	    //设置图表区域背景色
	    cp.setBackgroundPaint(Color.white);
	    //设置边线不可见
	    cp.setRangeGridlinesVisible(true);
	    cp.setRangeGridlinePaint(ChartColor.GRAY);//网格线设置
        //设置横轴的字体，防止中文乱码
        cp.getDomainAxis().setTickLabelFont(new Font("黑体", 0, 10));
        //设置竖轴的字体，防止中文乱码
        cp.getRangeAxis().setLabelFont(new Font("黑体", 0, 20));
	    //获取显示线条的对象
	    LineAndShapeRenderer lasp = (LineAndShapeRenderer)cp.getRenderer();
	    //设置拐点是否可见/是否显示拐点
	    lasp.setBaseShapesVisible(true);
	    //设置拐点不同用不同的形状
	    lasp.setDrawOutlines(true);
	    //设置线条是否被显示填充颜色
	    lasp.setUseFillPaint(true);
	    //设置拐点颜色
	    lasp.setBaseFillPaint(Color.yellow);
	    // 设置折线加粗
	    lasp.setSeriesStroke(0, new BasicStroke(3F));
	    lasp.setSeriesOutlineStroke(0, new BasicStroke(2.0F));
	    // 设置折线拐点
	    lasp.setSeriesShape(0,new java.awt.geom.Ellipse2D.Double(-5D, -5D, 10D, 10D));
	    
	    ChartFrame frame = new ChartFrame("日销量",chart,true);
	    frame.pack();
		frame.setVisible(true);
		/*CategoryPlot cp = chart.getCategoryPlot();
		cp.setBackgroundPaint(ChartColor.WHITE);//背景色设置
		cp.setRangeGridlinePaint(ChartColor.GRAY);//网格线设置
*/		
		/*ChartFrame frame = new ChartFrame("水果产量图",chart,true);
		frame.pack();
		frame.setVisible(true);*/
		//prcessChart(chart);
		try{
			ChartUtilities.saveChartAsPNG(new File(path+"LineChart.png"), chart, 800, 500);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	/**
	 * 3D折线图
	 */
	public static void createLineChart3D(DefaultCategoryDataset dataset){
		/*DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(100, "北京", "苹果");
		dataset.addValue(120, "上海", "苹果");
		dataset.addValue(160,"广州","苹果");
		dataset.addValue(210,"北京","梨子");
		dataset.addValue(220, "上海", "梨子");
		dataset.addValue(230, "广州", "梨子");
		dataset.addValue(330, "北京", "葡萄");
		dataset.addValue(340, "上海", "葡萄");
		dataset.addValue(340, "广州", "葡萄");
		dataset.addValue(420, "北京", "香蕉");
		dataset.addValue(430, "上海", "香蕉");
		dataset.addValue(440, "广州", "香蕉");
		dataset.addValue(510, "北京", "荔枝");
		dataset.addValue(530, "上海", "荔枝");
		dataset.addValue(510, "广州", "荔枝");*/
		JFreeChart chart = ChartFactory.createLineChart3D("水果产量图", "水果", "产量", dataset,PlotOrientation.VERTICAL,true,true,true);
		CategoryPlot cp = chart.getCategoryPlot();
		cp.setBackgroundPaint(ChartColor.WHITE);//背景色设置
		cp.setRangeGridlinePaint(ChartColor.GRAY);//网格线设置
		
		ChartFrame frame = new ChartFrame("水果产量图",chart,true);
		frame.pack();
		frame.setVisible(true);
		
		try{
			ChartUtilities.saveChartAsPNG(new File("src/main/webapp/media/image/LineChart3D.png"), chart, 800, 500);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	/**
	 * 解决图表汉字显示问题
	 * 
	 * @param chart
	 */
	private static void prcessChart(JFreeChart chart){
		/*CategoryPlot plot = chart.getCategoryPlot();
		CategoryAxis domainAxis = plot.getDomainAxis();
		ValueAxis rAxis = plot.getRangeAxis();
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, 
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(new Font("宋体",Font.PLAIN,20));
		domainAxis.setTickLabelFont(new Font("sans-serif",Font.PLAIN,11));
		domainAxis.setLabelFont(new Font("宋体",Font.PLAIN,12));
		rAxis.setTickLabelFont(new Font("sans-serif",Font.PLAIN,12));
		rAxis.setLabelFont(new Font("宋体",Font.PLAIN,12));
		chart.getLegend().setItemFont(new Font("宋体",Font.PLAIN,12));*/
		//创建主题样式  
       StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
       //设置标题字体  
       standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));  
       //设置图例的字体  
       standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));  
       //设置轴向的字体  
       standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));  
       //应用主题样式  
       ChartFactory.setChartTheme(standardChartTheme);  
	}
}
