可见性、原子性和顺序性
父线程调用子线程start方法之前的所有内存操作将对子线程可见；
父线程调用子线程的join方法等待子线程完成之后，子线程所有的内存操作对父线程均是可见的；
A线程对共享变量进行加锁synchronized写的值，B线程肯定能看到

有问题的代码不表示立即能测试证明出来，而是通过逻辑分析出来的。测试用例只能证明此时此环境没问题。可能只是运气好而已。

并发编程时请牢记：用原理说事，你看到的测试结果不一定是对的！