# About
Java CPU Simulator is a showcase application that displays how multi-threading works programmatically.
Nothing fancy though.

# Usage

1) Run `mvn clean install`
2) Execute the simulator located at: com\github\petruki\playground\Simulator.java


You can also create your own scenario or use one of the available pre-built scenarios located at: com\github\petruki\playground\sample

# Output sample
```
#############################
# Custom Scenario: 
# CPUs: 1
# - Computer
#      quantum=5
#
# Proc I
# Start at: 0
#   run(10)
#   run(13)
#
# Proc II
# Start at: 3
#   run(10)
#
# Proc III
# Start at: 2
#   run(10)
#
#############################

Proc I
|#####----------#####----------#############
Proc II
---|-------#####----------#####
Proc III
--|---#####----------#####

- : IDLE
# : RUNNING
| : Request time
```