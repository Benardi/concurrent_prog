# Concurrent Programming

> Code/Answers repository for experiments and exercises related to Concurrent Programming

Projeto da disciplina de Programação Concorrente 2019.1 (Ciência da Computação - UFCG)

Course Project Concurrent Programming 2019.1 (Computer Science program - UFCG)

### Prerequisites

* `Python 3.x`
* `Valgrind`
* `Golang`
* `Java`
* `Bash`
* `C`

## Running experiments

### Experiment Protected vs unprotected 

To run the first experiment first compile the following C scripts with the compiler you have available:

* `code/list1/c-code/ex1/protected_ex1.c` 
* `code/list1/c-code/ex1/unprotected_ex1.c`

Now, simply run the Bash script `code/utils/perf_exp.sh`:

```
 bash ./code/perf_exp.sh
```

* The resulting C.S.V file can be found at `output/list1/ex1/perf_result.csv`

<br>

### Experiment Goroutines vs Java-Threads

Run the following Bash script to generate the mem profiling logs:

```
 bash ./code/utils/generate_mem_logs.sh
```

The generated logs can be found at `output/list2/ex3/goroutine_javathread/`

Now, run the following Bash script to process the logs into a C.S.V file:

```
 bash ./code/utils/format_all_logs.sh
```

* The Bash script invokes the Python script `code/utils/format_mem_profiling.py`. For further information about it, run:

```
 python ./code/utils/format_mem_profiling.py -h
```

<br>

### Experiment on Collections' performance

Run the following Bash script to produce the results:

```
 bash ./code/utils/collection_perf.sh
```

* The resulting C.S.V file can be found at `output/list1/ex5/report.csv`
* The Bash script invokes the Java script `code/list1/java-code/ex5/Main.java`.

<br>

## Members

* **José Benardi de Souza Nunes** - *Data Analysis and implementation* - [Benardi](https://github.com/Benardi)
* **Gustavo Diniz Monteiro** - *Data Analysis and implementation* - [GustavoDinizMonteiro](https://github.com/GustavoDinizMonteiro)
* **Ruan Roberto Eloy Silveira** - *Implementation* - [ruanres](https://github.com/ruanres)

See also the list of [contributors](https://github.com/Benardi/concurrent_prog/contributors) who participated in this project.

<br>

## Built With

* [Valgrind](http://valgrind.org/) -  Instrumentation framework for building dynamic analysis tools
    + [Massif](http://valgrind.org/docs/manual/ms-manual.html/) -  Massif is a heap profiler. It measures how much heap memory your program uses
 * [ggplot2](http://valgrind.org/) - ggplot2 is a system for declaratively creating graphics, based on The Grammar of Graphics.