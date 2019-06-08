package main

import (
	"fmt"
	"runtime"
	"sync"
)

func main() {
	var wg sync.WaitGroup
	var m runtime.MemStats

	wg.Add(1)
	runtime.ReadMemStats(&m)
	memMainStart := m.TotalAlloc

	go func() {
		defer wg.Done()
		runtime.ReadMemStats(&m)
		memGoroutineStart := m.TotalAlloc
		fmt.Print((memGoroutineStart - memMainStart))
	}()

	wg.Wait()
}