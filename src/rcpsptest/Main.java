package rcpsptest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Dan Princ
 * @date 17.4.2013
 */

public class Main {
    private static int maxDuration ;
    private static int activities;
    private static int[] resources;
    public static void main(String[] args) throws Exception {
	
	Activity a1 = new Activity(1, 4, new int[] {2, 0});
	Activity a2 = new Activity(2, 3, new int[] {3, 0});
	Activity a3 = new Activity(3, 2, new int[] {1, 0});
	Activity a4 = new Activity(4, 5, new int[] {1, 0});
	Activity a5 = new Activity(5, 2, new int[] {4, 0});
	
	Activity a6 = new Activity(6, 2, new int[] {2, 0});
	Activity a7 = new Activity(7, 2, new int[] {0, 8});
	
	a1.addNext(a2).addNext(a4).addNext(a6);
	a2.addNext(a3);
	a4.addNext(a5).addNext(a7);
	
	Activity start = a1;
	activities = 7;
	resources = new int[] {4, 8};
	maxDuration = 5;
	
//	Activity start = parsePSPlibData("data/j301_7.sm");
	
	Rcpsp sheduling = new Rcpsp(start, activities, resources, maxDuration);
	sheduling.search();
	System.out.println(sheduling);

    }
    


}
