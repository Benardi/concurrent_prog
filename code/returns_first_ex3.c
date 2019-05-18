#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <stdbool.h>

bool one_finished = false;
int first_time = -1; 

void* request(void)
{
  int sleep_time, my_id;
  
  sleep_time = 1 + rand() % 30; 
  sleep (sleep_time); 
  printf("Sleep time was: %d\n",sleep_time);
  one_finished = true;
  first_time = sleep_time;
 
  pthread_exit(sleep_time); 
}

int gateway(int num_replicas)
{
  void *returnValue;
  int i, first_returned;
  
  pthread_t pthreads[num_replicas];
  
  for(i = 0; i < num_replicas; i++) {
    pthread_create(&pthreads[i], NULL, &request, NULL); 
  
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

  printf("Result was: %d\n",result);
  return 0;
}


