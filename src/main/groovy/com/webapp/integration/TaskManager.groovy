package com.webapp.integration

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created with IntelliJ IDEA.
 * User: u1cc32
 */
class TaskManager {



    def concurrentRun(int interval, int concurrencySize) {
        ExecutorService pool = Executors.newFixedThreadPool(concurrencySize)
        println "concurrencySize: " + concurrencySize


        (0 .. concurrencySize).each{ pool.submit(new MqSyncClient(it)) }

        pool.shutdown
    }

/*
    def concurrentRun(int interval, int concurrencySize) {
        ExecutorService pool = Executors.newFixedThreadPool(concurrencySize)
        println "concurrencySize: " + concurrencySize

       // def doIt = {f-> pool.submit(f as Callable)}

        //def mywork = {n-> println " do work #" + n}
        def mywork = {println " do work "}

        //(0 .. concurrencySize).each{doIt(work(it))}
        pool.submit(mywork())

        pool.shutdown
    }
*/


/*
    def concurrentRun(int interval, int concurrencySize) {
    def CUTOFF = 12    // not worth parallelizing for small n
    def THREADS = 100

    println "Calculating Fibonacci sequence in parallel..."
    def serialFib = { Integer n -> (n < 2) ? n : serialFib(n-1) + serialFib(n-2) }
    def pool = Executors.newFixedThreadPool(THREADS)
    def defer = { c -> pool.submit(c as Callable) }
    def fib = { Integer n ->
      if (n < CUTOFF) return serialFib(n)
      def left = defer{ fib(n-1) }
      def right = defer{ fib(n-2) }
      left.get() + right.get()
    }

    (8..16).each{ n -> println "n=$n => ${fib(n)}" }
    pool.shutdown()
*/
}
