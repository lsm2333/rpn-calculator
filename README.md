# What's this
This project implements a basic calculator including RPN-calculator using java. And users can use it via command line.

#### Stage 1: A command-line based RPN
- Basic math operators (+,-,*,/,sqrt)
- Undo and clear functionality
 
#### Stage 2: Enhanced functionalities
- More math operators, such as n!, COS, ATAN, etc
- Other operators such as redo, swap, etc. (TBD)

#### Stage 3: Online & UI (TBD)
- Bring it online
- Customize the colour of each number in the stack depends on it position.

# For developers
Just share some thoughts during development to help developers go further beyond this project. In order to make project have better maintainability and extensibility, I take some of the design principle into consideration.
For example, the SOLID principle(https://en.wikipedia.org/wiki/SOLID). I abstract an interface ```model.calculator.Calculator``` for stack based calculators. For more details, please check below lists.

## Add more operators
1. just add one enum in ```enums.RpnOperator``` with implementation.
2. nothing else!
3. the reason I use abstract method in enum is to set a limitation on the implementation of operator.
A note under [javase specs Example §8.9.2-4](https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.9.2)  seems to agree: ```This pattern is much safer than using a switch statement in the base type (Operation), as the pattern precludes the possibility of forgetting to add a behavior for a new constant (since the enum declaration would cause a compile-time error).``` 

## Add more stack based calculators
1. you can implement more stack based calculators by implementing interface ```model.calculator.Calculator``` 
    1. currenty it defines a method ```ExtendStack<Double> calculate(String input)``` to calculate for input, and a method ```getResultStack``` to return result stack
    2. it also has a default method ```selfIntro()``` which you can use to introduce when specific calculator is chosen by user; and a default method ```handleException``` to handle exception, print error message and stack
    
# For users

## Run
1. try ```run_calculator.sh```, which is equivalent to following operation
    1. run ```mvn package``` to build a jar under /target
    2. run command ```java -jar target/rpn-calculator-0.0.1-SNAPSHOT.jar```  to run a rpn calculator or ```mvn exec:java``` 
2. rpn calculator is chosen by default
3. enter an expression to calculate

the actual running example:
![](src/main/resources/image/run_example.png)

## Test
1. try ```test.sh``` which will execute ```mvn test```

the actual testing example:
![](src/main/resources/image/test_example.png)