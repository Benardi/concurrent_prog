package main
import (
  "os"
  "fmt"
  "time"
  "sync"
)

var wg sync.WaitGroup

func sub_routine() {
  defer wg.Done()
  time.Sleep(3 * time.Second)
}

func main() {
  args := os.Args[1:]
  // Command line argument (Number of goroutines)
  nroutines := args[0]

  for i := 0; i < nroutines; i++ {
    wg.Add(1)
    go sub_routine()
  }

  wg.Wait()

}
