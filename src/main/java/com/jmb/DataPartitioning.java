package com.jmb;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.apache.spark.sql.functions.*;


public class DataPartitioning {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataPartitioning.class);
    private static final String SPARK_FILES_FORMAT = "csv";
    private static final String PATH_RESOURCES = "src/main/resources/spark-data/sales_information.csv";

    public static void main(String[] args) throws Exception {

        LOGGER.info("Application starting up");
        DataPartitioning app = new DataPartitioning();
        app.init();
        LOGGER.info("Application gracefully exiting...");
    }

    private void init() throws Exception {
        //Create the Spark Session
        SparkSession session = SparkSession.builder()
                .appName("DataPartitioning")
                .master("local").getOrCreate();

        //Ingest data from CSV file into a DataFrame
        Dataset<Row> df = session.read()
                .format(SPARK_FILES_FORMAT)
                .option("header", "true")
                .option("inferSchema", "true")
                .load(PATH_RESOURCES);

        //Show first 5 records of the Raw ingested DataSet
        df.show(5);

        //Show the amount of partitions
        LOGGER.info("Number of Partitions " + df.javaRDD().getNumPartitions());

    }

}
