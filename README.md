# About
Java CPU Simulator is a showcase application that displays how multi-threading works programmatically.
Nothing fancy though.

# Usage

1) Run `mvn clean install`
2) Execute the simulator located at: com\github\petruki\playground\Simulator.java

You can also create yout own scenario or use one of available pre-built scenarios located at: com\github\petruki\playground\sample

```
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