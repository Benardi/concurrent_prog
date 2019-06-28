package main
import (
    "fmt"
    "sync"
    "time"
)

const n_eating = 10
var fork [n_eating]sync.Mutex
var exit int
func phil(id int) {
    for i := 0; i < 2; i++ {
        if (id+i)%2 == 0 {
            fork[id].Lock()
            fmt.Println("P[", id, "] picked up Right Fork")
            time.Sleep(time.Duration(1) * time.Second)
        } else {
            fork[(id+1)%5].Lock()
            fmt.Println("P[", id, "] picked up Left Fork")
            time.Sleep(time.Duration(1) * time.Second)
        }
    }
    fork[id].Unlock()
    fork[(id+1)%5].Unlock()
    fmt.Println("P[", id, "] ate and dropped the forks")
    exit++
}
func main() {
    fmt.Println("Dining Philosophers Problem")
    for i := 0; i < n_eating; i++ {
        go phil(i)
    }
    for exit != n_eating { /* loop wait till all threads are over */
    }
}
