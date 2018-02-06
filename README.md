# Illumio-Coding-Test
Illumio Coding Assignment 2017-­‐2018, PCE team (Avenger)

It took me about 90 mins to come up with this implementation.
Due to time limitation, I could not cover every implementation mentioned in the problem.
I have addressed the following input rules:

1) direction – either “inbound” or “outbound”
2) protocol – either “tcp” or “udp”
3) port – a) an integer 
          b) ambiguity related to the port range which is addressed below
4)  IP address – a) an ip address
                 b) range of two ip addresses.
                 
I have considered the following input through a CSV file:
inbound,tcp,80,192.168.1.2
outbound,tcp,10000-­‐20000,192.168.10.11
inbound,udp,53,192.168.1.1-­‐192.168.2.5
outbound,udp,1000-­‐2000,52.12.48.92

The problem expects me to match all packets within the range of a port or an ip address when a range for the same is passed.
Unfortunately, I fell short of time and was only able to validate the input for the passed starting and ending value and not for the values that lie within them.
With more time at hand, I would have stored the values by setting a counter from the starting value to the ending value inclusive of both.


-Ambiguities:
1)accept_packet method takes port as an Integer.
When a range of integers is passed as port, the value is the subtracted value .ie 80-89 will pass -9.
I have only considered the case in which the input value is an integer.
For range values of port, I have commented my logic as it does not fit into the design of the method.
Thought of method overloading option too to solve this but could not think of something concrete as the input type is restricted as per the function interface.

2)I wish to know why these cases fail:
 > fw.accept_packet("inbound", "tcp", 81, "192.168.1.2")
false
> fw.accept_packet("inbound", "udp", 24, "52.12.48.92")
false

My solution should work with a time complexity of O(n).


- I would love to be a part of the Data Team.










