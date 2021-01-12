import org.junit.jupiter.api.*;

/**
 * The class SchedulerTest it is testing only the scheduler algorithms,
 * in order to do that it make sure that all the processes can fit into the memory
 */
class SchedulerTest {
    CPU cpu;

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class FCFSTests {
        void init(Process[] processes) {
            int[] blockSizes = new int[processes.length];
            for (int i = 0; i < blockSizes.length; i++) blockSizes[i] = processes[i].getMemoryRequirements();
            Scheduler scheduler = new FCFS();
            MMU mmu = new MMU(blockSizes, new FirstFit(blockSizes));
            CPU.clock = 0;
            cpu = new CPU(scheduler, mmu, processes);
            cpu.run();
        }

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        class Test1 {
            Process[] processes = {
                    new Process(0, 5, 10),
                    new Process(2, 2, 40),
                    new Process(3, 1, 25),
                    new Process(4, 3, 30)
            };

            @BeforeAll
            void initTest1() {
                init(processes);
            }

            @TestFactory
            @DisplayName("Process 0")
            @Order(0)
            DynamicTest[] Process_0() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(5, processes[0].getCompletionTime())),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(0.0, processes[0].getResponseTime())),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(5.0, processes[0].getTurnAroundTime())),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(0.0, processes[0].getWaitingTime())),
                };
            }

            @TestFactory
            @DisplayName("Process 1")
            @Order(1)
            DynamicTest[] Process_1() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(7, processes[1].getCompletionTime())),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(3.0, processes[1].getResponseTime())),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(5.0, processes[1].getTurnAroundTime())),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(3.0, processes[1].getWaitingTime())),
                };
            }

            @TestFactory
            @DisplayName("Process 2")
            @Order(2)
            DynamicTest[] Process_2() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(8, processes[2].getCompletionTime())),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(4.0, processes[2].getResponseTime())),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(5.0, processes[2].getTurnAroundTime())),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(4.0, processes[2].getWaitingTime())),
                };
            }

            @TestFactory
            @DisplayName("Process 3")
            @Order(3)
            DynamicTest[] Process_3() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(11, processes[3].getCompletionTime())),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(4.0, processes[3].getResponseTime())),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(7.0, processes[3].getTurnAroundTime())),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(4.0, processes[3].getWaitingTime())),
                };
            }
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class SJFTests {
        void init(Process[] processes) {
            int[] blockSizes = new int[processes.length];
            for (int i = 0; i < blockSizes.length; i++) blockSizes[i] = processes[i].getMemoryRequirements();
            Scheduler scheduler = new SJF();
            MMU mmu = new MMU(blockSizes, new FirstFit(blockSizes));
            CPU.clock = 0;
            cpu = new CPU(scheduler, mmu, processes);
            cpu.run();
        }

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        class Test1 {
            Process[] processes = {
                    new Process(0, 5, 10),
                    new Process(2, 2, 40),
                    new Process(3, 1, 25),
                    new Process(4, 3, 30)
            };

            @BeforeAll
            void initTest1() {
                init(processes);
            }

            @TestFactory
            @DisplayName("Process 0")
            @Order(0)
            DynamicTest[] Process_0() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(5, processes[0].getCompletionTime())),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(0.0, processes[0].getResponseTime())),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(5.0, processes[0].getTurnAroundTime())),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(0.0, processes[0].getWaitingTime())),
                };
            }

            @TestFactory
            @DisplayName("Process 2")
            @Order(1)
            DynamicTest[] Process_2() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(6, processes[2].getCompletionTime())),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(2.0, processes[2].getResponseTime())),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(3.0, processes[2].getTurnAroundTime())),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(2.0, processes[2].getWaitingTime())),
                };
            }

            @TestFactory
            @DisplayName("Process 1")
            @Order(2)
            DynamicTest[] Process_1() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(8, processes[1].getCompletionTime())),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(4.0, processes[1].getResponseTime())),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(6.0, processes[1].getTurnAroundTime())),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(4.0, processes[1].getWaitingTime())),
                };
            }

            @TestFactory
            @DisplayName("Process 3")
            @Order(3)
            DynamicTest[] Process_3() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(11, processes[3].getCompletionTime())),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(4.0, processes[3].getResponseTime())),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(7.0, processes[3].getTurnAroundTime())),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(4.0, processes[3].getWaitingTime())),
                };
            }
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class RoundRobinTests {
        void init(Process[] processes, int quantum) {
            int[] blockSizes = new int[processes.length];
            for (int i = 0; i < blockSizes.length; i++) blockSizes[i] = processes[i].getMemoryRequirements();
            Scheduler scheduler = new RoundRobin(quantum);
            MMU mmu = new MMU(blockSizes, new FirstFit(blockSizes));
            CPU.clock = 0;
            cpu = new CPU(scheduler, mmu, processes);
            cpu.run();
        }

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        class Test1 {
            Process[] processes = {
                    new Process(0, 5, 10),
                    new Process(2, 2, 40),
                    new Process(3, 1, 25),
                    new Process(4, 3, 30)
            };

            @BeforeAll
            void initTest1() {
                init(processes, 2);
            }

            @TestFactory
            @DisplayName("Process 1")
            @Order(0)
            DynamicTest[] Process_1() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(4, processes[1].getCompletionTime())),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(0.0, processes[1].getResponseTime())),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(2.0, processes[1].getTurnAroundTime())),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(0.0, processes[1].getWaitingTime())),
                };
            }

            @TestFactory
            @DisplayName("Process 2")
            @Order(1)
            DynamicTest[] Process_2() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(7, processes[2].getCompletionTime())),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(3.0, processes[2].getResponseTime())),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(4.0, processes[2].getTurnAroundTime())),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(3.0, processes[2].getWaitingTime())),
                };
            }

            @TestFactory
            @DisplayName("Process 0")
            @Order(3)
            DynamicTest[] Process_0() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(10, processes[0].getCompletionTime())),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(0.0, processes[0].getResponseTime())),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(10.0, processes[0].getTurnAroundTime())),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(5.0, processes[0].getWaitingTime())),
                };
            }

            @TestFactory
            @DisplayName("Process 3")
            @Order(3)
            DynamicTest[] Process_3() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(11, processes[3].getCompletionTime())),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(3.0, processes[3].getResponseTime())),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(7.0, processes[3].getTurnAroundTime())),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(4.0, processes[3].getWaitingTime())),
                };
            }
        }
    }
}