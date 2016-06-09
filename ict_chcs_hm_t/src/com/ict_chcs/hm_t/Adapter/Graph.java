package com.ict_chcs.hm_t.Adapter;

import java.util.ArrayList;
import java.util.List;

import com.ict_chcs.hm_t.R;
import com.ict_chcs.hm_t.hzgrapherlib.animation.GraphAnimation;
import com.ict_chcs.hm_t.hzgrapherlib.graphview.LineGraphView;
import com.ict_chcs.hm_t.hzgrapherlib.vo.GraphNameBox;
import com.ict_chcs.hm_t.hzgrapherlib.vo.linegraph.LineGraph;
import com.ict_chcs.hm_t.hzgrapherlib.vo.linegraph.LineGraphVO;
import com.ict_chcs.hm_t.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class Graph {

	private ViewGroup LineGraphView = null;
	private Context mContext = null;
	private LineGraphVO vo = null;
	
	public int paddingBottom = 50;// LineGraphVO.DEFAULT_PADDING;
	public int paddingTop = 50;// LineGraphVO.DEFAULT_PADDING;
	public int paddingLeft = 100;// LineGraphVO.DEFAULT_PADDING;
	public int paddingRight = 50;// LineGraphVO.DEFAULT_PADDING;

	// graph margin
	public int marginTop = 10;// LineGraphVO.DEFAULT_MARGIN_TOP;
	public int marginRight = 10;// LineGraphVO.DEFAULT_MARGIN_RIGHT;
	// max value
	public int maxValue = 1000;// LineGraphVO.DEFAULT_MAX_VALUE;
	// increment
	public int increment = 100;// LineGraphVO.DEFAULT_INCREMENT;

	public void setLineGraph(String name, Context context, float[] graph1, float[] graph2, float[] graph3, ViewGroup vg) {

		mContext = context;
		LineGraphView = vg;

		if (name.equalsIgnoreCase("CALORIES")) {
			vo = makeLineGraphExCaloriesSetting(graph1, graph2, graph3);

		}
		else if (name.equalsIgnoreCase("HEARTPULSE")) {
			vo = makeLineGraphExDistanceSetting(graph1, graph2, graph3);

		}
		else if (name.equalsIgnoreCase("TIME")) {
			vo = makeLineGraphExTimeSetting(graph1, graph2, graph3);

		}
		else if (name.equalsIgnoreCase("DISTANCE")) {
			vo = makeLineGraphExHeartPulseSetting(graph1, graph2, graph3);
		}
		else  {
			return;
		}

		// all setting
		LineGraphView.addView(new LineGraphView(mContext, vo));
	}

	private LineGraphVO makeLineGraphExCaloriesSetting(float[] graph1, float[] graph2, float[] graph3) {
		
		// GRAPH SETTING
		String[] legendArr = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }; // x
		// float[] graph1 = { 200, 100, 30, 50, 100, 200, 100, 300, 200, 100 };
		// // y
		// float[] graph2 = { 000, 100, 200, 10, 20, 50, 100, 300, 200, 100 };
		// float[] graph3 = { 200, 100, 300, 40, 000, 50, 100, 300, 200, 100 };

		List<LineGraph> arrGraph = new ArrayList<LineGraph>();

		arrGraph.add(new LineGraph("All", 0xaa66ff33, graph1));
		arrGraph.add(new LineGraph("Treadmill", 0xaa00ffff, graph2));
		arrGraph.add(new LineGraph("Bike", 0xaaff0066, graph3));

		LineGraphVO vo = new LineGraphVO(paddingBottom, paddingTop, paddingLeft, paddingRight, marginTop, marginRight,
				maxValue, increment, legendArr, arrGraph);

		// set animation
		vo.setAnimation(new GraphAnimation(GraphAnimation.LINEAR_ANIMATION, GraphAnimation.DEFAULT_DURATION));
		// set graph name box
		vo.setGraphNameBox(new GraphNameBox());
		// set draw graph region
		// vo.setDrawRegion(true);

		// use icon
		// arrGraph.add(new Graph(0xaa66ff33, graph1, R.drawable.icon1));
		// arrGraph.add(new Graph(0xaa00ffff, graph2, R.drawable.icon2));
		// arrGraph.add(new Graph(0xaaff0066, graph3, R.drawable.icon3));

		// LineGraphVO vo = new LineGraphVO(
		// paddingBottom, paddingTop, paddingLeft, paddingRight,
		// marginTop, marginRight, maxValue, increment, legendArr, arrGraph,
		// R.drawable.bg);
		return vo;
	}

	private LineGraphVO makeLineGraphExDistanceSetting(float[] graph1, float[] graph2, float[] graph3) {

		// GRAPH SETTING
		String[] legendArr = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }; // x
		// float[] graph1 = { 200, 100, 30, 50, 100, 200, 100, 300, 200, 100 };
		// // y
		// float[] graph2 = { 000, 100, 200, 10, 20, 50, 100, 300, 200, 100 };
		// float[] graph3 = { 200, 100, 300, 40, 000, 50, 100, 300, 200, 100 };

		List<LineGraph> arrGraph = new ArrayList<LineGraph>();

		arrGraph.add(new LineGraph("All", 0xaa66ff33, graph1));
		arrGraph.add(new LineGraph("Treadmill", 0xaa00ffff, graph2));
		arrGraph.add(new LineGraph("Bike", 0xaaff0066, graph3));

		LineGraphVO vo = new LineGraphVO(paddingBottom, paddingTop, paddingLeft, paddingRight, marginTop, marginRight,
				maxValue, increment, legendArr, arrGraph);

		// set animation
		vo.setAnimation(new GraphAnimation(GraphAnimation.LINEAR_ANIMATION, GraphAnimation.DEFAULT_DURATION));
		// set graph name box
		vo.setGraphNameBox(new GraphNameBox());
		// set draw graph region
		// vo.setDrawRegion(true);

		// use icon
		// arrGraph.add(new Graph(0xaa66ff33, graph1, R.drawable.icon1));
		// arrGraph.add(new Graph(0xaa00ffff, graph2, R.drawable.icon2));
		// arrGraph.add(new Graph(0xaaff0066, graph3, R.drawable.icon3));

		// LineGraphVO vo = new LineGraphVO(
		// paddingBottom, paddingTop, paddingLeft, paddingRight,
		// marginTop, marginRight, maxValue, increment, legendArr, arrGraph,
		// R.drawable.bg);
		return vo;
	}

	private LineGraphVO makeLineGraphExTimeSetting(float[] graph1, float[] graph2, float[] graph3) {
		
		// GRAPH SETTING
		String[] legendArr = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }; // x
		// float[] graph1 = { 200, 100, 30, 50, 100, 200, 100, 300, 200, 100 };
		// // y
		// float[] graph2 = { 000, 100, 200, 10, 20, 50, 100, 300, 200, 100 };
		// float[] graph3 = { 200, 100, 300, 40, 000, 50, 100, 300, 200, 100 };

		List<LineGraph> arrGraph = new ArrayList<LineGraph>();

		arrGraph.add(new LineGraph("All", 0xaa66ff33, graph1));
		arrGraph.add(new LineGraph("Treadmill", 0xaa00ffff, graph2));
		arrGraph.add(new LineGraph("Bike", 0xaaff0066, graph3));

		LineGraphVO vo = new LineGraphVO(paddingBottom, paddingTop, paddingLeft, paddingRight, marginTop, marginRight,
				maxValue, increment, legendArr, arrGraph);

		// set animation
		vo.setAnimation(new GraphAnimation(GraphAnimation.LINEAR_ANIMATION, GraphAnimation.DEFAULT_DURATION));
		// set graph name box
		vo.setGraphNameBox(new GraphNameBox());
		// set draw graph region
		// vo.setDrawRegion(true);

		// use icon
		// arrGraph.add(new Graph(0xaa66ff33, graph1, R.drawable.icon1));
		// arrGraph.add(new Graph(0xaa00ffff, graph2, R.drawable.icon2));
		// arrGraph.add(new Graph(0xaaff0066, graph3, R.drawable.icon3));

		// LineGraphVO vo = new LineGraphVO(
		// paddingBottom, paddingTop, paddingLeft, paddingRight,
		// marginTop, marginRight, maxValue, increment, legendArr, arrGraph,
		// R.drawable.bg);
		return vo;
	}

	private LineGraphVO makeLineGraphExHeartPulseSetting(float[] graph1, float[] graph2, float[] graph3) {
		
		// GRAPH SETTING
		String[] legendArr = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }; // x
		// float[] graph1 = { 200, 100, 30, 50, 100, 200, 100, 300, 200, 100 };
		// // y
		// float[] graph2 = { 000, 100, 200, 10, 20, 50, 100, 300, 200, 100 };
		// float[] graph3 = { 200, 100, 300, 40, 000, 50, 100, 300, 200, 100 };

		List<LineGraph> arrGraph = new ArrayList<LineGraph>();

		arrGraph.add(new LineGraph("All", 0xaa66ff33, graph1));
		arrGraph.add(new LineGraph("Treadmill", 0xaa00ffff, graph2));
		arrGraph.add(new LineGraph("Bike", 0xaaff0066, graph3));

		LineGraphVO vo = new LineGraphVO(paddingBottom, paddingTop, paddingLeft, paddingRight, marginTop, marginRight,
				maxValue, increment, legendArr, arrGraph);

		// set animation
		vo.setAnimation(new GraphAnimation(GraphAnimation.LINEAR_ANIMATION, GraphAnimation.DEFAULT_DURATION));
		// set graph name box
		vo.setGraphNameBox(new GraphNameBox());
		// set draw graph region
		// vo.setDrawRegion(true);

		// use icon
		// arrGraph.add(new Graph(0xaa66ff33, graph1, R.drawable.icon1));
		// arrGraph.add(new Graph(0xaa00ffff, graph2, R.drawable.icon2));
		// arrGraph.add(new Graph(0xaaff0066, graph3, R.drawable.icon3));

		// LineGraphVO vo = new LineGraphVO(
		// paddingBottom, paddingTop, paddingLeft, paddingRight,
		// marginTop, marginRight, maxValue, increment, legendArr, arrGraph,
		// R.drawable.bg);
		return vo;
	}
}
