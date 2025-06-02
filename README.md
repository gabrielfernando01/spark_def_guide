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



