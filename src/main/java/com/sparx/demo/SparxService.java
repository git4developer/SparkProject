package com.sparx.demo;

import java.io.IOException;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SparxService {

	@Autowired
	private SparkSession spark;

//	Logger log = LoggerFactory.getLogger(SparxService.class);

	public ResponseEntity<String> readWrite() throws EncryptedDocumentException, IOException {

		// spark.sparkContext().parallelize(null, 0, null)

		Properties connectionProperties = new Properties();
		connectionProperties.put("user", "inferyx");
		connectionProperties.put("password", "inferyx");
		connectionProperties.put("driver", "com.mysql.cj.jdbc.Driver");
		connectionProperties.put("url", "jdbc:mysql://192.168.1.135:3306/sparx");
		//connectionProperties.put("url", "jdbc:mysql://localhost:3306/sparx");

		// for dockerise

		StructType schema = new StructType(
				new StructField[] { new StructField("Username", DataTypes.StringType, true, Metadata.empty()),
						new StructField("Identifier", DataTypes.IntegerType, true, Metadata.empty()),
						new StructField("First name", DataTypes.StringType, true, Metadata.empty()),
						new StructField("Last name", DataTypes.StringType, true, Metadata.empty()),

				// Add more fields as needed
				});

		String filePath = "/home/inferyx/Downloads/username.csv";

		Dataset<Row> csvDataSet = spark.read()
				.option("header","true")
				.option("delimiter", ";").
				//option("inferSchema","true")-->   Unable to infer schema for CSV. It must be specified manually.
				schema(schema)
				.csv(filePath);	
		
		
		
		
	//	spark.read().option("header", "true").option("header","true").option("header","true").option("header","true")  
	              
		                      
		
		

//for normal csv application csvFile

//		Dataset<Row> csvDataSet = spark.read().option("header", "true") // Use first row as header
//				.option("inferSchema", "true") // Infer data types automatically
//
//				.csv("/home/inferyx/Downloads/nw.csv");
		// .csv("file:///home/inferyx/Downloads/nw.csv");
		// .csv("file:///home/inferyx/Downloads/nw.csv");

		Dataset<Row> csvDataFrame = csvDataSet.toDF();
		csvDataFrame.createOrReplaceTempView("sparkusercsv");

		if (spark.catalog().tableExists("sparkusercsv")) {
			System.out.println("Temporary view sparkusercsv has been created");
		} else {
			System.out.println("Temporary view sparkusercsv is not created");
		}

		Dataset<Row> tempData = spark.sql("SELECT * from sparkusercsv");
		//
//				// tempData.show();
		//
//				// this piece of code
//				// write csv_file_data into mysql database
//				// help of DataFrame
		//192.168.1.135
		                                           //if using localhost--> got error connection refuse
		csvDataFrame.write().mode(SaveMode.Append).jdbc("jdbc:mysql://192.168.1.135:3306/sparx", "sparkusercsv",
				connectionProperties);

		Dataset<Row> jdbc = spark.read().jdbc("jdbc:mysql://192.168.1.135:3306/sparx", "sparkusercsv",
				connectionProperties); // // jdbc.show(); // // jdbc.

		csvDataFrame.show();
		tempData.show();
		jdbc.show();

		return ResponseEntity.ok("new chnages done for dockerising spring boot aparx application");

		// for normal csv to database table creation

//		System.out.println("checking it is working or not");
//		String[] columns = csvDataSet.columns();
//
//		for (String str : columns) {
//			Dataset<Row> sort = csvDataSet.sort(str);
//			Column apply = sort.apply(str);
//
//			System.out.println(apply.desc());
//		}
//		csvDataSet.printSchema();

		// csvDataSet.show();// ->only show top 20 result of csv file

		// sort column value in asc order
//		Dataset<Row> sort = csvDataSet.sort("transaction_id");
//		sort.show(10);
		// csvDataSet.show(10);

//		Dataset<Row> tempData = spark.sql("SELECT * from Tempcsv");
//
//		// tempData.show();
//
//		// this piece of code
//		// write csv_file_data into mysql database
//		// help of DataFrame
//		csvDataFrame.write().mode(SaveMode.Overwrite).jdbc("jdbc:mysql://localhost:3306/sparx", "sparkcsv",
//				connectionProperties);
//
//		Dataset<Row> jdbc = spark.read().jdbc("jdbc:mysql://localhost:3306/sparx", "sparkcsv", connectionProperties);
//		// jdbc.show();
//		// jdbc.
//
//		return ResponseEntity.ok("reading done");
	}

}
