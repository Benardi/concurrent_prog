package main

import (
	"fmt"
	"math/rand"
	"os"
	"strconv"
	"time"
)

func request(id int, results chan int) {
	sleepTime := 1 + rand.Intn(30)
	fmt.Printf("goroutine %d: sleep %ds\n", id, sleepTime)
	t := time.Duration(sleepTime) * time.Second
	time.Sleep(t)

	results <- sleepTime
}

func gateway(numReplicas int) int {
	results := make(chan int, numReplicas)

	for i := 1; i <= numReplicas; i++ {
		go request(i, results)
	}

	combinedResults := 0
	for i := 1; i <= numReplicas; i++ {
		select {
		case currentResult := <-results:
			combinedResults += currentResult
		}
	}

	return combinedResults
}

func main() {
	// set the seed for rand
	rand.Seed(time.Now().UTC().UnixNano())
	// convert command line arg to int
	numReplicas, _ := strconv.Atoi(os.Args[1])
	combinedTime := gateway(numReplicas)
	fmt.Printf("Combined return: %d\n", combinedTime)
}
