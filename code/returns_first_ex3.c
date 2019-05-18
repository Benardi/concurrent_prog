#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <pthread.h>
#include <stdbool.h>

bool one_finished = false;
int first_time = -1; 

void* request(void* args)
{
  int sleep_time;
  int my_id = (intptr_t) args;  

  sleep_time = 1 + rand() % 30; 
  sleep (sleep_time); 
  printf("Sleep time of thread %d: %ds\n", my_id, sleep_time);
  one_finished = true;
  first_time = sleep_time;
 
  pthread_exit((void*) (intptr_t) sleep_time); 
}

int gateway(int num_replicas)
{
  void *returnValue;
  int i, first_returned;
  
  pthread_t pthreads[num_replicas];
  
  for(i = 0; i < num_replicas; i++) {
    pthread_create(&pthreads[i], NULL, &request, (void*) (intptr_t) i); 
  
  }
  
  while(true) {
  
    if(one_finished) { 
      return first_time;
    }

  }


}


int main(int argc, char *argv[])
{
  void *ret;
  /* Init random number generator*/
  int seed, result;
  seed = time(NULL);
  srand(seed);
  
  result = gateway(5);

  printf("Result: %ds\n",result);
  return 0;
}


