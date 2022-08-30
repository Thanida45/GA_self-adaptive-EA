/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selfadaptiveea_demo;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author pang
 */
public class SelfAdaptiveEA_Demo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int iteration=1000;
        int NumStates = 4;
        int numInput = 3;
        int MutationRate = 0;
        // the simple fsm, rows are state s1 -s4, columns are inputs a b c; where a=0, b=1, c=2
        // show the set of 'the outputs' between states and inputs.
        int[][] fsmOutputSet={
                    {0, 2, 1},
                    {0, 0, 2},
                    {1, 0, 2},
                    {0, 2, 1}};
        // the simple fsm, rows are state s1 -s4, columns are inputs a b c; where a=0, b=1, c=2
        // show the set of 'the next states' between states and inputs
        int[][] fsmNextState={
                    {0, 1, 2},
                    {2, 3, 1},
                    {0, 2, 3},
                    {2, 3, 1}};
        // the lenght of any input sequences
        int inputLenght=10;
        // the number of the population
        int numPopulation = 40;
        int[][] population= new int[numPopulation][inputLenght];
        int[] individual= new int[inputLenght];
        int[][] population1= new int[numPopulation][inputLenght];
        int[] fitnessSet=new int[numPopulation];
        float rate = 0;
        int f=0;
        float aver=0;
        // example //
        // An example input sequence
        int[] inputS={2,2,2};
        int fitness = CalculateFitness(NumStates,fsmOutputSet,fsmNextState,inputS);
        System.out.println("The fitness of example: " + fitness+"\n\n");
        
        System.out.println("---Start---");
        
        // 1 //random input sequences = population // population
        population = createFirstPopulation( numPopulation,  inputLenght,  numInput);
        
        // for iterations
        for(int z=0; z<iteration;z++){ 
            // for all individuals
            for(int a=0; a<numPopulation;a++){ 
                
                // 2 //compute the fitness of the population // evaluation
                int[] inputSeq=new int[inputLenght];
                for(int i=0; i<numPopulation;i++){
                    for(int j=0;j<inputLenght;j++){
                        inputSeq[j]= population[i][j];
                    }
                    fitnessSet[i]=CalculateFitness(NumStates,fsmOutputSet,fsmNextState,inputSeq);
                }
                System.out.println("\n\nThe population : "+Arrays.deepToString(population)+"\n");
                System.out.println("The fitness of the population : "+Arrays.toString(fitnessSet)+"\n");
                
                // sort the population
                comparePopulation(numPopulation,inputLenght,population,fitnessSet);
                // 3 // choose one individual for mutation
                for(int i=0;i<inputLenght;i++){
                     
                     individual[i]=population[0][i];
                }
               
                System.out.println(Arrays.toString(individual));
                
                Random r = new Random();
                rate= (float) (r.nextFloat()+0.8);
                System.out.println("The rate X: "+rate);
                // 4 //mutate the rate // Mutation Rate
                rate= MutationRate(rate, inputLenght);
                
                // 5 //mutate the offspring // mutation
                individual = Mutation(individual,rate,numInput);
                
                // 6 //reproduce the individual
                for(int j=0;j<individual.length;j++){
                  population[a][j]= individual[j];
                }

            } 
        //population=population1.clone();
        aver =(aver+Average(fitnessSet));
        System.out.println((z+1)+"  The Next Population is : " + Arrays.deepToString(population));
        }//end main class
        System.out.println("  The average : "+aver/iteration);
    }
    
    
     // Functions for the main class
    // Compute fitness function
    public static int CalculateFitness( int Num,int[][] fsmOutputSet,int[][] fsmNextState, int[] inputSeq){
        int count=0;
        int number=Num;
        fsm n = new fsm();
        count=number-n.transitionMany(fsmOutputSet, fsmNextState, inputSeq);
        return count;
    }
    
    // create the population
    public static int[][] createFirstPopulation(int numPopulation, int inputLenght, int numInput) {
       //random input sequences = population
        int[][] population = new int[numPopulation][inputLenght];
        for(int j=0;j<numPopulation;j++){
            Random rand = new Random();
            for(int i=0;i<inputLenght;i++){
              population[j][i] = rand.nextInt(numInput);
            }
        }
         System.out.println("---The GA---\nPopulation  :  " + Arrays.deepToString(population));
        
        return population;
    }
    
    // comparable
    public static int[][] comparePopulation(int numPopulation,int inputLenght,int[][] population, int[] fitness){
        
        int[] pop=new int[inputLenght];
        
        for(int i = 0; i<numPopulation-1;i++){
            for(int j=i+1; j<numPopulation;j++){
                if(fitness[i]<fitness[j]){
                    for(int k=0; k<inputLenght;k++){
                        pop[k]= population[i][k];
                        population[i][k]=population[j][k];
                        population[j][k] = pop[k];
                    }
                }else; 
                
            }
          
        }
        System.out.println("sorting : "+Arrays.deepToString(population));
        return population;
    } 
    // choose two individuals
    public static int[][] Selection(int[][] population, int numPopulation, int inputLenght){
        Random rand1 = new Random();
        Random rand2 = new Random();
        int a = rand1.nextInt(numPopulation-1);
        int b = rand2.nextInt(numPopulation-1);
        int[][] individuals = new int[2][inputLenght];
        if (a==b){
            if (a!=0){
                a--;
            } else a++;
        }
       for(int i = 0;i<inputLenght;i++){
            individuals[0][i] = population[a][i];
            individuals[1][i] = population[b][i];
        }
        System.out.println("Selection :  choose two individuals  :  "+Arrays.deepToString(individuals));
        return individuals;
    }
    
    // MutationRate the individuals
    public static float MutationRate(float rate, int inputLenght ){
        if(rate>1.1){
            rate =(float)inputLenght/2;
            System.out.println("Mutation rate X' (n/2) : "+rate);
        } else {rate= (float) 0.1;
            System.out.println("Mutation rate X' (1/10)=(E) : "+rate);}
        return rate;
    }
    
    // mutate the offspring
    public static int[] Mutation(int[] individual, float rate, int numInput){
        System.out.println("Mutation : X'/n\n");
        if(rate<0.5){
            rate = 0;
            return individual;
        } 
        if (rate>=0.5&&rate<1){
            rate =1;
        }
        
        int m = (int) rate;
        Random r = new Random();
        System.out.println("Mutation Rate :  " +m);
            for (int i=individual.length-m;i<individual.length;i++ ){
                System.out.println("Mutation  :  from " +individual[i]);
                individual[i]= r.nextInt(numInput);
                System.out.println("Mutation  :   to "+individual[i]);
            }
            
            System.out.println("Mutation  : "+Arrays.toString(individual));
        
        return individual;
    }
    
    public static float Average(int[] fitness){
       float a=0;
        for(int i=0;i<fitness.length;i++){
            a=a+fitness[i];
        }
        a= a/fitness.length;
        return a;
    }
    //end the GA program.
    
}
