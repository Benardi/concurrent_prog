#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdint.h>
#include <pthread.h>

int f_sleep_time = 0;
int n_released = 0;
pthread_mutex_t my_mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t myConVar = PTHREAD_COND_INITIALIZER;

void* request(void* args);
void* timeout(void* args);
int gateway(int num_replicas);
int main(int argc, char *argv[]);


void* request(void* args)
{
  int sleep_time;
  int my_id = (intptr_t) args;

  sleep_time = 1 + rand() % 30; 
  printf("Sleep time of thread %d: %ds\n", my_id, sleep_time);
  sleep (sleep_time); 
  
  pthread_mutex_lock(&my_mutex);    /* Lock mutex */
  
  f_sleep_time = sleep_time;
  pthread_cond_signal(&myConVar);
  
  pthread_mutex_unlock(&my_mutex);  /* Unlock mutex */
  
  pthread_exit((void*) (intptr_t) sleep_time); 
}

void* time_out(void* args)
{
  int max_time = 16;
  sleep (max_time);
  
  pthread_mutex_lock(&my_mutex);
  
  f_sleep_time = -1;
  pthread_cond_signal(&myConVar);
  
  pthread_mutex_unlock(&my_mutex);

  pthread_exit((void*) (intptr_t) max_time); 
}


int gateway(int num_replicas)
{
  int i, sleep;
  int total_sleep = 0;
  pthread_t timeout;
  pthread_t pthreads[num_replicas];
 
  pthread_create(&timeout, NULL, &time_out, NULL);

  for(i = 0; i < num_replicas; i++) {
    pthread_create(&pthreads[i], NULL, &request, (void*) (intptr_t) i); 
  }
  
  pthread_mutex_lock(&my_mutex);

  while(n_released < num_replicas) {           /* Escape if all threads are finished */
    pthread_cond_wait(&myConVar, &my_mutex);
    if(f_sleep_time == -1) {
      total_sleep = -1;
      pthread_mutex_unlock(&my_mutex);
      break;
    }
    total_sleep += f_sleep_time;
    n_released += 1; 
  } 

  pthread_mutex_unlock(&my_mutex);

  return total_sleep;
}


int main(int argc, char *argv[])
{
  int seed, result, nthreads;
  
  /* Init random number generator*/
  seed = time(NULL);
  srand(seed);
  
  if(argc == 2) {
    nthreads = strtol(argv[1], NULL, 10);
  } else {
    nthreads = 2;
  }

  printf("Number of Threads: %d\n", nthreads); 
  result = gateway(nthreads);
  printf("Result: %ds\n", result);

  return 0;
}


