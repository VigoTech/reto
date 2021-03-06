/*
 *
 * Copyright 2017 @ Santiago Rodríguez .
 *
 * License: MIT
 *
 * Project: vigoreto-201710
 *
 */
package es.rocasan.vigojug.reto201710;

import java.util.ArrayList;

/**
 * A App class to be a entry point. To call the main method, should be passed
 * first the target, and later the capacities of jugs
 * @author Santiago Rodríguez 
 */
public class App {
	public static void main(String[] args) {
		if(args.length < 3) {
			System.out.println("Not enaugh params");
			System.out.println("Define first the target to achieve, later the jugs' capacities (min 2 jugs)");
			System.out.println("At the end you can define the debug/verbose option (-v) and/or the limit of attempts (-N, where N is a number)");
			System.out.println("Example: target 6, jug 4, jug 7 in less than 1000 attempts: java es.rocasan.vigojug.reto201710.App 6 4 7 -1000");
		}
		else {
			Integer target = null;
			ArrayList<Jug> jugs = new ArrayList<Jug>();
			Boolean debug = false;
			Integer limit = 10000;
			int n = 0;
			try {
				for(n=0; n<args.length; n++) {
					if(n==0) {
						target = Integer.parseInt(args[n]);
					}
					else {
						if(args[n].charAt(0)=='-') {
							if("-v".equals(args[n])) {
								debug = true;
							}
							else {
								limit = Integer.parseInt(args[n].substring(1, args[n].length()));
							}
						}
						else {
							jugs.add(new Jug(Integer.parseInt(args[n])));
						}
					}
				}
				Stage stage = new Stage(target, jugs.toArray(new Jug[jugs.size()]), limit);
				stage.debug = debug;
				System.out.println(stage.solve());
			}
			catch(NumberFormatException e) {
				System.out.println("Parameter "+(n+1)+" '"+args[n]+"' is not valid.");
			}
		}
	}
}
