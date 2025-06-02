![](https://raw.githubusercontent.com/gabrielfernando01/spark_def_guide/main/images/cover_readme.png)

# ⭐ Spark The Definitive Guide.
### by Bill Chambers & Matei Zaharia.

![](https://raw.githubusercontent.com/gabrielfernando01/spark_def_guide/main/images/cover_def_guide.png)

## 💊 Chapter 1. What is Apache Spark?

Apache Spark is a unifed computing engine and a set of libraries for parellel data processing on computer cluster.

![](https://raw.githubusercontent.com/gabrielfernando01/spark_def_guide/main/images/sparks_toolkit.png)

### ⚜️ History of Spark.

Apache Spark began at UC Berkeley in 2009 as the Spark research project, which was first published the following year in a paper entitled «Spark: Cluster Computing with Working Sets» by Matei Zaharia, Mosharaf Chowdhury, Michael Franklin, Scott Shenker, and Ion Stoica of the UC Berkeley AMPlab.

### 💫 Running Spark.

Before starting the installation we must check that we have Java ☕, Python 🐍, Scala 🟥 installed.

Bash
```
java --version
python3 --version
scala -version
```

### 📩 Downloading Spark Locally.

From the official site <a href="https://spark.apache.org/downloads.html">Download the latest version of Apache Spark with Hadoop</a>. Once downloaded, then:

Bash
```
cd ~/Download
tar -xf spark-*.tgz
mv spark-* /opt/spark		--set superuser permission for this directory
```

We must also add the following lines to assign the environment variables within the <code>.bashrc</code> or <code>.profile</code> file:

```
export SPARK_HOME=/opt/spark
export PATH=$PATH:$SPARK_HOME/bin
export PATH=$PATH:$SPARK_HOME/sbin
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export SCALA_HOME=/usr/local/share/scala
export PATH=$PATH:$SCALA_HOME/bin
export PYSPARK_PYTHON=/usr/bin/python3
export PYSPARK_DRIVER_PYTHON=jupyter
export PYSPARK_DRIVER_PYTHON_OPTS='notebook'
```

Once Spark is installed, we can launch it from the console using the command:

Bash
```
spark-shell
```

## Chapter 2. Gentle Introduction to Spark

### Spark's Basic Archictecture

Single machines do not have enough power and resources to perform computation on huge amounts of information (or the user probability does not have the time to wait for the computation to finish). A cluster, or group, of computers, pools the resources of many machines together, giving us the ability to use all the cumulative resources as if they were a single computer. Now, a group of machines alone in not powerful, you need a framework to coordinate work across them. Spark does just that, managing and coordination the execution of task on data across a cluster of computers.

The cluster of machines that Spark 💥 will use to execute tasks in managed by a cluster manager like:

- Local cluster mode.
- Spark's standalone cluster manager.
- YARN.
- Mesos.

![](https://raw.githubusercontent.com/gabrielfernando01/spark_def_guide/main/images/local_vs_standalone.png)

### Spark Application

Spark Application consist of a _driver_ process and a set of _executor process._ The driver process run your <code>main()</code> function, sits on a node in the cluster, and is responsible for three things:

- Maintaining information about the Spark Application.
- Responding to a user's program or input.
- Analyzing, distributing, and scheduling work across the executors.

**The drive process is absolutely essential. It's the heart of a Spark Application and maintains all relevant information during the lifetime of the application.**

The _exexutors_ are responsible for actually carrying out the work that the driver assigns them. This means that each executor is responsible for only two things:

- Excuting code assigned to it by the driver
- Reporting the state of the computation on that executor back to the driver node.

![](https://raw.githubusercontent.com/gabrielfernando01/spark_def_guide/main/images/driver_vs_managercluster.png)

In figure above, we can see the driver on the left and four executor on the right. In this diagram, we removed the concept of cluster node. The user can specify how many executors should fall on each node through configuration.

Here are the key points to understand about Spark Applications at this point:

- Spark employs a cluster manager that keeps track of the resources available.
- The driver process is responsible for executing the driver program’s commands across the executors to complete a given task.

### Spark's Language APIs

- Scala
- Java
- Python
- SQL
- R

### Spark's APIs

Although you can drive Spark from a variety of languages, what it makes avaible in those languages is worth mentioning. Spark has two funtadamental sets of APIs:

- The low-level «unstructured» APIs.
- The higher-level «structured» APIs.

### Startig Spark

When you start Spark in this interactive mode, you implicitly create a SparkSession that manages
the Spark Application. When you start it through a standalone application, you must create the
SparkSession object yourself in your application code.

### The SparkSession

As discussed in the beginning of this chapter, you control your Spark Application through a driver process called the SparkSession. The SparkSession instance is the way Spark executes user-defined manipulations across the cluster. There is a one-to-one correspondence between a SparkSession and a Spark Application. In Scala and Python, the variable is available as spark when you start the console. Let’s go ahead and look at the SparkSession in both Scala and/or Python:

```
spark
```

Let’s now perform the simple task of creating a range of numbers. This range of numbers is just like a named column in a spreadsheet:

```
val myRange = spark.range(1000).toDF("number")
```

### DataFrames

A DataFrame is the most common Structured API and simply represents a table of data with rows and columns. The list that defines the columns and the types within those columns is calledthe schema. You can think of a DataFrame as a spreadsheet with named columns. The figure below 👇🏼 illustrates the fundamental difference: a spreadsheet sits on one computer in one specific location, whereas a Spark DataFrame can span thousands of computers. The reason for putting the data on more than one computer should be intuitive: either the data is too large to fit on one machine or it would simply take too long to perform that computation on one machine.

![](https://raw.githubusercontent.com/gabrielfernando01/spark_def_guide/main/images/df.png)

---
Spark has several core abstractions: Datasets, DataFrames, SQL Tables, and Resilient Distributed
Datasets (RDDs). These different abstractions all represent distributed collections of data. The easiest
and most efficient are DataFrames, which are available in all languages. We cover Datasets at the end
of Part II, and RDDs in Part III.

---

**Partitions**

As DataFrames are a high-level API, the DataFrames parallelism will be executed automatically.

### Transformation

In Spark, the core data structures are _immutable_, meaning they cannot be changed after they’re created. This might seem like a strange concept at first: if you cannot change it, how are you supposed to use it? To «change» a DataFrame, you need to instruct Spark how you would like to modify it to do what you want. These instructions are called transformations. Let’s perform a simple transformation to find all even numbers in our current DataFrame:

```
val divisBy2 = myRange.where("number % 2 = 0")
```

Notice that these return no output. This is because we specified only an abstract transformation, and Spark will not act on transformations until we call an action. There are two types of transformation:

- Narrow dependency (narrow transformation) are those for which each input partition will contribute to only one output.
- Wide dependency (wide transformation) style transformation will have input partitions contributing to many output partitions.

![](https://raw.githubusercontent.com/gabrielfernando01/spark_def_guide/main/images/narrow_wide_trans.png)

**Lazy Evaluation**

Lazy evaulation means that Spark will wait until the very last moment to execute the graph of computation instructions.

### Actions

Transformations allow us to build up our logical transformation plan. To trigger the computation, we run an _action_.  An action instructs Spark to compute a result from a series of transformations. The simplest action is count, which gives us the total number of records in the DataFrame:

```
divisBy2.count()
```
There are three kinds of actions:

- Actions to view data in the console.
- Actions to collect data to native objects in the respective language.
- Actions to write to output data sources.

### SparkUI

You can monitor the progress of a job through the Spark web UI. The Spark UI is available on port 4040 of the driver node. If you are running in local mode, this will be _http://localhost:4040_. The Spark UI displays information on the state of your Spark jobs, its environment, and cluster state. It’s very useful, especially for tuning and debugging. The figure below 👇🏼 show an example UI for Spark job where two stages containig nine task were execute.

![](https://raw.githubusercontent.com/gabrielfernando01/spark_def_guide/main/images/ui.png)

### An End-to-End Example

We’ll use Spark to analyze some <a href="https://github.com/gabrielfernando01/spark_def_guide/tree/main/data/flight-data/csv">flight-data</a> from the United States Bureau of Transportation statistics.

Each file has a number of rows within it. These files are CSV files, meaning that they’re a semi-structured data format, with each row in the file representing a row in our future DataFrame:

```
$ head Documents/de/spark_dg/data/flight-data/csv/2015-summary.csv
```

To get the schema information, Spark reads in a little bit of the data an then attempts to parse the types in those rows according to the types available in Spark.

```
val flightData2015 = spark
  .read
  .option("inferSchema", "true")
  .option("header", "true")
  .csv("/data/flight-data/csv/2015-summary.csv")
```

The figure below 👇🏼 provides an ilustration of the CSV file being read into a DataFrame and then being converted into a local array or list of rows.

![](https://raw.githubusercontent.com/gabrielfernando01/spark_def_guide/main/images/read_csv.png)

