/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga_demo;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author pang
 */
public class GA_Demo {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        int iteration=1000;
        // the lenght of any input sequences
        int inputLenght=10;
        // the number of the individuals
        int numPopulation = 40;
        int NumStates = 4;
        // the set of inputs or genes in any input sequences
        int numInput = 3;//0=a,1=b,2=c
        // the simple fsm, rows are state s1 -s4, columns are inputs a b c; where a=0, b=1, c=2
        // show the set of 'the outputs' between states and inputs.
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
        
        
        int[][] population= new int[numPopulation][inputLenght];
        int[] fitnessSet=new int[numPopulation];
        float aver=0;
        
        // example //
        // An example input sequence
        int[] inputS={2,2,2};
        int fitness = CalculateFitness(NumStates,fsmOutputSet,fsmNextState,inputS);
        System.out.println("The fitness of example: " + fitness+"\n\n");
        
        System.out.println("---Start---");
        // 1 //random input sequences = population // population
        population = createFirstPopulation( numPopulation,  inputLenght,  numInput);
        
        //iteration
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
                // 3 //choose two individuals by tournament selection // selection
                int[][] individuals=Selection(population, numPopulation, inputLenght);

                // 4 //crossover two individuals // crossover
                // 5 //mutate the offspring // mutation
                int[] offspring= Mutation(Crossover(individuals, inputLenght), numInput);

                // 6 //reproduce the individual
                for(int j=0;j<offspring.length;j++){
                    population[a][j]= offspring[j];
                }

            } 
        aver =(aver+Average(fitnessSet));
        System.out.println((z+1)+"  iteration The Next Population is : " + Arrays.deepToString(population));
        //System.out.println((z+1)+"  The average : "+ Average(fitnessSet));
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
    
    // crossover the individuals
    public static int[] Crossover(int[][] twoIndividuals, int inputLenght){
        int[] individual1= new int[inputLenght];
        int[] individual2= new int[inputLenght];
        for(int i=0;i<inputLenght;i++){
            individual1[i]= twoIndividuals[0][i];
            individual2[i]= twoIndividuals[1][i];
        }
        Random randCrossover = new Random();
        int c =randCrossover.nextInt(inputLenght);
        System.out.println("Crossover : crossover point  :  "+c );
        for(int j=c ;j<inputLenght;j++){
               individual1[j]=individual2[j];       
        }
        System.out.println("crossover : "+Arrays.toString(individual1));
        return individual1;
    }
    
    // mutate the offspring
    public static int[] Mutation(int[] offspring, int numInput){
        if(offspring.length!=0){
            Random r = new Random();
            int m=r.nextInt(offspring.length);
            System.out.println("Mutation point  : "+m);
            offspring[m]= r.nextInt(numInput);
            System.out.println("Mutation  : "+Arrays.toString(offspring));
        }
        return offspring;
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
