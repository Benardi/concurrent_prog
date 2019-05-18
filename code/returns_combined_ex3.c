#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

void* request(void)
{
  int sleep_time;

  sleep_time = 1 + rand() % 30; 
  sleep (sleep_time); 
  printf("Sleep time was: %d\n",sleep_time);

  pthread_exit(sleep_time); 
}

int gateway(int num_replicas)
{
  int i, sleep;
  void *returnValue;
  int total_sleep = 0;
  
  pthread_t pthreads[num_replicas];
  
  for(i = 0; i < num_replicas; i++) {
    pthread_create(&pthreads[i], NULL, &request, NULL); 
  
  }

  for(i = 0; i < 5; i++) {
    pthread_join(pthreads[i], &returnValue);
    sleep = (int) returnValue;
    total_sleep += sleep;
  }
  return total_sleep;
}


int main(int argc, char *argv[])
{
  void *ret;
  /* Init random number generator*/
  int seed, result;
  seed = time(NULL);
  srand(seed);
  
  result = gateway(5);

  printf("Result was: %d\n",result);
  return 0;
}


