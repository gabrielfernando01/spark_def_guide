// =====================================================================================
// Chapter 2. A Gentle Introduction to Spark
$ spark-shell

spark

// Perform the simple task of creating a range of numbers.
val myRange = spark.range(1000).toDF("number")

// Let's perform simple transformation to find all enven numbers in our currentDataFrame
val divisBy2 = myRange.where("number % 2 = 0")

// The simplest action count which gives us the total number of record in the DataFrame
divisBy2.count()

// End-to-End Example
$ head Documents/de/spark_dg/data/flight-data/csv/2015-summary.csv

val flightData2015 = spark.read
  .option("interSchema", "true")
  .option("header", "true")
  .csv("Documents/de/spark_dg/data/flight-data/csv/2015-summary.csv")

// If we perform the take action on the DataFrame
flightData2015.take(3)

flightData2015.sort("count").explain()

// By default, when we perform a shuffle, Spark outputs 200 shuffle partions.
spark.conf.set("spark.sql.shuffle.partions", "5")

flightData2015.sort("count").take(2)

// DataFrame and SQL
// You can make any DataFrame into a table or view withone simple method call:
flightData2015.createOrReplaceTempView("flight_data_2015")

val sqlWay = spark.sql("""

SELECT DEST_COUNTRY_NAME, count(1)
FROM flight_data_2015
GROUP BY DEST_COUNTRY_NAME
""")

val dataFrameWay = flightData2015
  .groupBy('DEST_COUNTRY_NAME)
  .count()

sqlWay.explain
dataFrameWay.explain

// Let’s pull out some interesting statistics from our data

spark.sql("SELECT max(count) from flight_data_2015").take(1)

import org.apache.spark.sql.functions.max

flightData2015.select(max("count")).take(1)

// find out the top five destination countries in the dataset
val maxSql = spark.sql("""
SELECT DEST_COUNTRY_NAME, sum(count) as destination_total
FROM flight_data_2015
GROUP BY DEST_COUNTRY_NAME
ORDER BY sum(count) DESC
LIMIT 5
""")

maxSql.show()

// DataFrame syntax that is semantically similar
import org.apache.spark.sql.functions._

// how show types of table
flightData2015.printSchema()

// now change cout type
val flightData2015_0 = flightData2015.withColumn("count", col("count").cast("integer"))

// how show types of table
flightData2015_0.printSchema()

flightData2015_0
  .groupBy("DEST_COUNTRY_NAME")
  .sum("count")
  .withColumnRenamed("sum(count)", "destination_total")
  .sort(desc("destination_total"))
  .limit(5)
  .show()

// let’s look at the explain plan for the previous query
flightData2015_0
  .groupBy("DEST_COUNTRY_NAME")
  .sum("count")
  .withColumnRenamed("sum(count)", "destination_total")
  .sort(desc("destination_total"))
  .limit(5)
  .explain()

// Chapter 3. A Tour of Spark’s Toolset
// Running Production Applications
// using the following command in the directory where you downloaded Spark
./bin/spark-submit \
--class org.apache.spark.examples.SparkPi \
--master local \
examples/jars/spark-examples_2.12-3.3.1.jar

// We can also run a Python version of the application
./bin/spark-submit \
--master local \
examples/src/main/python/pi.py

// Datasets: Type-Safe Structured APIs
case class Flight(DEST_COUNTRY_NAME: String,
                  ORIGIN_COUNTRY_NAME: String,
                  count: BigInt)

val flightDF = spark.read
  .parquet("Documents/de/spark_dg/data/flight-data/parquet/2010-summary.parquet/")

val flights = flightDF.as[Flight]   //Dataset class is parameterized with the type of object contained inside [T]

flights
  .take(5)
  .filter(flight_row => flight_row.ORIGIN_COUNTRY_NAME != "Canada")
  .map(fr => Flight(fr.DEST_COUNTRY_NAME, fr.ORIGIN_COUNTRY_NAME, fr.count + 5))

// Caching Data for Faster Accesss

/* val DF1 = spark.read.format("csv")
  .option("interSchema", "true")
  .option("header", "true")
  .csv("data/flight-data/csv/2015-summary.csv")

val DF2 = DF1.groupBy("DEST_COUNTRY_NAME").count().collect()
val DF3 = DF1.groupBy("ORIGIN_COUNTRY_NAME").count().collect()
val df4 = DF1.groupBy("count").count().collect()    */

// Structured Streming
// We’ll also create a schema from this static dataset
val staticDataFrame = spark.read.format("csv")
  .option("header", "true")
  .option("inferSchema", "true")
  .load("Documents/de/spark_dg/data/retail-data/by-day/*.csv")

staticDataFrame.createOrReplaceTempView("retail_data")
val staticSchema = staticDataFrame.schema

import org.apache.spark.sql.functions.{window, column, desc, col}

staticDataFrame
  .selectExpr(
    "CustomerId",
    "(UnitPrice * Quantity) as total_cost",
    "InvoiceDate")
  .groupBy(
    col("CustomerId"), window(col("InvoiceDate"), "1 day"))
  .sum("total_cost")
  .show(5)

// that’s going to be a better fit for local mode
spark.conf.set("spark.sql.shuffle.partitions", "5")

// let’s take a look at the streaming code!
val streamingDataFrame = spark.readStream
  .schema(staticSchema)
  .option("maxFilesPerTrigger", 1)
  .format("csv")
  .option("header", "true")
  .load("Documents/de/spark_dg/data/retail-data/by-day/*.csv")

// Now we can whether our DataFrame is streming:
streamingDataFrame.isStreaming

val purchaseByCustomerPerHour = streamingDataFrame
  .selectExpr(
    "CustomerId",
    "(UnitPrice * Quantity) as total_cost",
    "InvoiceDate")
  .groupBy(
    $"CustomerId", window($"InvoiceDate", "1 day"))
  .sum("total_cost")


