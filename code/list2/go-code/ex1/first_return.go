package main

import (
	"fmt"
	"math/rand"
	"os"
	"strconv"
	"time"
)

func request(id int, fstReturn chan int) {
	sleepTime := 1 + rand.Intn(30)
	fmt.Printf("goroutine %d: sleep %ds\n", id, sleepTime)
	t := time.Duration(sleepTime) * time.Second
	time.Sleep(t)

	fstReturn <- sleepTime
}

func gateway(numReplicas int) int {
	fstReturn := make(chan int)
	for i := 1; i <= numReplicas; i++ {
		go request(i, fstReturn)
	}

	select {
	case value := <-fstReturn:
		return value
	}
}

func main() {
	// set the seed for rand
	rand.Seed(time.Now().UTC().UnixNano())
	numReplicas := 3
	if len(os.Args) > 1 {
		// convert command line arg to int
		numReplicas, _ = strconv.Atoi(os.Args[1])
	}
	fistTime := gateway(numReplicas)
	fmt.Printf("First returned: %d\n", fistTime)
}
