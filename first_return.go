package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

type firstReturn struct {
	sync.Mutex
	cond  *sync.Cond
	value int
}

func newFirstReturn() *firstReturn {
	fstReturn := firstReturn{}
	fstReturn.cond = sync.NewCond(&fstReturn)
	return &fstReturn
}

func request(id int, fstReturn *firstReturn) {
	sleepTime := 1 + rand.Intn(30)
	fmt.Printf("goroutine %d: sleep %ds\n", id, sleepTime)
	t := time.Duration(sleepTime) * time.Second
	time.Sleep(t)

	fstReturn.Lock()
	fstReturn.value = sleepTime
	fstReturn.cond.Signal()
	fstReturn.Unlock()
}

func gateway(numReplicas int) int {
	fstReturn := newFirstReturn()
	fstReturn.value = -1

	for i := 1; i <= numReplicas; i++ {
		go request(i, fstReturn)
	}

	fstReturn.Lock()
	fstReturn.cond.Wait()
	fstReturn.Unlock()

	return fstReturn.value
}

func main() {
	// set the seed for rand
	rand.Seed(time.Now().UTC().UnixNano())
	fistTime := gateway(3)
	fmt.Printf("First returned: %d\n", fistTime)
}
