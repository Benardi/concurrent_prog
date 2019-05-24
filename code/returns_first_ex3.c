#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdint.h>
#include <pthread.h>

int f_sleep_time = -1; 
pthread_mutex_t my_mutex = PTHREAD_MUTEX_INITIALIZER; /* mutex */
pthread_cond_t myConVar = PTHREAD_COND_INITIALIZER;   /* conditional variable */

void* request(void* args);
int gateway(int num_replicas);
int main(int argc, char *argv[]);

void* request(void* args)
{
  int sleep_time;
  int my_id = (intptr_t) args;  

  sleep_time = 1 + rand() % 30; 
  printf("Sleep time of thread %d: %ds\n", my_id, sleep_time);
  sleep (sleep_time); 

  pthread_mutex_lock(&my_mutex);   /* Lock mutex*/
  f_sleep_time = sleep_time;
  pthread_cond_signal(&myConVar); 
  pthread_mutex_unlock(&my_mutex); /* Unlock mutex */

  pthread_exit((void*) (intptr_t) sleep_time); 
}


int gateway(int num_replicas)
{
  int i, result;
  
  pthread_t pthreads[num_replicas];
  
  for(i = 0; i < num_replicas; i++) {
    pthread_create(&pthreads[i], NULL, &request, (void*) (intptr_t) i); 
  
  }

  pthread_mutex_lock(&my_mutex);            /* Lock mutex */

  while(f_sleep_time == -1) {                 /*  wait can end without any thread signaling */
    pthread_cond_wait(&myConVar, &my_mutex);  /* hence the while loop */
  }
  result = f_sleep_time;

  pthread_mutex_unlock(&my_mutex);          /*Unlock mutex*/

  return result;
}


int main(int argc, char *argv[])
{
  int seed, result, nthreads;
  
  /* Init random number generator*/
  seed = time(NULL);
  srand(seed);
 
  /* If receives one cmd line argument the argument is used as
   * number of threads, Else number of threads is 2 */ 
  if(argc == 2) {
    nthreads = strtol(argv[1], NULL, 10);
  } else {
    nthreads = 2;
  } 

  printf("Number of threads: %d\n",nthreads);
  result = gateway(nthreads);
  printf("Result: %ds\n",result);

  return 0;
}


