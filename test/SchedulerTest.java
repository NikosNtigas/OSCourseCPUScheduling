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
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(processes[0].getCompletionTime(), 5)),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(processes[0].getResponseTime(), 1.0)),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(processes[0].getTurnAroundTime(), 5.0)),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(processes[0].getWaitingTime(), 0.0)),
                };
            }

            @TestFactory
            @DisplayName("Process 1")
            @Order(1)
            DynamicTest[] Process_1() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(processes[1].getCompletionTime(), 7)),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(processes[1].getResponseTime(), 4.0)),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(processes[1].getTurnAroundTime(), 5.0)),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(processes[1].getWaitingTime(), 3.0)),
                };
            }

            @TestFactory
            @DisplayName("Process 2")
            @Order(2)
            DynamicTest[] Process_2() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(processes[2].getCompletionTime(), 8)),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(processes[2].getResponseTime(), 5.0)),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(processes[2].getTurnAroundTime(), 5.0)),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(processes[2].getWaitingTime(), 4.0)),
                };
            }

            @TestFactory
            @DisplayName("Process 3")
            @Order(3)
            DynamicTest[] Process_3() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(processes[3].getCompletionTime(), 11)),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(processes[3].getResponseTime(), 5.0)),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(processes[3].getTurnAroundTime(), 7.0)),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(processes[3].getWaitingTime(), 4.0)),
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
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(processes[0].getCompletionTime(), 5)),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(processes[0].getResponseTime(), 1.0)),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(processes[0].getTurnAroundTime(), 5.0)),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(processes[0].getWaitingTime(), 0.0)),
                };
            }

            @TestFactory
            @DisplayName("Process 2")
            @Order(1)
            DynamicTest[] Process_2() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(processes[2].getCompletionTime(), 6)),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(processes[2].getResponseTime(), 3.0)),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(processes[2].getTurnAroundTime(), 3.0)),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(processes[2].getWaitingTime(), 2.0)),
                };
            }

            @TestFactory
            @DisplayName("Process 1")
            @Order(2)
            DynamicTest[] Process_1() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(processes[1].getCompletionTime(), 8)),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(processes[1].getResponseTime(), 5.0)),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(processes[1].getTurnAroundTime(), 6.0)),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(processes[1].getWaitingTime(), 4.0)),
                };
            }

            @TestFactory
            @DisplayName("Process 3")
            @Order(3)
            DynamicTest[] Process_3() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(processes[3].getCompletionTime(), 11)),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(processes[3].getResponseTime(), 5.0)),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(processes[3].getTurnAroundTime(), 7.0)),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(processes[3].getWaitingTime(), 4.0)),
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
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(processes[1].getCompletionTime(), 4)),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(processes[1].getResponseTime(), 1.0)),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(processes[1].getTurnAroundTime(), 2.0)),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(processes[1].getWaitingTime(), 0.0)),
                };
            }

            @TestFactory
            @DisplayName("Process 2")
            @Order(1)
            DynamicTest[] Process_2() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(processes[2].getCompletionTime(), 7)),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(processes[2].getResponseTime(), 4.0)),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(processes[2].getTurnAroundTime(), 4.0)),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(processes[2].getWaitingTime(), 3.0)),
                };
            }

            @TestFactory
            @DisplayName("Process 0")
            @Order(3)
            DynamicTest[] Process_0() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(processes[0].getCompletionTime(), 10)),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(processes[0].getResponseTime(), 1.0)),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(processes[0].getTurnAroundTime(), 10.0)),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(processes[0].getWaitingTime(), 5.0)),
                };
            }

            @TestFactory
            @DisplayName("Process 3")
            @Order(3)
            DynamicTest[] Process_3() {
                return new DynamicTest[]{
                        DynamicTest.dynamicTest("Completion Time", () -> Assertions.assertEquals(processes[3].getCompletionTime(), 11)),
                        DynamicTest.dynamicTest("Response Time", () -> Assertions.assertEquals(processes[3].getResponseTime(), 4.0)),
                        DynamicTest.dynamicTest("TAT", () -> Assertions.assertEquals(processes[3].getTurnAroundTime(), 7.0)),
                        DynamicTest.dynamicTest("Waiting Time", () -> Assertions.assertEquals(processes[3].getWaitingTime(), 4.0)),
                };
            }
        }
    }
}