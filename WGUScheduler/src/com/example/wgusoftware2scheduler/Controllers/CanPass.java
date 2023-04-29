package com.example.wgusoftware2scheduler.Controllers;
/**

 CanPass is an interface that defines a single method 'passData' which can be implemented by classes that need to pass data between them.
 */
public interface CanPass {
    /**

     Method to pass data between classes implementing the CanPass interface.
     @param data The data to be passed between classes.
     */
    public void passData(Object data);
}
