package TSP;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TspProblem {
	public static int maxGenerations = 500;		//最大迭代次数
	public static void main(String[] args) throws IOException {
		int numCities=20;
		City cities[]=new City[numCities];
		for(int cityIndex=0;cityIndex<numCities;cityIndex++){
			int x=(int)(100*Math.random());
			int y=(int)(100*Math.random());		//给这100个城市赋予随机的坐标
			cities[cityIndex]=new City(x,y);
		}
//	City cities[]=TspProblem.init("c://data.txt", 6);
		//initial GA初始化GA
		GeneticAlgorithm ga=new GeneticAlgorithm(30,0.01,0.95,3,5);    //20 0.05 0.9 2 3 
		                                                                  //48城市最优33522   150 0.01 0.95 25 15
		Population p=ga.initPopulation(cities.length);
		//评估种群
		ga.evaluatePopulationFitness(p, cities);
		int generation=1;
		while(ga.isTerminationConditionMet(generation,maxGenerations)==false){
			//将该代种群中适应度最高的个体放入路径，再打印出该路径的距离
			Route route=new Route(p.getFittest(0),cities);
			System.out.println("第"+generation+"代最优解:"+route.getDistance());
			//交叉
			p=ga.crossoverPopulation(p);
			//变异
			p=ga.mutatePopulation(p);
			//评估
			ga.evaluatePopulationFitness(p, cities);
			generation++;
		}
		//打印结果
		Route route=new Route(p.getFittest(0),cities);
		System.out.println(maxGenerations+"代后满意距离为"+route.getDistance());
		System.out.println("满意路径为"+p.getFittest(0));
	}
	 private static City[] init(String filename,int cityNum) throws IOException {    //读取文件中的坐标
	        // 读取数据   
	        String strbuff;  
	        BufferedReader data = new BufferedReader(new InputStreamReader(  
	                new FileInputStream(filename)));
	        City cities[]=new City[cityNum];
	        for (int i = 0; i < cityNum; i++) {  
	            // 读取一行数据，数据格式1 6734 1453  
	            strbuff = data.readLine();  
	            // 字符分割  
	            String[] strcol = strbuff.split(" ");
	            City c=new City(Integer.valueOf(strcol[1]),Integer.valueOf(strcol[2]));
	            cities[i]=c;
	        }  
	        data.close();  	
	        return cities;
}
}