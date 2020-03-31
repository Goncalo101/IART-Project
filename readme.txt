Para executar o programa, na pasta principal do programa onde se encontra o ficheiro IART-Project.jar, execute no terminal o comando:
java -jar IART-Project.jar <algoritmo> <ficheiro de input> <ficheiro de output>
No campo algoritmo pode-se colocar: simulatedAnnealing, genetic ou hillClimbing.
Nos campos de input e output devem ser colocados ficheiros ja existentes.
Exemplo:

java -jar IART-Project.jar hillClimbing input/b_short_walk.in output/b_short_walk.out
