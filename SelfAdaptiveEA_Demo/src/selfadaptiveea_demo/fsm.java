/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selfadaptiveea_demo;

import java.util.Arrays;

/**
 *
 * @author pang
 */
public class fsm {
    int nextState=0;
    int CurrentState;
    int output=0;
    int NumStates = 4;
    int count =0;
    int numInput = 3;
    int[] uio= new int [numInput];
    
    public int transitionMany (int[][] fsmOutputSet,int[][] fsmNextState, int[] inputSeq){
        int[] outputSeq = new int[inputSeq.length];
        
        for(int a=0; a<NumStates; a++){
            CurrentState =a;
            System.out.println("\nStart State :  "+ CurrentState);
            System.out.println("InputSeq : "+Arrays.toString(inputSeq));
            
            for(int i=0; i<inputSeq.length; i++){
            //check each state
            if(i!=0){
                CurrentState = nextState;
            }
            outputSeq[i]= transition(fsmOutputSet,CurrentState,inputSeq[i]);
            //update state
            nextState = getCurrentState(fsmNextState,CurrentState,inputSeq[i]);
            
            System.out.println("current state :"+ CurrentState + "   Output :" +outputSeq[i] + "   Next State :"+ nextState);
            
            }
            
            System.out.println(Arrays.toString (uio)+Arrays.toString(outputSeq));
            if(a==0){
                uio = outputSeq.clone();
                count++;
                System.out.println("UIO s1"+ Arrays.toString(uio));
                System.out.println("OutputSeq s1 : "+Arrays.toString(outputSeq));
            }  
            if(a!=0){
                if(Arrays.toString(uio).equals(Arrays.toString(outputSeq)) ){
                          count++;
                          System.out.println("UIO s1"+Arrays.toString(uio));
                          System.out.println("OutputSeq same : "+Arrays.toString(outputSeq));
                        } else {
                          System.out.println("UIO s1"+Arrays.toString(uio));
                          System.out.println("OutputSeq uio : "+Arrays.toString(outputSeq));
                        }
            }
            
            
        }
        System.out.println("\nthen\nCounts : "+count+"\n");
        if(count==1){
            System.out.println(Arrays.toString(inputSeq)+" is a UIO for s1, the output is " +Arrays.toString(uio));
        } else 
            System.out.println("this input is not UIO for s1");
        return count;
    }
    
    
    //get the outputs and next states
    public int transition(int[][] fsmOutputSet, int currentState, int input){
        output = fsmOutputSet[currentState][input];
        return output;
    }
    
    public int getCurrentState(int[][] fsmNextState, int currentState, int input){
        nextState = fsmNextState[currentState][input];
        return nextState;
    }
    
}
