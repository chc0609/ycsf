package TSP;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TspProblem {
	public static int maxGenerations = 500;		//����������
	public static void main(String[] args) throws IOException {
		int numCities=20;
		City cities[]=new City[numCities];
		for(int cityIndex=0;cityIndex<numCities;cityIndex++){
			int x=(int)(100*Math.random());
			int y=(int)(100*Math.random());		//����100�����и������������
			cities[cityIndex]=new City(x,y);
		}
//	City cities[]=TspProblem.init("c://data.txt", 6);
		//initial GA��ʼ��GA
		GeneticAlgorithm ga=new GeneticAlgorithm(30,0.01,0.95,3,5);    //20 0.05 0.9 2 3 
		                                                                  //48��������33522   150 0.01 0.95 25 15
		Population p=ga.initPopulation(cities.length);
		//������Ⱥ
		ga.evaluatePopulationFitness(p, cities);
		int generation=1;
		while(ga.isTerminationConditionMet(generation,maxGenerations)==false){
			//���ô���Ⱥ����Ӧ����ߵĸ������·�����ٴ�ӡ����·���ľ���
			Route route=new Route(p.getFittest(0),cities);
			System.out.println("��"+generation+"�����Ž�:"+route.getDistance());
			//����
			p=ga.crossoverPopulation(p);
			//����
			p=ga.mutatePopulation(p);
			//����
			ga.evaluatePopulationFitness(p, cities);
			generation++;
		}
		//��ӡ���
		Route route=new Route(p.getFittest(0),cities);
		System.out.println(maxGenerations+"�����������Ϊ"+route.getDistance());
		System.out.println("����·��Ϊ"+p.getFittest(0));
	}
	 private static City[] init(String filename,int cityNum) throws IOException {    //��ȡ�ļ��е�����
	        // ��ȡ����   
	        String strbuff;  
	        BufferedReader data = new BufferedReader(new InputStreamReader(  
	                new FileInputStream(filename)));
	        City cities[]=new City[cityNum];
	        for (int i = 0; i < cityNum; i++) {  
	            // ��ȡһ�����ݣ����ݸ�ʽ1 6734 1453  
	            strbuff = data.readLine();  
	            // �ַ��ָ�  
	            String[] strcol = strbuff.split(" ");
	            City c=new City(Integer.valueOf(strcol[1]),Integer.valueOf(strcol[2]));
	            cities[i]=c;
	        }  
	        data.close();  	
	        return cities;
}
}