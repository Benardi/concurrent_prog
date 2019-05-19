#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <pthread.h>
#include <unistd.h>
#include <stdbool.h>
#include <signal.h>

void* request(void* args);
int gateway(int num_replicas);
void signalrm_handler(int sig);
int main(int argc, char *argv[]);

bool one_finished = false;
int first_time = -1; 

void* request(void* args)
{
  int sleep_time;
  int my_id = (intptr_t) args;  

  sleep_time = 1 + rand() % 30; 
  printf("Sleep time of thread %d: %ds\n", my_id, sleep_time);
  sleep (sleep_time); 
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


void signalrm_handler(int sig)
{
  printf("Result: %d\n", -1);
  exit(0);
}

int main(int argc, char *argv[])
{
  int seed, result, nthreads;

  /* Map alarm to its handler */
  signal(SIGALRM, signalrm_handler);

  /* Init random number generator*/
  seed = time(NULL);
  srand(seed);
  
  if(argc == 2) {
    nthreads = strtol(argv[1], NULL, 10);
  } else {
    nthreads = 2;
  } 

  printf("Number of threads: %d\n", nthreads);
  
  alarm(8);

  result = gateway(nthreads);
  printf("Result: %ds\n", result);

  return 0;
}


