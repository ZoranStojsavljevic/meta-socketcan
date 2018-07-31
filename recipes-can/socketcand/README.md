### short socketcand user manual: How to activate socketcand on Beaglebone Black?
'''
root@beaglebone:~# modprobe can
[  481.509387] can: controller area network core (rev 20170425 abi 9)
[  481.516123] NET: Registered protocol family 29
root@beaglebone:~#   modprobe can_raw
[  481.567775] can: raw protocol (rev 20170425)
root@beaglebone:~#   modprobe can-bcm
[  481.618128] can: broadcast manager protocol (rev 20170425 t)
root@beaglebone:~#   modprobe can-dev
[  481.668321] CAN device driver interface
root@beaglebone:~#   modprobe can-gw
[  481.717922] can: netlink gateway (rev 20170425) max_hops=1
root@beaglebone:~#   modprobe vcan
[  481.767334] vcan: Virtual CAN interface driver
root@beaglebone:~#   lsmod | grep can
vcan                   16384  0
can_gw                 20480  0
can_dev                24576  0
can_bcm                24576  0
can_raw                20480  0
can                    28672  3 can_raw,can_bcm,can_gw
root@beaglebone:~# ip link add dev vcan0 type vcan
root@beaglebone:~#   ip link set vcan0 mtu 72
root@beaglebone:~#   ip link set dev vcan0 up
root@beaglebone:~#   ifconfig
eth0      Link encap:Ethernet  HWaddr F0:45:DA:83:09:B4  
          inet addr:192.168.15.101  Bcast:192.168.15.255  Mask:255.255.255.0
          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
          RX packets:8 errors:0 dropped:0 overruns:0 frame:0
          TX packets:24 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:3101 (3.0 KiB)  TX bytes:6156 (6.0 KiB)
          Interrupt:44 

lo        Link encap:Local Loopback  
          inet addr:127.0.0.1  Mask:255.0.0.0
          UP LOOPBACK RUNNING  MTU:65536  Metric:1
          RX packets:2 errors:0 dropped:0 overruns:0 frame:0
          TX packets:2 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:140 (140.0 B)  TX bytes:140 (140.0 B)

vcan0     Link encap:UNSPEC  HWaddr 00-00-00-00-00-00-00-00-00-00-00-00-00-00-00-00  
          UP RUNNING NOARP  MTU:72  Metric:1
          RX packets:0 errors:0 dropped:0 overruns:0 frame:0
          TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)

root@beaglebone:~# ifconfig
eth0      Link encap:Ethernet  HWaddr F0:45:DA:83:09:B4  
          inet addr:192.168.15.101  Bcast:192.168.15.255  Mask:255.255.255.0
          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
          RX packets:12 errors:0 dropped:0 overruns:0 frame:0
          TX packets:24 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:3360 (3.2 KiB)  TX bytes:6156 (6.0 KiB)
          Interrupt:44 

lo        Link encap:Local Loopback  
          inet addr:127.0.0.1  Mask:255.0.0.0
          UP LOOPBACK RUNNING  MTU:65536  Metric:1
          RX packets:2 errors:0 dropped:0 overruns:0 frame:0
          TX packets:2 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:140 (140.0 B)  TX bytes:140 (140.0 B)

vcan0     Link encap:UNSPEC  HWaddr 00-00-00-00-00-00-00-00-00-00-00-00-00-00-00-00  
          UP RUNNING NOARP  MTU:72  Metric:1
          RX packets:0 errors:0 dropped:0 overruns:0 frame:0
          TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)

root@beaglebone:~# cat /etc/socketcand.conf
# The network interface the socketcand will bind to
# listen = "eth0";

# The port the socketcand is listening on
# port = 28600;

# List of busses the daemon shall provide access to
# Multiple busses must be separated with ',' and whitespace
# is not allowed. eg "vcan0,vcan1"
busses = "vcan0";

# Description of the service. This will show up in the discovery beacon
# description = "socketcand";

root@beaglebone:~# vi /etc/socketcand.conf
root@beaglebone:~# cat /etc/socketcand.conf
# The network interface the socketcand will bind to
listen = "127.0.0.1";

# The port the socketcand is listening on
port = 28601;

# List of busses the daemon shall provide access to
# Multiple busses must be separated with ',' and whitespace
# is not allowed. eg "vcan0,vcan1"
busses = "vcan0";

# Description of the service. This will show up in the discovery beacon
description = "socketcand";

root@beaglebone:~# ps -aux | grep socketcand
root      1259  0.0  0.2   2776  1228 ttyO0    S+   05:39   0:00 grep socketcand
root@beaglebone:~# /etc/init.d/socketcand start
Starting SocketCAN daemon socketcand
root@beaglebone:~# ps -aux | grep socketcand
root      1263  0.0  0.2  10196  1324 ?        Sl   05:39   0:00 /usr/bin/socketcand --daemon
root      1270  0.0  0.2   2776  1268 ttyO0    S+   05:40   0:00 grep socketcand
root@beaglebone:~# nc 127.0.0.1 28601
< hi >< open vcan0 >
< ok >< rawmode >
< ok >
root@beaglebone:~# 
'''
