package main

import (
	"fmt"
	"math/rand"
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

	wg.Wait()

	return fstReturn.value
}

func main() {
	// set the seed for rand
	rand.Seed(time.Now().UTC().UnixNano())
	combinedTime := gateway(3)
	fmt.Printf("Combined return: %d\n", combinedTime)
}
