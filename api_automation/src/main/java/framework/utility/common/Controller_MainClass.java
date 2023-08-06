package framework.utility.common;

import org.testng.TestNG;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;

public class Controller_MainClass implements Runnable {

    public static void main(String[] args) {
        Runnable runnable = new Controller_MainClass();
        System.out.println("Creating Thread...");
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        System.out.println("Starting Thread...");
        thread1.start();
        thread2.start();

        State Thread_state;
        Thread_state = thread1.getState();

        if (Thread_state.equals("Idle")) {
            System.out.println("Thread state is Idle...");
        }
    }

    public void run() {
        System.out.println("thread is running...");
        TestNG runner = new TestNG();
        // Create a list of String
        List<String> suitefiles = new ArrayList<String>();
        // Add xml file which you have to execute
        suitefiles.add("./testng.xml");
        // now set xml file for execution
        runner.setTestSuites(suitefiles);
        // finally execute the runner using run method
        runner.run();
    }


}
