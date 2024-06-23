# Spark The Definitive Guide
### by Bill Chambers & Matei Zaharia

![](https://raw.githubusercontent.com/gabrielfernando01/spark_def_guide/main/images/cover_def_guide.png)

## Chapter 1. What is Apache Spark?

Apache Spark is a unifed computing engine and a set of libraries for parellel data processing on computer cluster.

![](https://raw.githubusercontent.com/gabrielfernando01/spark_def_guide/main/images/sparks_toolkit.png)

### History of Spark

Apache Spark began at UC Berkeley in 2009 as the Spark research project, which was first
published the following year in a paper entitled “Spark: Cluster Computing with Working Sets”
by Matei Zaharia, Mosharaf Chowdhury, Michael Franklin, Scott Shenker, and Ion Stoica of the
UC Berkeley AMPlab.

Once Spark is installed, we can launch it from the console using the command:

```
$ spark-shell
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

### Spark's APIs

Although you can drive Spark from a variety of languages, what it makes avaible in those languages is worth mentioning. Spark has two funtadamental sets of APIs:

- The low-level "unstructured" APIs.
- The higher-level structured APIs.

