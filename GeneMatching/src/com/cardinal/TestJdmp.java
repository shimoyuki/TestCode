package com.cardinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ArffLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class TestJdmp {
	private Instances data;
	
	public Instances getData() {
		return data;
	}

	public void setData(Instances data) {
		this.data = data;
	}

	public void createInstances(String path) {
		try {
			BufferedReader reader = new BufferedReader( new FileReader(path)); 
			data = new Instances(reader); 
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
		data.setClassIndex(data.numAttributes() - 1); 
	}
	
	public void useFilter(){
		try {
			String[] options = Utils.splitOptions("-R 1");
			Remove remove = new Remove();
			remove.setOptions(options);
			remove.setInputFormat(data);
			data = Filter.useFilter(data, remove);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void buildCalssifier(){
		String[] options = new String[1]; 
		options[0] = "-U"; // unpruned tree 
		J48 tree = new J48(); // new instance of tree 
		try {
			tree.setOptions(options); // set the options 
			tree.buildClassifier(data); // build classifier 
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void useClassifier(String path){
		try {
			ArffLoader loader = new ArffLoader(); 
			loader.setFile(new File(path)); 
			Instances structure = loader.getStructure();
			structure.setClassIndex(structure.numAttributes()-1);
			// train NaiveBayes 
			NaiveBayesUpdateable nb = new NaiveBayesUpdateable(); 
			nb.buildClassifier(structure); 
			    Instance current; 
			    while ((current = loader.getNextInstance(structure)) != null) 
			    nb.updateClassifier(current);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
	}

	public static void main(String[] args) {
		String path = "Resource/weather.nominal.arff";
		TestJdmp tj = new TestJdmp();
			tj.createInstances(path);
			tj.useClassifier(path);

	}

}
