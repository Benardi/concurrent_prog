package main

import (
	"fmt"
	"math/rand"
	"os"
	"strconv"
	"sync"
	"time"
)

type firstReturn struct {
	sync.Mutex
	value int
}

func request(id int, fstReturn *firstReturn, wg *sync.WaitGroup) {
	sleepTime := 1 + rand.Intn(30)
	fmt.Printf("goroutine %d: sleep %ds\n", id, sleepTime)
	t := time.Duration(sleepTime) * time.Second
	time.Sleep(t)

	fstReturn.Lock()
	fstReturn.value += sleepTime
	fstReturn.Unlock()
	wg.Done()
}

func gateway(numReplicas int) int {
	fstReturn := &firstReturn{value: 0}

	var wg sync.WaitGroup
	wg.Add(numReplicas)

	for i := 1; i <= numReplicas; i++ {
		go request(i, fstReturn, &wg)
	}

	timeout := 16
	sleepTime := time.Duration(timeout) * time.Second
	c := make(chan struct{})

	go func() {
		defer close(c)
		wg.Wait()
	}()

	select {
	case <-c:
		return fstReturn.value
	case <-time.After(sleepTime):
		return -1
	}
}

func main() {
	// set the seed for rand
	rand.Seed(time.Now().UTC().UnixNano())
	// convert command line arg to int
	numReplicas := 2
	if len(os.Args) > 1 {
		numReplicas, _ = strconv.Atoi(os.Args[1])
	}
	combinedTime := gateway(numReplicas)
	if combinedTime > 0 {
		fmt.Printf("Combined return: %d\n", combinedTime)
	} else {
		fmt.Printf("Gateway returned -1\n")
	}
}
